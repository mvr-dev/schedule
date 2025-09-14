package dev.mvr.schedule;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.*;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.*;

@WebListener
public class Bot implements ServletContextListener {

    private Thread botThread;
    private boolean isRunning = false;
    private static final Map<Integer, String> userUniversity = new HashMap<>();
    private static final Map<Integer, String> userGroup = new HashMap<>();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🚀 Starting VK Bot...");
        isRunning = true;

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

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🛑 Stopping VK Bot...");
        isRunning = false;
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

        GroupActor actor = new GroupActor(groupId, token);
        // Тест подключения к VK API
        try {
            String groupName = vk.groups().getByIdObjectLegacy(actor).execute().get(0).getName();
        } catch (Exception e) {
            return;
        }

        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
        Random random = new Random();
        while (isRunning) {
            try {
                MessagesGetLongPollHistoryQuery historyQuery =
                        vk.messages().getLongPollHistory(actor).ts(ts);

                List<Message> messages = historyQuery.execute().getMessages().getItems();

                if (!messages.isEmpty()) {
                    for (Message message : messages) {
                        if (message.getText() != null) {
                            if (message.getText().equals("Начать")) {
                                Keyboard keyboard = new Keyboard().setOneTime(true);
                                List<List<KeyboardButton>> allKeys = new ArrayList<>();
                                List<KeyboardButton> line1 = new ArrayList<>();
                                line1.add(new KeyboardButton().setAction(
                                        new KeyboardButtonAction().setLabel("ОмГУ")
                                                .setType(TemplateActionTypeNames.TEXT)
                                ));
                                line1.add(new KeyboardButton().setAction(
                                        new KeyboardButtonAction().setLabel("ОмГТУ")
                                                .setType(TemplateActionTypeNames.TEXT)
                                ));
                                allKeys.add(line1);
                                keyboard.setButtons(allKeys);
                                vk.messages()
                                        .send(actor)
                                        .message("Привет выбери университет")
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .keyboard(keyboard)
                                        .execute();
                            } else if (message.getText().equals("ОмГУ")) {
                                userUniversity.put(message.getFromId(),"ОмГУ");}
                            else if( message.getText().equals("ОмГТУ")){
                                userUniversity.put(message.getFromId(),"ОмГТУ");
                            } else if (message.getText().equals("мой универ")) {
                                vk.messages()
                                        .send(actor)
                                        .message(userUniversity.getOrDefault(message.getFromId(),"Университет не указан"))
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .execute();

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

                // Ждем 3 секунды
                Thread.sleep(1000);

            } catch (Exception e) {
                Thread.sleep(10000); // Ждем 10 сек при ошибке
            }
        }
    }
}