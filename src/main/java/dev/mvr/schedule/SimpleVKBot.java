package dev.mvr.schedule;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class SimpleVKBot implements ServletContextListener {

    private Thread botThread;
    private boolean isRunning = false;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("🚀 Starting VK Bot...");

        isRunning = true;
        botThread = new Thread(this::runBot);
        botThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("🛑 Stopping VK Bot...");
        isRunning = false;
        if (botThread != null) {
            botThread.interrupt();
        }
    }

    private void runBot() {
        try {
            // 1. Инициализация VK API
            VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

            // 2. Получаем токен и ID из переменных окружения
            String token = System.getenv("VK_BOT_TOKEN");
            String groupId = System.getenv("VK_GROUP_ID");

            if (token == null || groupId == null) {
                System.out.println("❌ Set VK_TOKEN and VK_GROUP_ID environment variables!");
                return;
            }

            GroupActor actor = new GroupActor(Integer.parseInt(groupId), token);
            System.out.println("✅ Bot initialized for group: " + groupId);

            // 3. Инициализируем LongPoll
            Integer ts = vk.messages().getLongPollServer(actor).execute().getTs();
            System.out.println("📡 LongPoll started. TS: " + ts);

            // 4. ОСНОВНОЙ ЦИКЛ БОТА - ВСТАВЛЯЕМ СЮДА
            while (isRunning) {
                try {
                    System.out.println("🔍 Checking for messages...");

                    // 5. Запрашиваем новые сообщения
                    var response = vk.messages()
                            .getLongPollHistory(actor)
                            .ts(ts)
                            .execute();

                    // 6. Обрабатываем сообщения
                    if (response.getMessages() != null &&
                            response.getMessages().getItems() != null) {

                        for (var message : response.getMessages().getItems()) {
                            if (message != null &&
                                    message.getText() != null &&
                                    !message.getText().trim().isEmpty() &&
                                    message.getFromId() > 0) { // Игнорируем сообщения от групп

                                System.out.println("💬 New message from " + message.getFromId() + ": " + message.getText());

                                // 7. ПРОСТЕЙШИЙ ОТВЕТ
                                String responseText;
                                String userText = message.getText().toLowerCase();

                                if (userText.contains("привет")) {
                                    responseText = "Привет! 😊 Как дела?";
                                } else if (userText.contains("как дела")) {
                                    responseText = "У меня отлично! Работаю без перебоев! 💪";
                                } else if (userText.contains("команды")) {
                                    responseText = "Я понимаю: привет, как дела, команды";
                                } else {
                                    responseText = "Вы сказали: \"" + message.getText() + "\". Я пока только учусь!";
                                }

                                // 8. Отправляем ответ
                                vk.messages().send(actor)
                                        .peerId(message.getPeerId())
                                        .message(responseText)
                                        .execute();

                                System.out.println("✅ Sent response: " + responseText);
                            }
                        }
                    }

                    // 9. Обновляем timestamp для следующего запроса
                    ts = response.getNewPts();

                    // 10. Ждем 3 секунды перед следующим запросом
                    Thread.sleep(3000);

                } catch (Exception e) {
                    System.out.println("⚠️ Error in bot loop: " + e.getMessage());
                    e.printStackTrace();

                    // При ошибке переинициализируем LongPoll
                    try {
                        ts = vk.messages().getLongPollServer(actor).execute().getTs();
                        System.out.println("🔄 Reinitialized LongPoll. New TS: " + ts);
                    } catch (Exception ex) {
                        System.out.println("❌ Failed to reinitialize LongPoll: " + ex.getMessage());
                    }

                    Thread.sleep(10000); // Ждем 10 секунд при ошибках
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Failed to start bot: " + e.getMessage());
            e.printStackTrace();
        }
    }
}