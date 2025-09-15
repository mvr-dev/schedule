package dev.mvr.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mvr.schedule.model.OmstuGroup;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtil {
    private static Map<String,String> cache = new HashMap<>();
    public static String getOmstuGroupJson(String group){
        String v;
        if ((v=cache.get(group))!=null)
            return v;
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(
                        "https://rasp.omgtu.ru/api/search?term=%s&type=group",group)))
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Java HttpClient")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(
                    request, HttpResponse.BodyHandlers.ofString());
            cache.put(group,response.body());
            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
