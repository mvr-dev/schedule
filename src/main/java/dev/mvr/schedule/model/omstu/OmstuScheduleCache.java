package dev.mvr.schedule.model.omstu;

import java.time.LocalDate;
import java.util.List;

public class OmstuScheduleCache {
    LocalDate begin;
    LocalDate end;
    List<OmstuLesson> lessons;

    public OmstuScheduleCache(LocalDate begin, LocalDate end, List<OmstuLesson> lessons) {
        this.begin = begin;
        this.end = end;
        this.lessons = lessons;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public List<OmstuLesson> getLessons() {
        return lessons;
    }
}
