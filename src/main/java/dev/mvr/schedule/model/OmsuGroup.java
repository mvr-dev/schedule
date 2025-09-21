package dev.mvr.schedule.model;

public class OmsuGroup {
    int id;
//    @JsonIgnoreProperties()
    Integer real_group_id;
    String name;

    public OmsuGroup(int id, Integer real_group_id, String name) {
        this.id = id;
        this.real_group_id = real_group_id;
        this.name = name;
    }

    public OmsuGroup() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReal_group_id() {
        return real_group_id;
    }

    public void setReal_group_id(Integer real_group_id) {
        this.real_group_id = real_group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
