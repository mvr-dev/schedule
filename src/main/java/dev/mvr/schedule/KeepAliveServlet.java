package dev.mvr.schedule;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebServlet("/keep-alive")
public class KeepAliveServlet extends HttpServlet {

    private ScheduledExecutorService scheduler;

    @Override
    public void init() {
        System.out.println("🔛 Starting Keep-Alive Service...");
        scheduler = Executors.newSingleThreadScheduledExecutor();

        // Пинг каждые 45 секунд
        scheduler.scheduleAtFixedRate(this::pingServer, 0, 45, TimeUnit.SECONDS);
    }

    @Override
    public void destroy() {
        System.out.println("🔴 Stopping Keep-Alive Service...");
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    private void pingServer() {
        try {
            URL url = new URL("https://schedule-derw.onrender.com/ping");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();
            System.out.println("🏓 Keep-alive ping: " + responseCode);

            connection.disconnect();

        } catch (Exception e) {
            System.out.println("⚠️ Keep-alive error: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("text/plain");
            resp.getWriter().write("Keep-alive service is running! " + new Date());
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }
}