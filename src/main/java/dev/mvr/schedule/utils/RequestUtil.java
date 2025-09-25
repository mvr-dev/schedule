package dev.mvr.schedule.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mvr.schedule.model.omstu.OmstuGroup;
import dev.mvr.schedule.model.omstu.OmstuLesson;
import dev.mvr.schedule.model.omstu.OmstuScheduleCache;
import dev.mvr.schedule.model.omsu.OmsuGroup;
import dev.mvr.schedule.model.omsu.OmsuSchedule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtil {
    private static Map<String,OmstuGroup> cacheOmstuGroups= new HashMap<>();
    private static List<OmsuGroup> cacheOmsuGroups = null;
    private static Map<String,List<OmsuSchedule>> cacheOmsuSchedule = new HashMap<>();
    private static Map<String, OmstuScheduleCache> cacheOmstuSchedule = new HashMap<>();

    public static OmstuGroup getOmstuGroup(String group){
        OmstuGroup v = cacheOmstuGroups.get(group);
        if (v!=null)
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
            OmstuGroup gr = Utils.getOmstuGroup(response.body());
            if (gr!=null) {
                cacheOmstuGroups.put(group, gr);
                return gr;
            }
            else
                return null;
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

    public static List<OmstuLesson> getOmstuLessons(String group,LocalDate begin){
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(
                        URI.create(
                                String.format("https://rasp.omgtu.ru/api/schedule/group/%d?start=%d.%02d.%02d&finish=%d.%02d.%02d",
                                        getOmstuGroup(group).getId(),
                                        begin.getYear(),
                                        begin.getMonthValue(),
                                        begin.getDayOfMonth(),
                                        begin.plusDays(7).getYear(),
                                        begin.plusDays(7).getMonthValue(),
                                        begin.plusDays(7).getDayOfMonth()
                                )
                        )
                )
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .header("User-Agent", "Java HttpClient")
                .GET()
                .build();
        var gr = cacheOmstuSchedule.get(group);
        if(gr!=null && (Utils.inInterval(begin,gr.getBegin(),begin.plusDays(7)))){
            return gr.getLessons();
        }
        try{
            HttpResponse<String> response =
                    client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            var schedules = Utils.parseOmstuLessons(response.body());
            cacheOmstuSchedule.put(group,new OmstuScheduleCache(begin,begin.plusDays(7),schedules));
            return schedules;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
