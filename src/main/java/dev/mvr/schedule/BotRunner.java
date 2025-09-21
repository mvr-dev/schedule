package dev.mvr.schedule;

import dev.mvr.schedule.service.VkBotService;
import dev.mvr.schedule.utils.RequestUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class BotRunner implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("🚀 Initializing all bots...");
        RequestUtil.getOmsuGroups();

        // Запускаем VK бота в отдельном потоке
        Thread vkThread = new Thread(new VkBotService());
        vkThread.setDaemon(true);
        vkThread.start();

        // Запускаем Telegram бота в отдельном потоке
//        Thread tgThread = new Thread(new TelegramBotService());
//        tgThread.setDaemon(true);
//        tgThread.start();

        System.out.println("✅ All bots started");
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("🛑 Stopping all bots...");
    }
}
