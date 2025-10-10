package dev.mvr.schedule;

import dev.mvr.schedule.service.VkBotService;
import dev.mvr.schedule.utils.RequestUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class BotRunner implements ServletContextListener {
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🚀 Initializing all bots...");
        RequestUtil.getOmsuGroups();

        // Запускаем VK бота в отдельном потоке
        Thread vkThread = new Thread(new VkBotService());
        vkThread.setDaemon(true);
        vkThread.start();
        startSelfPinging();

        // Запускаем Telegram бота в отдельном потоке
//        Thread tgThread = new Thread(new TelegramBotService());
//        tgThread.setDaemon(true);
//        tgThread.start();

        System.out.println("✅ All bots started");
    }
    private void startSelfPinging() {
        scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            try {
                String renderUrl = System.getenv("RENDER_EXTERNAL_URL");
                if (renderUrl == null || renderUrl.isEmpty()) {
                    // Если URL не установлен, пробуем получить из контекста
                    renderUrl = "https://schedule-derw.onrender.com";
                }

                java.net.URL url = new java.net.URL(renderUrl + "/health");
                java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                int responseCode = connection.getResponseCode();
                System.out.println("Self-ping response: " + responseCode + " at " + new java.util.Date());

                connection.disconnect();
            } catch (Exception e) {
                System.out.println("Self-ping failed: " + e.getMessage());
            }
        }, 2, 5, TimeUnit.MINUTES); // Первый пинг через 2 минуты, потом каждые 5 минут
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🛑 Stopping all bots...");

        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
