package dev.mvr.schedule.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import dev.mvr.schedule.model.UniversityGroup;
import dev.mvr.schedule.repository.StudentRepository;
import dev.mvr.schedule.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class VkBotService implements Runnable{

    @Override
    public void run() {
        try {
            TransportClient transportClient = new HttpTransportClient();
            VkApiClient vk = new VkApiClient(transportClient);

            String token = System.getenv("VK_BOT_TOKEN");
            Integer groupId = Integer.parseInt(System.getenv("VK_GROUP_ID"));

            if (token == null) {
                return;
            }
//        System.out.println(groups);
            GroupActor actor = new GroupActor(groupId, token);
            // Тест подключения к VK API
            String groupName = vk.groups().getByIdObjectLegacy(actor).execute().get(0).getName();


            Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();

            Random random = new Random();
            Keyboard universityKeyboard = new Keyboard();
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
            universityKeyboard.setButtons(universities);

            Keyboard actionsKeyboard = new Keyboard();
            List<List<KeyboardButton>> actions = List.of(
                    List.of(
                            new KeyboardButton().setAction(
                                    new KeyboardButtonAction().setLabel("мой универ")
                                            .setType(TemplateActionTypeNames.TEXT)
                            ),
                            new KeyboardButton().setAction(
                                    new KeyboardButtonAction().setLabel("добавить группу")
                                            .setType(TemplateActionTypeNames.TEXT)
                            )
                    ),
                    List.of(
                            new KeyboardButton().setAction(
                                    new KeyboardButtonAction().setLabel("расписание на сегодня")
                                            .setType(TemplateActionTypeNames.TEXT)
                            )
                    )
            );


            while (true) {
                try {
                    MessagesGetLongPollHistoryQuery historyQuery =
                            vk.messages().getLongPollHistory(actor).ts(ts);

                    List<Message> messages = historyQuery.execute().getMessages().getItems();

                    if (!messages.isEmpty()) {
                        for (Message message : messages) {
                            if (message.getText() != null) {
                                if (message.getText().equalsIgnoreCase("Начать")) {
                                    StudentRepository.addStudent(message.getFromId());
                                    vk.messages()
                                            .send(actor)
                                            .message("Привет! Выбери университет")
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .keyboard(universityKeyboard)
                                            .execute();
                                } else if (message.getText().equals("ОмГУ")) {
                                    StudentRepository.addGroup(message.getFromId(), new UniversityGroup("ОмГУ"));
                                    vk.messages()
                                            .send(actor)
                                            .message("Введи номер группы (полностью)\n например: ХХБ-001-О-01")
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .execute();
                                } else if (message.getText().equals("ОмГТУ")) {
                                    StudentRepository.addGroup(message.getFromId(), new UniversityGroup("ОмГТУ"));
                                    vk.messages()
                                            .send(actor)
                                            .message("Введи номер группы")
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .execute();
                                } else if (message.getText().equalsIgnoreCase("мой универ")) {
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
                                            .execute();

                                } else if (message.getText().equalsIgnoreCase("добавить группу")) {
                                    var groups = StudentRepository.getStudentGroups(message.getFromId());
                                    var group = groups.get(groups.size() - 1);
                                    if (group.getGroup() != null)
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
                                                .message(Objects.equals(group.getGroup(), "ОмГУ") ?
                                                                "Введи номер группы (полностью)\n например: ХХБ-001-О-01"
                                                        : "Введи номер группы")
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .execute();

                                } else if (Utils.groupIdOmsu(message.getText()) != -1) {
                                    System.out.println("Group for OmSU: "+message.getText());
                                    var groups = StudentRepository.getStudentGroups(message.getFromId());
                                    var group = groups.get(groups.size() - 1);
                                    if (group.getUniversity().equals("ОмГУ")) {
                                        group.setGroup(message.getText().toUpperCase());
                                        vk.messages()
                                                .send(actor)
                                                .message("Группа добавлена")
                                                .userId(message.getFromId())
                                                .keyboard(actionsKeyboard)
                                                .randomId(random.nextInt(10000))
                                                .execute();
                                    } else {
                                        vk.messages()
                                                .send(actor)
                                                .message("Неверная группа для " + group.getUniversity())
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .keyboard(
                                                        new Keyboard().setButtons(
                                                                List.of(List.of(
                                                                                new KeyboardButton().setAction(
                                                                                        new KeyboardButtonAction().setLabel("Добавить группу")
                                                                                                .setType(TemplateActionTypeNames.TEXT)
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                                .execute();
                                    }
                                } else if (Utils.getOmstuGroup(message.getText().toUpperCase()) != null) {
                                    var groups = StudentRepository.getStudentGroups(message.getFromId());
                                    var group = groups.get(groups.size() - 1);
                                    if (group.getUniversity().equals("ОмГТУ")) {
                                        group.setGroup(message.getText());
                                        vk.messages()
                                                .send(actor)
                                                .message("Группа добавлена")
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .execute();
                                    } else {
                                        vk.messages()
                                                .send(actor)
                                                .message("Неверная группа для " + group.getUniversity())
                                                .userId(message.getFromId())
                                                .randomId(random.nextInt(10000))
                                                .keyboard(new Keyboard().setButtons(
                                                        List.of(
                                                                List.of(
                                                                        new KeyboardButton().setAction(
                                                                                new KeyboardButtonAction().setLabel("Добавить группу")
                                                                                        .setType(TemplateActionTypeNames.TEXT)
                                                                        )
                                                                )
                                                        )
                                                ))
                                                .execute();
                                    }
                                } else if (message.getText().equalsIgnoreCase("расписание на сегодня")) {

                                } else {
                                    vk.messages()
                                            .send(actor)
                                            .message("Я вас не понял")
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .execute();
                                }
                            }
                        }
                    }

                    // Обновляем TS
                    ts = vk.messages().getLongPollServer(actor).execute().getTs();

                    Thread.sleep(500);

                } catch (Exception e) {
                    try {
                        Thread.sleep(10000); // Ждем 10 сек при ошибке
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }


}
