package dev.mvr.schedule.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OmsuLesson {
    long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    LocalDate day;
    int week;
    int time;
    String faculty;
    String lesson;
    String type_work;
    long lesson_id;
    String teacher;
    long teacher_id;
    String group;
    long group_id;
    String auditCorps;
    long auditory_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    LocalDateTime publishDate;
    String subgroupName;

    public String getSubgroupName() {
        return subgroupName;
    }

    public void setSubgroupName(String subgroupName) {
        this.subgroupName = subgroupName;
    }

    public OmsuLesson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getType_work() {
        return type_work;
    }

    public void setType_work(String type_work) {
        this.type_work = type_work;
    }

    public long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(long group_id) {
        this.group_id = group_id;
    }

    public String getAuditCorps() {
        return auditCorps;
    }

    public void setAuditCorps(String auditCorps) {
        this.auditCorps = auditCorps;
    }

    public long getAuditory_id() {
        return auditory_id;
    }

    public void setAuditory_id(long auditory_id) {
        this.auditory_id = auditory_id;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public String toString() {
        if (teacher!=null && auditCorps!=null)
            return String.format(
                "\uD83D\uDCDA: %s\n\uD83D\uDC68\u200D\uD83C\uDFEB: %s\n\uD83C\uDFDB: %s\n",
                lesson,
                teacher,
                auditCorps
        );
        else
            return lesson;
    }
}
