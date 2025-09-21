package dev.mvr.schedule.model;

import java.util.Objects;

public class UniversityGroup {
    String university;
    String group;

    public UniversityGroup(String university) {
        this.university = university;
        this.group = null;
    }

    public UniversityGroup() {
    }

    public String getUniversity() {
        return university;
    }


    public String getGroup() {
        return group;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniversityGroup that = (UniversityGroup) o;
        return Objects.equals(university, that.university) && Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(university, group);
    }

    @Override
    public String toString() {
        return String.format("%s, %s",university,group);
    }

}
