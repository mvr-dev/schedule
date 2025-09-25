package dev.mvr.schedule.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import dev.mvr.schedule.model.omsu.OmsuLesson;
import dev.mvr.schedule.model.omsu.OmsuSchedule;
import dev.mvr.schedule.model.Payload;
import dev.mvr.schedule.model.UniversityGroup;
import dev.mvr.schedule.repository.StudentRepository;
import dev.mvr.schedule.utils.RequestUtil;
import dev.mvr.schedule.utils.Utils;

import java.time.LocalDate;
import java.util.*;

public class VkBotService implements Runnable{

    @Override
    public void run() {
        try {
            TransportClient transportClient = new HttpTransportClient();
            VkApiClient vk = new VkApiClient(transportClient);

            String token = System.getenv("VK_BOT_TOKEN");
            Integer groupId = Integer.parseInt(System.getenv("VK_GROUP_ID"));

            GroupActor actor = new GroupActor(groupId, token);
            Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();

            Random random = new Random();
            Keyboard universityKeyboard = createUniversityKeyboard();
            Keyboard actionsKeyboard = createActionsKeyboard();
            Map<String,Integer> scheduleMessageLastId = new HashMap<>();

            while (true) {
                try {
                    MessagesGetLongPollHistoryQuery historyQuery =
                            vk.messages().getLongPollHistory(actor).ts(ts);
                    List<Message> messages = historyQuery.execute().getMessages().getItems();
//                    vk.messages().getLongPollHistory(actor).ts(ts).execute().getHistory().

                    if (!messages.isEmpty()) {
                        for (Message message : messages) {
                            try {
                                System.out.println(message.getPayload());
                                // Обработка callback
                                if (message.getPayload()!=null) {
                                    System.out.println("Processing payload: " + message.getPayload());
                                    Payload payload = Utils.parsePayload(message.getPayload());
                                    String groupName = payload.getGroup();
                                    LocalDate day = LocalDate.parse(payload.getDate());
                                    boolean needUpdate = payload.isNeedUpdate();

                                    if ("prev_day".equals(payload.getAction())) {
                                        day = day.minusDays(1);
                                    }
                                    else if ("next_day".equals(payload.getAction())) {
                                        day = day.plusDays(1);
                                    }
                                    else if("today".equals(payload.getAction())){
                                        day = LocalDate.now();
                                        needUpdate = true;
                                    }

                                    // Получаем расписание
                                    var schedule = RequestUtil.getOmsuGroupSchedule(groupName, needUpdate);
                                    int index = Utils.getIndexOfDayInOmsuScheduleList(groupName, day);
                                    OmsuSchedule todayLessons;

                                    if (index >= schedule.size() || !schedule.get(index).getDay().equals(day)) {
                                        todayLessons = new OmsuSchedule();
                                        todayLessons.setDay(day);
                                        OmsuLesson noLesson = new OmsuLesson();
                                        noLesson.setLesson("Нет занятий");
                                        todayLessons.setLessons(List.of(noLesson));
                                    } else {
                                        todayLessons = schedule.get(index);
                                    }
//                                    System.out.printl;
                                    vk.messages().delete(actor)
                                            .groupId(groupId) // ID группы должен быть положительным
                                            .peerId(message.getPeerId())
                                            .messageIds(scheduleMessageLastId.get(groupName)) // Удаляем текущее сообщение
                                            .deleteForAll(true) // Важно: удалить для всех
                                            .execute();
                                    Integer id = vk.messages()
                                            .send(actor)
                                            .message(String.format("Расписание для %s\n\n%s", groupName, todayLessons.toString()))
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .keyboard(createNavigationKeyboard(groupName, day))
                                            .execute();
                                    var msg = scheduleMessageLastId.get(groupName);
                                    if (msg==null){
                                        scheduleMessageLastId.put(groupName,id);
                                    }
                                    else{
                                        scheduleMessageLastId.replace(groupName,id);
                                    }
//                                    continue; // Пропускаем обработку текста
                                }

                                // Обработка текстовых сообщений
                                else if (message.getText() != null) {
                                    System.out.println("Text message: " + message.getText());

                                    if (message.getText().equalsIgnoreCase("Начать")) {
                                        StudentRepository.addStudent(message.getFromId());
                                        vk.messages()
                                                .send(actor)
                                                .message("Привет! Выбери университет")
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .keyboard(universityKeyboard)
                                                .execute();
                                    }
                                    else if (message.getText().equals("ОмГУ")) {
                                        StudentRepository.addGroup(message.getFromId(), new UniversityGroup("ОмГУ"));
                                        vk.messages()
                                                .send(actor)
                                                .message("Введи номер группы (полностью)\n например: ХХБ-001-О-01")
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .execute();
                                    }
                                    else if (message.getText().equals("ОмГТУ")) {
                                        StudentRepository.addGroup(message.getFromId(), new UniversityGroup("ОмГТУ"));
                                        vk.messages()
                                                .send(actor)
                                                .message("Введи номер группы")
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .execute();
                                    }
                                    else if (message.getText().equalsIgnoreCase("мои группы")) {
                                        StringBuilder sb = new StringBuilder("Твои группы:\n");
                                        for (UniversityGroup group : StudentRepository.getStudentGroups(message.getFromId())) {
                                            sb.append(group.toString());
                                            sb.append('\n');
                                        }
                                        vk.messages()
                                                .send(actor)
                                                .message(sb.toString())
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .keyboard(actionsKeyboard)
                                                .execute();
                                    }
                                    else if (message.getText().toLowerCase().contains("расписание для")) {
                                        boolean needUpdate = true;
                                        LocalDate day = LocalDate.now();

                                        var groups = StudentRepository.getStudentGroups(message.getFromId());
                                        String thisGroup = message.getText().substring(15).strip();

                                        UniversityGroup group = null;
                                        for(UniversityGroup gr: groups){
                                            if (gr.getGroup().equalsIgnoreCase(thisGroup)){
                                                group = gr;
                                                break;
                                            }
                                        }

                                        if (group==null){
                                            vk.messages()
                                                    .send(actor)
                                                    .message("Неверная группа")
                                                    .userId(message.getFromId())
                                                    .randomId(random.nextInt(10000))
                                                    .execute();
                                        }
                                        else if (group.getUniversity().equalsIgnoreCase("ОмГУ")){
                                            var schedule = RequestUtil.getOmsuGroupSchedule(thisGroup, needUpdate);
                                            int index = Utils.getIndexOfDayInOmsuScheduleList(group.getGroup(), day);
                                            OmsuSchedule todayLessons;

                                            if (index >= schedule.size() || !schedule.get(index).getDay().equals(day)) {
                                                todayLessons = new OmsuSchedule();
                                                todayLessons.setDay(day);
                                                OmsuLesson noLesson = new OmsuLesson();
                                                noLesson.setLesson("Нет занятий");
                                                todayLessons.setLessons(List.of(noLesson));
                                            } else {
                                                todayLessons = schedule.get(index);
                                            }
                                            Integer id = vk.messages()
                                                    .send(actor)
                                                    .message(String.format("Расписание для %s\n\n%s", group.getGroup(), todayLessons.toString()))
                                                    .userId(message.getFromId())
                                                    .randomId(random.nextInt(10000))
                                                    .keyboard(createNavigationKeyboard(group.getGroup(), day))
                                                    .execute();
                                            var msg = scheduleMessageLastId.get(group.getGroup());
                                            if (msg==null){
                                                scheduleMessageLastId.put(group.getGroup(),id);
                                            }
                                            else{
                                                scheduleMessageLastId.replace(group.getGroup(),id);
                                            }
                                        }
                                    }
                                    else if (message.getText().equalsIgnoreCase("добавить группу")) {
                                        var groups = StudentRepository.getStudentGroups(message.getFromId());
                                        UniversityGroup group;
                                        if (!groups.isEmpty()) {
                                            group = groups.get(groups.size() - 1);
                                        } else {
                                            group = new UniversityGroup();
                                        }
                                        if (group.getGroup() != null || group.getUniversity() == null)
                                            vk.messages()
                                                    .send(actor)
                                                    .message("Выбери университет")
                                                    .userId(message.getFromId())
                                                    .randomId(random.nextInt(10000))
                                                    .keyboard(universityKeyboard)
                                                    .execute();
                                        else
                                            vk.messages()
                                                    .send(actor)
                                                    .message(Objects.equals(group.getUniversity(), "ОмГУ") ?
                                                            "Введи номер группы (полностью)\n например: ХХБ-001-О-01"
                                                            : "Введи номер группы")
                                                    .userId(message.getFromId())
                                                    .randomId(random.nextInt(10000))
                                                    .execute();
                                    } else if (Utils.groupIdOmsu(message.getText()) != -1) {
                                        var groups = StudentRepository.getStudentGroups(message.getFromId());
                                        var group = groups.get(groups.size() - 1);
                                        if (group.getUniversity().equals("ОмГУ")) {
                                            group.setGroup(message.getText().toUpperCase());
                                            if(actionsKeyboard.getButtons().size()<9){
                                                actionsKeyboard.getButtons().add(List.of(
                                                        new KeyboardButton().setAction(
                                                                new KeyboardButtonAction().setLabel(
                                                                        "Расписание для "+message.getText().toUpperCase()
                                                                ).setType(TemplateActionTypeNames.TEXT)
                                                        )
                                                ));
                                            }
                                            vk.messages()
                                                    .send(actor)
                                                    .message("Группа добавлена")
                                                    .userId(message.getFromId())
                                                    .keyboard(actionsKeyboard)
                                                    .randomId(random.nextInt(10000))
                                                    .execute();
                                        }
                                    }
                                    else if (Utils.isOmstuPattern(message.getText().toUpperCase())) {
                                        var groups = StudentRepository.getStudentGroups(message.getFromId());
                                        var group = groups.get(groups.size() - 1);
                                        if (group.getUniversity().equals("ОмГТУ")) {
                                            group.setGroup(message.getText());
                                            if(actionsKeyboard.getButtons().size()<9){
                                                actionsKeyboard.getButtons().add(List.of(
                                                        new KeyboardButton().setAction(
                                                                new KeyboardButtonAction().setLabel(
                                                                        "Расписание для "+message.getText().toUpperCase()
                                                                ).setType(TemplateActionTypeNames.TEXT)
                                                        )
                                                ));
                                            }
                                            vk.messages()
                                                    .send(actor)
                                                    .message("Группа добавлена")
                                                    .userId(message.getFromId())
                                                    .randomId(random.nextInt(10000))
                                                    .keyboard(actionsKeyboard)
                                                    .execute();
                                        }
                                    }
                                    else {
                                        vk.messages()
                                                .send(actor)
                                                .message("Я вас не понял")
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .keyboard(actionsKeyboard)
                                                .execute();
                                    }
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                System.out.println("Error processing message: " + e.getMessage());
                            }
                        }
                    }

                    ts = vk.messages().getLongPollServer(actor).execute().getTs();
                    Thread.sleep(500);

                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Keyboard createUniversityKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<List<KeyboardButton>> universities = new ArrayList<>();
        List<KeyboardButton> line1 = List.of(
                new KeyboardButton().setAction(
                        new KeyboardButtonAction().setLabel("ОмГУ")
                                .setType(TemplateActionTypeNames.TEXT)
                ),
                new KeyboardButton().setAction(
                        new KeyboardButtonAction().setLabel("ОмГТУ")
                                .setType(TemplateActionTypeNames.TEXT)
                ));
        universities.add(line1);
        keyboard.setButtons(universities);
        return keyboard;
    }

    private Keyboard createActionsKeyboard() {
        Keyboard keyboard = new Keyboard();
        List<List<KeyboardButton>> actions = new ArrayList<>(List.of(
                List.of(
                        new KeyboardButton().setAction(
                                new KeyboardButtonAction().setLabel("мои группы")
                                        .setType(TemplateActionTypeNames.TEXT)
                        ),
                        new KeyboardButton().setAction(
                                new KeyboardButtonAction().setLabel("добавить группу")
                                        .setType(TemplateActionTypeNames.TEXT)
                        )
                )
        ));
        keyboard.setButtons(actions);
        return keyboard;
    }

    private Keyboard createNavigationKeyboard(String groupName, LocalDate date) {
        return new Keyboard().setInline(true)
                .setButtons(List.of(
                        List.of(
                                new KeyboardButton().setAction(
                                        new KeyboardButtonAction()
                                                .setLabel("<-")
                                                .setType(TemplateActionTypeNames.TEXT)
                                                .setPayload(createPayload("prev_day", groupName, date, false))
                                ),
                                new KeyboardButton().setAction(
                                        new KeyboardButtonAction()
                                                .setLabel("Сегодня")
                                                .setType(TemplateActionTypeNames.TEXT)
                                                .setPayload(createPayload("today", groupName, date, true))
                                ),
                                new KeyboardButton().setAction(
                                        new KeyboardButtonAction()
                                                .setLabel("->")
                                                .setType(TemplateActionTypeNames.TEXT)
                                                .setPayload(createPayload("next_day", groupName, date, false))
                                )
                        )
                ));
    }

    private String createPayload(String action, String groupName, LocalDate date, boolean needUpdate) {
        return String.format("{\"action\":\"%s\",\"group\":\"%s\",\"date\":\"%s\",\"need_update\":%b}",
                action, groupName, date.toString(), needUpdate);
    }
}