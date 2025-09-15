package dev.mvr.schedule;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

@WebServlet("/ping")
public class PingServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("text/plain");
            resp.getWriter().write("pong - " + new Date());
            System.out.println("🏓 Ping received at " + new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}