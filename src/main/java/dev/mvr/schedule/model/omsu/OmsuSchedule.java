package dev.mvr.schedule.model.omsu;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.*;

public class OmsuSchedule implements Comparable<OmsuSchedule> {
    List<OmsuLesson> lessons;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", locale = "ru")
    LocalDate day;

    public OmsuSchedule() {
    }

    public List<OmsuLesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<OmsuLesson> lessons) {
        this.lessons = lessons;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    @Override
    public int compareTo(OmsuSchedule o) {
        return this.getDay().compareTo(o.getDay());
    }
    public int compareTo(LocalDate o) {
        return this.getDay().compareTo(o);
    }

    @Override
    public String toString() {
        String[] time = {"8:45-10:20","10:30-12:05","12:45-14:20",
                "14:30-16:05","16:15-17:50","18:00-19:35","19:45-21:20"};
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\uD83D\uDCC5: %02d.%02d.%d\n\n",day.getDayOfMonth(),day.getMonthValue(),day.getYear()));
        Map<Integer,List<OmsuLesson>> map = new LinkedHashMap<>();
        for(OmsuLesson lesson: lessons){
            var key = map.get(lesson.time);
            if(key!=null){
                key.add(lesson);
            }
            else {
                map.put(lesson.time,new ArrayList<>(List.of(lesson)));
            }
        }
        for (int i = 0; i < 7; i++) {
            var lessons = map.get(i+1);
            sb.append(String.format("\uD83D\uDD70\uFE0F: %s\n",time[i]));
            if(lessons!=null){
                if(lessons.size()==1){
                    sb.append(lessons.get(0));
//                    sb.append("\n");
                }
                else if(lessons.size()>2 || lessons.get(0).getLesson().equals(lessons.get(1).getLesson())){
                    sb.append(String.format("\uD83D\uDCDA: %s\n",lessons.get(0).getLesson()));
                    for(OmsuLesson lesson:lessons){
                        sb.append(String.format("\uD83D\uDC68\u200D\uD83C\uDFEB: %s\n\uD83C\uDFDB: %s\n",lesson.getTeacher(),lesson.getAuditCorps()));
                    }
                }
                else{
                    sb.append(String.format(
                            "\uD83D\uDCDA: %s/\n%s\n\uD83D\uDC68\u200D\uD83C\uDFEB: %s/\n%s\n\uD83C\uDFDB: %s/\n%s\n",
                            lessons.get(0).getLesson(),lessons.get(1).getLesson(),
                            lessons.get(0).getTeacher(),lessons.get(1).getTeacher(),
                            lessons.get(0).getAuditCorps(),lessons.get(0).getAuditCorps())
                    );
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
