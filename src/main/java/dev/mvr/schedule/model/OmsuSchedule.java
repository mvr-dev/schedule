package dev.mvr.schedule.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

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
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\uD83D\uDCC5: %02d.%02d.%d\n\n",day.getDayOfMonth(),day.getMonthValue(),day.getYear()));
        for(OmsuLesson lesson: lessons){
            sb.append(lesson);
            sb.append("\n");

        }
        return sb.toString();
    }
}
