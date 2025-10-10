package dev.mvr.schedule.model.omstu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OmstuScheduleCache {
    private Map<LocalDate, List<OmstuLesson>> dateToLessons;

    public OmstuScheduleCache(Map<LocalDate, List<OmstuLesson>> dateToLessons) {
        this.dateToLessons = dateToLessons;
    }

    public  List<OmstuLesson> getByDate(LocalDate date){
        return dateToLessons.get(date);
    }
    public void addByDate(LocalDate date, OmstuLesson lesson){
        var a = dateToLessons.get(date);
        if (a==null){
            dateToLessons.put(date,new ArrayList<>(List.of(lesson)));
        }
        else{
            a.add(lesson);
        }
    }
    public boolean containsDay(LocalDate date){
        return dateToLessons.get(date)!=null;
    }
    public List<OmstuLesson> getLessons(LocalDate date){
        return dateToLessons.getOrDefault(date,new ArrayList<>());
    }
}
