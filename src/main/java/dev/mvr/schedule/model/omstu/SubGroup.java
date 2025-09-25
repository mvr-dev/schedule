package dev.mvr.schedule.model.omstu;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class SubGroup {

    private String subgroup;

    @JsonProperty("subgroupGUID")
    private UUID subgroupGuid;

    private Integer subgroupGid;
    private Integer subgroupOid;

    @JsonProperty("subgroupUID")
    private UUID subgroupUid;

    private Integer subgroup_facultyoid;
    private Integer subgroup_groupOid;

    // Конструкторы, геттеры и сеттеры
    public SubGroup() {
    }

    public String getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(String subgroup) {
        this.subgroup = subgroup;
    }

    public UUID getSubgroupGuid() {
        return subgroupGuid;
    }

    public void setSubgroupGuid(UUID subgroupGuid) {
        this.subgroupGuid = subgroupGuid;
    }

    public Integer getSubgroupGid() {
        return subgroupGid;
    }

    public void setSubgroupGid(Integer subgroupGid) {
        this.subgroupGid = subgroupGid;
    }

    public Integer getSubgroupOid() {
        return subgroupOid;
    }

    public void setSubgroupOid(Integer subgroupOid) {
        this.subgroupOid = subgroupOid;
    }

    public UUID getSubgroupUid() {
        return subgroupUid;
    }

    public void setSubgroupUid(UUID subgroupUid) {
        this.subgroupUid = subgroupUid;
    }

    public Integer getSubgroup_facultyoid() {
        return subgroup_facultyoid;
    }

    public void setSubgroup_facultyoid(Integer subgroup_facultyoid) {
        this.subgroup_facultyoid = subgroup_facultyoid;
    }

    public Integer getSubgroup_groupOid() {
        return subgroup_groupOid;
    }

    public void setSubgroup_groupOid(Integer subgroup_groupOid) {
        this.subgroup_groupOid = subgroup_groupOid;
    }
}
