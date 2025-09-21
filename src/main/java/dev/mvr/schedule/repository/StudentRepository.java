package dev.mvr.schedule.repository;

import dev.mvr.schedule.model.UniversityGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRepository {
    private static Map<Integer, List<UniversityGroup>> studentGroup = new HashMap<>();

    public static List<UniversityGroup> getStudentGroups(int id){
        return studentGroup.getOrDefault(id,new ArrayList<>());
    }
    public static void addGroup(int studentId, UniversityGroup universityGroup){
        studentGroup.getOrDefault(studentId,new ArrayList<>()).add(universityGroup);
    }
    public static void addStudent(int studentId){
        studentGroup.put(studentId,new ArrayList<>());
    }
}
