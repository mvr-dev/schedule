package dev.mvr.schedule;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

    @WebListener
    public class SimpleVKBot implements ServletContextListener {

        private Thread botThread;
        private boolean isRunning = false;

        @Override
        public void contextInitialized(ServletContextEvent event) {
            System.out.println("🚀 Запускаем VK бота...");
            isRunning = true;
            botThread = new Thread(this::runBot);
            botThread.start();
        }

        @Override
        public void contextDestroyed(ServletContextEvent event) {
            System.out.println("🛑 Останавливаем бота...");
            isRunning = false;
            if (botThread != null) botThread.interrupt();
        }

        private void runBot() {
            try {
                // 1. Настройка клиента VK
                VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

                // 2. Ваши данные из ВК
                String token = System.getenv("VK_BOT_TOKEN");
                Integer groupId = Integer.parseInt(System.getenv("VK_GROUP_ID"));

                GroupActor actor = new GroupActor(groupId, token);
                System.out.println("✅ Бот запущен для группы ID: " + groupId);

                // 3. Основной цикл бота
                while (isRunning) {
                    try {
                        // 4. Получаем свежий TS
                        var server = vk.messages().getLongPollServer(actor).execute();
                        int ts = server.getTs();

                        // 5. Проверяем новые сообщения
                        var response = vk.messages()
                                .getLongPollHistory(actor)
                                .ts(ts)
                                .execute();

                        // 6. Обрабатываем сообщения
                        if (response.getMessages() != null) {
                            for (var message : response.getMessages().getItems()) {
                                if (message != null && message.getText() != null) {
                                    handleMessage(vk, actor, message);
                                }
                            }
                        }

                        // 7. Ждем 3 секунды
                        Thread.sleep(3000);

                    } catch (Exception e) {
                        System.out.println("⚠️ Ошибка: " + e.getMessage());
                        Thread.sleep(10000); // Ждем 10 сек при ошибке
                    }
                }

            } catch (Exception e) {
                System.out.println("❌ Фатальная ошибка: " + e.getMessage());
            }
        }

        private void handleMessage(VkApiClient vk, GroupActor actor, Message message) {
            try {
                String text = message.getText().toLowerCase();
                String response;
                System.out.println(text);
                // Простейшая логика ответов
                if (text.contains("привет")) {
                    response = "Привет! 😊";
                } else if (text.contains("как дела")) {
                    response = "Отлично! А у тебя?";
                } else {
                    response = "Я получил: " + message.getText();
                }

                // Отправляем ответ
                vk.messages().send(actor)
                        .peerId(message.getPeerId())
                        .message(response)
                        .execute();

                System.out.println("💬 Ответил: " + response);

            } catch (Exception e) {
                System.out.println("❌ Ошибка отправки: " + e.getMessage());
            }
        }
    }
