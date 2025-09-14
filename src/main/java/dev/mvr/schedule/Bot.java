package dev.mvr.schedule;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.List;
import java.util.Random;

@WebListener
public class Bot implements ServletContextListener {

    private Thread botThread;
    private boolean isRunning = false;

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
        System.out.println("🤖 runBot() method called");

        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);

        String token = System.getenv("VK_BOT_TOKEN");
        Integer groupId = Integer.parseInt(System.getenv("VK_GROUP_ID"));

        System.out.println("🔑 Token: " + (token != null ? "set" : "null"));
        System.out.println("🏢 Group ID: " + groupId);

        if (token == null) {
            System.out.println("❌ VK_BOT_TOKEN is not set!");
            return;
        }

        GroupActor actor = new GroupActor(groupId, token);
        System.out.println("✅ Bot initialized for group: " + groupId);

        // Тест подключения к VK API
        try {
            String groupName = vk.groups().getByIdObjectLegacy(actor).execute().get(0).getName();
            System.out.println("✅ Connected to group: " + groupName);
        } catch (Exception e) {
            System.out.println("❌ Failed to connect to VK: " + e.getMessage());
            return;
        }

        Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
        Random random = new Random();

        System.out.println("🔍 Starting message checking loop...");

        while (isRunning) {
            try {
                System.out.println("📡 Checking for new messages...");

                MessagesGetLongPollHistoryQuery historyQuery =
                        vk.messages().getLongPollHistory(actor).ts(ts);

                List<Message> messages = historyQuery.execute().getMessages().getItems();

                if (!messages.isEmpty()) {
                    System.out.println("💬 Found " + messages.size() + " messages");

                    for (Message message : messages) {
                        if (message.getText() != null) {
                            System.out.println("📨 Message from " + message.getFromId() + ": " + message.getText());

                            if (message.getText().equals("Привет")) {
                                vk.messages()
                                        .send(actor)
                                        .message("Привет от бота! 🎉")
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .execute();
                                System.out.println("✅ Sent: Привет от бота!");
                            } else {
                                vk.messages()
                                        .send(actor)
                                        .message("Вы сказали: " + message.getText())
                                        .userId(message.getFromId())
                                        .randomId(random.nextInt(10000))
                                        .execute();
                                System.out.println("✅ Echo: " + message.getText());
                            }
                        }
                    }
                } else {
                    System.out.println("📭 No new messages");
                }

                // Обновляем TS
                ts = vk.messages().getLongPollServer(actor).execute().getTs();

                // Ждем 3 секунды
                Thread.sleep(3000);

            } catch (Exception e) {
                System.out.println("⚠️ Error in loop: " + e.getMessage());
                Thread.sleep(10000); // Ждем 10 сек при ошибке
            }
        }
    }
}