package dev.mvr.schedule.model.omstu;

import java.util.Objects;

public class OmstuGroup {
    private long id;
    private String label;
    private String description;
    private String type;

    public OmstuGroup() {
    }

    public OmstuGroup(long id, String label, String description, String type) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OmstuGroup that = (OmstuGroup) o;
        return id == that.id && Objects.equals(label, that.label) && Objects.equals(description, that.description) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, description, type);
    }

    @Override
    public String toString() {
        return "OmstuGroup{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
