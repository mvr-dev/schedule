package dev.mvr.schedule;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/bot")
public class BotAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html; charset=UTF-8");

        try {
            String token = System.getenv("VK_BOT_TOKEN");
            String groupId = System.getenv("VK_GROUP_ID");

            if (token == null || groupId == null) {
                resp.getWriter().write("❌ Переменные окружения не настроены");
                return;
            }

            TransportClient transportClient = HttpTransportClient.getInstance();
            VkApiClient vk = new VkApiClient(transportClient);
            GroupActor actor = new GroupActor(Integer.parseInt(groupId), token);

            // Проверяем соединение
            String groupName = vk.groups().getByIdObjectLegacy(actor).execute().get(0).getName();

            resp.getWriter().write("✅ Бот активен<br>");
            resp.getWriter().write("Группа: " + groupName + "<br>");
            resp.getWriter().write("ID группы: " + groupId + "<br>");
            resp.getWriter().write("Токен: " + token.substring(0, 10) + "***<br>");

        } catch (Exception e) {
            resp.getWriter().write("❌ Ошибка: " + e.getMessage());
        }
    }
}