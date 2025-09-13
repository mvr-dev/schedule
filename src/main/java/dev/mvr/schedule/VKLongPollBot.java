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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

@WebListener
public class VKLongPollBot implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(VKLongPollBot.class);
    private static VkApiClient vk;
    private static GroupActor actor;
    private static Integer ts;
    private static boolean isRunning = false;
    private static Thread botThread;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("Starting VK LongPoll Bot...");

        try {
            // Инициализация VK API
            TransportClient transportClient = HttpTransportClient.getInstance();
            vk = new VkApiClient(transportClient);

            // Получаем токен из переменных окружения
            String token = System.getenv("VK_BOT_TOKEN");
            String groupId = System.getenv("VK_GROUP_ID");

            if (token == null || groupId == null) {
                log.error("VK_BOT_TOKEN или VK_GROUP_ID не установлены!");
                log.error("Установите переменные окружения в Render.com");
                return;
            }

            actor = new GroupActor(Integer.parseInt(groupId), token);
            log.info("VK Bot initialized for group: {}", groupId);

            // Запускаем бота в отдельном потоке
            isRunning = true;
            botThread = new Thread(this::runBot);
            botThread.setDaemon(true);
            botThread.start();

        } catch (Exception e) {
            log.error("Failed to initialize VK Bot", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("Stopping VK LongPoll Bot...");
        isRunning = false;
        if (botThread != null) {
            botThread.interrupt();
        }
    }

    private void runBot() {
        log.info("Bot thread started");

        while (isRunning) {
            try {
                // Получаем новые события
                MessagesGetLongPollHistoryQuery eventsQuery = vk.messages()
                        .getLongPollHistory(actor)
                        .ts(ts);

                if (ts != null) {
                    eventsQuery.ts(ts);
                }

                List<Message> messages = eventsQuery.execute().getMessages().getItems();

                // Обрабатываем каждое сообщение
                for (Message message : messages) {
                    if (message != null) {
                        handleMessage(message);
                    }
                }

                // Обновляем ts для следующего запроса
                ts = vk.messages().getLongPollServer(actor).execute().getTs();

                // Ждем перед следующим запросом
                Thread.sleep(1000);

            } catch (ApiException e) {
                log.error("VK API error: {}", e.getMessage());
                sleep(5000); // Ждем при ошибках API
            } catch (ClientException e) {
                log.error("Client error: {}", e.getMessage());
                sleep(5000);
            } catch (InterruptedException e) {
                log.info("Bot thread interrupted");
                break;
            } catch (Exception e) {
                log.error("Unexpected error", e);
                sleep(10000);
            }
        }
    }

    private void handleMessage(Message message) {
        try {
            String text = message.getText();
            Integer userId = message.getFromId();
            Integer chatId = message.getPeerId();

            log.info("New message from {}: {}", userId, text);

            // Простейшая логика ответа
            String response;

            if (text == null || text.trim().isEmpty()) {
                response = "Привет! Я получил твое сообщение без текста 🎯";
            } else if (text.toLowerCase().contains("привет")) {
                response = "Привет! Как твои дела? 😊";
            } else if (text.toLowerCase().contains("как дела")) {
                response = "У меня все отлично! Работаю без перерыва 💪";
            } else if (text.toLowerCase().contains("погода")) {
                response = "Я пока не умею проверять погоду 🌤️";
            } else {
                response = "Я получил твое сообщение: \"" + text + "\"\n" +
                        "Пока я только учусь отвечать!";
            }

            // Отправляем ответ
            vk.messages().send(actor)
                    .peerId(chatId)
                    .message(response)
                    .execute();

            log.info("Sent response to {}: {}", userId, response);

        } catch (Exception e) {
            log.error("Error handling message", e);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}