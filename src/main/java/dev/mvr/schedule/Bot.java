package dev.mvr.schedule;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import dev.mvr.schedule.model.UniversityGroup;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class Bot implements ServletContextListener {

    private Thread botThread;
    private boolean isRunning = false;
    Map<Integer, List<UniversityGroup>> studentGroup = new HashMap<>();
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🚀 Starting VK Bot...");
        isRunning = true;
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::keepAlivePing, 0, 45, TimeUnit.SECONDS);


        // ЗАПУСКАЕМ бота в отдельном потоке
        botThread = new Thread(() -> {
            try {
                runBot();
            } catch (Exception e) {
                System.out.println("❌ Bot crashed: " + e.getMessage());
            }
        });

        botThread.start();
        System.out.println("✅ Bot thread started");
    }

    private void keepAlivePing() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://schedule-derw.onrender.com/ping"))
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Java HttpClient")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🛑 Stopping VK Bot...");
        isRunning = false;
        if (scheduler!=null){
            scheduler.shutdown();
        }
        if (botThread != null) {
            botThread.interrupt();
        }
    }

    public void runBot() throws ClientException, ApiException, InterruptedException {

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
        try {
            String groupName = vk.groups().getByIdObjectLegacy(actor).execute().get(0).getName();
        } catch (Exception e) {
            return;
        }

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


        while (isRunning) {
            try {
                MessagesGetLongPollHistoryQuery historyQuery =
                        vk.messages().getLongPollHistory(actor).ts(ts);

                List<Message> messages = historyQuery.execute().getMessages().getItems();

                if (!messages.isEmpty()) {
                    for (Message message : messages) {
                        if (message.getText() != null) {
                            if (message.getText().equalsIgnoreCase("Начать")) {
                                studentGroup.put(message.getFromId(),new ArrayList<>());
                                vk.messages()
                                        .send(actor)
                                        .message("Привет! Выбери университет")
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .keyboard(universityKeyboard)
                                        .execute();
                            } else if (message.getText().equals("ОмГУ")) {
                                studentGroup.get(message.getFromId()).add(new UniversityGroup("ОмГУ"));
                                vk.messages()
                                        .send(actor)
                                        .message("Введи номер группы (полностью)\n например: ХХБ-001-О-01")
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .execute();
                            }
                            else if( message.getText().equals("ОмГТУ")) {
                                studentGroup.get(message.getFromId()).add(new UniversityGroup("ОмГТУ"));
                                vk.messages()
                                        .send(actor)
                                        .message("Введи номер группы")
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .execute();
                            } 
                            else if (message.getText().equalsIgnoreCase("мой универ")) {
                                StringBuilder sb = new StringBuilder("Твои группы:\n");
                                for (UniversityGroup group : studentGroup.get(message.getFromId())){
                                    sb.append(group.toString());
                                }
                                vk.messages()
                                        .send(actor)
                                        .message(sb.toString())
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .execute();

                            } else if (message.getText().equalsIgnoreCase("добавить группу")) {
                                var groups = studentGroup.get(message.getFromId());
                                var group = groups.get(groups.size()-1);
                                if (group.getGroup()!=null)
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
                                            .message("Введи номер группы")
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .execute();

                            }
                             else if (Utils.groupIdOmsu(message.getText())!=-1) {
                                 var groups = studentGroup.get(message.getFromId());
                                 var group = groups.get(groups.size()-1);
                                 if (group.getUniversity().equals("ОмГУ")) {
                                     group.setGroup(message.getText());
                                     vk.messages()
                                             .send(actor)
                                             .message("Группа добавлена")
                                             .userId(message.getFromId())
                                             .randomId(random.nextInt(10000))
                                             .execute();
                                 }
                                 else{
                                     vk.messages()
                                             .send(actor)
                                             .message("Неверная группа для "+group.getUniversity())
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
                            }
                             else if(Utils.getOmstuGroup(message.getText())!=null){
                                var groups = studentGroup.get(message.getFromId());
                                var group = groups.get(groups.size()-1);
                                if (group.getUniversity().equals("ОмГТУ")){
                                    group.setGroup(message.getText());
                                    vk.messages()
                                            .send(actor)
                                            .message("Группа добавлена")
                                            .userId(message.getFromId())
                                            .randomId(random.nextInt(10000))
                                            .execute();
                                }
                                else{
                                    vk.messages()
                                            .send(actor)
                                            .message("Неверная группа для "+group.getUniversity())
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
                            }
                             else if (message.getText().equalsIgnoreCase("расписание на сегодня")){

                            }
                        else {
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
                Thread.sleep(10000); // Ждем 10 сек при ошибке
            }
        }
    }
}