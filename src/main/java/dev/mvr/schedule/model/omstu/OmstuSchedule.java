package dev.mvr.schedule.model.omstu;

import dev.mvr.schedule.model.omsu.OmsuLesson;

import java.time.LocalDate;
import java.util.*;

public class OmstuSchedule {
    private LocalDate day;
    private List<OmstuLesson> lessons;

    public OmstuSchedule() {
    }

    public OmstuSchedule(LocalDate day, List<OmstuLesson> lessons) {
        this.day = day;
        this.lessons = lessons;
    }

    public LocalDate getDate() {
        return day;
    }

    public List<OmstuLesson> getLessons() {
        return lessons;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public void setLessons(List<OmstuLesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        lessons = lessons.stream().sorted(Comparator.comparing(OmstuLesson::getContentTableOfLessonsOid)).toList();
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\uD83D\uDCC5: %02d.%02d.%d\n\n",day.getDayOfMonth(),day.getMonthValue(),day.getYear()));
        for(OmstuLesson lesson: lessons){
            sb.append(lesson);
            sb.append("\n");
        }
        sb.append('\n');
        return sb.toString();
    }
}
