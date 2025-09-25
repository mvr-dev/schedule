package dev.mvr.schedule.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.mvr.schedule.model.*;
import dev.mvr.schedule.model.omstu.OmstuGroup;
import dev.mvr.schedule.model.omstu.OmstuLesson;
import dev.mvr.schedule.model.omstu.OmstuSchedule;
import dev.mvr.schedule.model.omsu.OmsuGroup;
import dev.mvr.schedule.model.omsu.OmsuLesson;
import dev.mvr.schedule.model.omsu.OmsuResponse;
import dev.mvr.schedule.model.omsu.OmsuSchedule;

import java.time.LocalDate;
import java.util.List;

public class Utils {

    public static int groupIdOmsu(String group){
        var groups = RequestUtil.getOmsuGroups();
        for(OmsuGroup omsuGroup: groups){
            if (omsuGroup.getName().equalsIgnoreCase(group)){
                return omsuGroup.getId();
            }
        }
        return -1;
    }
    public static OmstuGroup getOmstuGroup(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (json==null){
            return null;
        }
        List<OmstuGroup> groups = objectMapper.readValue(json, new TypeReference<List<OmstuGroup>>() {});
        if (groups.isEmpty()){
            return null;
        }
        else{
            return groups.get(0);
        }
    }

    public static <T> OmsuResponse<T> parseOmsuResponse(String response, Class<T> dataType)
            throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        JavaType type = objectMapper.getTypeFactory()
                .constructParametricType(OmsuResponse.class, dataType);

        return objectMapper.readValue(response, type);
    }

    public static int getIndexOfDayInOmsuScheduleList(String group, LocalDate day){
//        LocalDate today = LocalDate.now();
        var omsuGroupScedule = RequestUtil.getOmsuGroupSchedule(group,false);
        int l=0,r=omsuGroupScedule.size()-1;
        int index = (l+r)/2;
        OmsuSchedule onIndex = omsuGroupScedule.get(index);
        while (!onIndex.getDay().equals(day)){
//            System.out.println(index);
            if(onIndex.compareTo(day)==0 || r-l<=1) {
                return index;
            }
            else if(omsuGroupScedule.get(index).compareTo(day) > 0){
//                System.out.println(1);
                r=index;
            }
            else {
//                System.out.println(2);
                l=index;
            }
//            System.out.println(l+" "+r);
            index=(r+l)/2;
        }
        return -1;
    }

    public static Payload parsePayload(String jsonPayload) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(jsonPayload, Payload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing payload", e);
        }
    }

    public static boolean isOmstuPattern(String group){
        String regex = "^[А-ЯЁ]{3}-\\d{3}$";
        return group.matches(regex);
    }
    public static List<OmstuLesson> parseOmstuLessons(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        return objectMapper.readValue(response, new TypeReference<List<OmstuLesson>>() {});
    }

    public static boolean inInterval(LocalDate begin,LocalDate test,LocalDate end){
        return test.equals(begin) || test.equals(end) || (test.isBefore(end) && test.isAfter(begin));
    }


}

