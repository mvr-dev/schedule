package dev.mvr.schedule.utils;

import dev.mvr.schedule.model.OmsuGroup;
import dev.mvr.schedule.model.OmsuLesson;
import dev.mvr.schedule.model.OmsuSchedule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtil {
    private static Map<String,String> cacheOmstu = new HashMap<>();
    private static List<OmsuGroup> cacheOmsuGroups = null;
    private static Map<String,List<OmsuSchedule>> cacheOmsuSchedule = new HashMap<>();
    public static String getOmstuGroupJson(String group){
        String v;
        if ((v=cacheOmstu.get(group))!=null)
            return v;
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
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
            cacheOmstu.put(group,response.body());
            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<OmsuGroup> getOmsuGroups(){
        System.out.println("get omsu groups");
        if (cacheOmsuGroups==null){
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .build();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://eservice.omsu.ru/schedule/backend/dict/groups"))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "Java HttpClient")
                    .GET()
                    .build();
            try{
                HttpResponse<String> response =
                        client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                cacheOmsuGroups =  Utils.parseOmsuResponse(response.body(), OmsuGroup.class).getData();
            } catch (Exception e) {
                System.out.println(e.getMessage());;
            }
        }
        return cacheOmsuGroups;
    }

    public static List<OmsuSchedule> getOmsuGroupSchedule(String group,boolean needUpdate){
        List<OmsuSchedule> res = cacheOmsuSchedule.get(group);
        if (res==null || needUpdate){
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .build();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(
                            String.format("https://eservice.omsu.ru//schedule/backend/schedule/group/%d",
                                    Utils.groupIdOmsu(group)
                            )))
                    .timeout(Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "Java HttpClient")
                    .GET()
                    .build();
            try{
                HttpResponse<String> response =
                        client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                var schedules = Utils.parseOmsuResponse(response.body(), OmsuSchedule.class)//.getData();
                        .getData();
                schedules.sort(OmsuSchedule::compareTo);
                cacheOmsuSchedule.put(group, schedules);
                return schedules;

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        else{
            return res;
        }
    }
}
