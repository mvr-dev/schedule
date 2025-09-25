package dev.mvr.schedule.model.omstu;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public class Lecturer {

    private String lecturer;

    @JsonProperty("lecturerASAV_UID")
    private UUID lecturerAsavUid;

    @JsonProperty("lecturerCustomUID")
    private UUID lecturerCustomUid;

    private String lecturerEmail;

    @JsonProperty("lecturerGUID")
    private UUID lecturerGuid;

    @JsonProperty("lecturerMDM_UID")
    private UUID lecturerMdmUid;

    private Integer lecturerOid;

    @JsonProperty("lecturerUID")
    private UUID lecturerUid;

    @JsonProperty("lecturer_postASAV_UID")
    private UUID lecturerPostAsavUid;

    @JsonProperty("lecturer_postMDM_UID")
    private UUID lecturerPostMdmUid;

    @JsonProperty("lecturer_postUID")
    private UUID lecturerPostUid;

    private Integer lecturer_post_oid;
    private String lecturer_rank;
    private String lecturer_title;

    @JsonProperty("personHR_Person_ID")
    private UUID personHrPersonId;

    @JsonProperty("personMDM_Person_UID")
    private UUID personMdmPersonUid;

    // Конструкторы, геттеры и сеттеры
    public Lecturer() {
    }

    // Геттеры и сеттеры (аналогично основному классу)
    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public UUID getLecturerAsavUid() {
        return lecturerAsavUid;
    }

    public void setLecturerAsavUid(UUID lecturerAsavUid) {
        this.lecturerAsavUid = lecturerAsavUid;
    }

    public UUID getLecturerCustomUid() {
        return lecturerCustomUid;
    }

    public void setLecturerCustomUid(UUID lecturerCustomUid) {
        this.lecturerCustomUid = lecturerCustomUid;
    }

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public void setLecturerEmail(String lecturerEmail) {
        this.lecturerEmail = lecturerEmail;
    }

    public UUID getLecturerGuid() {
        return lecturerGuid;
    }

    public void setLecturerGuid(UUID lecturerGuid) {
        this.lecturerGuid = lecturerGuid;
    }

    public UUID getLecturerMdmUid() {
        return lecturerMdmUid;
    }

    public void setLecturerMdmUid(UUID lecturerMdmUid) {
        this.lecturerMdmUid = lecturerMdmUid;
    }

    public Integer getLecturerOid() {
        return lecturerOid;
    }

    public void setLecturerOid(Integer lecturerOid) {
        this.lecturerOid = lecturerOid;
    }

    public UUID getLecturerUid() {
        return lecturerUid;
    }

    public void setLecturerUid(UUID lecturerUid) {
        this.lecturerUid = lecturerUid;
    }

    public UUID getLecturerPostAsavUid() {
        return lecturerPostAsavUid;
    }

    public void setLecturerPostAsavUid(UUID lecturerPostAsavUid) {
        this.lecturerPostAsavUid = lecturerPostAsavUid;
    }

    public UUID getLecturerPostMdmUid() {
        return lecturerPostMdmUid;
    }

    public void setLecturerPostMdmUid(UUID lecturerPostMdmUid) {
        this.lecturerPostMdmUid = lecturerPostMdmUid;
    }

    public UUID getLecturerPostUid() {
        return lecturerPostUid;
    }

    public void setLecturerPostUid(UUID lecturerPostUid) {
        this.lecturerPostUid = lecturerPostUid;
    }

    public Integer getLecturer_post_oid() {
        return lecturer_post_oid;
    }

    public void setLecturer_post_oid(Integer lecturer_post_oid) {
        this.lecturer_post_oid = lecturer_post_oid;
    }

    public String getLecturer_rank() {
        return lecturer_rank;
    }

    public void setLecturer_rank(String lecturer_rank) {
        this.lecturer_rank = lecturer_rank;
    }

    public String getLecturer_title() {
        return lecturer_title;
    }

    public void setLecturer_title(String lecturer_title) {
        this.lecturer_title = lecturer_title;
    }

    public UUID getPersonHrPersonId() {
        return personHrPersonId;
    }

    public void setPersonHrPersonId(UUID personHrPersonId) {
        this.personHrPersonId = personHrPersonId;
    }

    public UUID getPersonMdmPersonUid() {
        return personMdmPersonUid;
    }

    public void setPersonMdmPersonUid(UUID personMdmPersonUid) {
        this.personMdmPersonUid = personMdmPersonUid;
    }
}