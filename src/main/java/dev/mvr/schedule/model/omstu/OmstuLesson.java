package dev.mvr.schedule.model.omstu;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OmstuLesson implements Comparable<OmstuLesson> {

        @JsonProperty("SP_DisciplineInPlan_ID")
        private Integer spDisciplineInPlanId;

        private String auditorium;
        private Integer auditoriumAmount;

        @JsonProperty("auditoriumGUID")
        private UUID auditoriumGuid;

        private Integer auditoriumOid;
        private Integer auditoriumfloor;
        private String author;
        private String beginLesson;
        private String building;
        private Integer buildingGid;
        private Integer buildingOid;
        private Integer contentOfLoadOid;

        @JsonProperty("contentOfLoadUID")
        private UUID contentOfLoadUid;

        private String contentTableOfLessonsName;
        private Integer contentTableOfLessonsOid;
        private Integer courseContent;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z00:00'")
        private LocalDateTime createddate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
        private LocalDate date;
        private String dateOfNest;
        private Integer dayOfWeek;
        private String dayOfWeekString;
        private String detailInfo;
        private String discipline;

        @JsonProperty("disciplineASAV_UID")
        private UUID disciplineAsavUid;

        @JsonProperty("disciplineMDM_UID")
        private UUID disciplineMdmUid;

        private Integer disciplineOid;

        @JsonProperty("disciplineUid")
        private UUID disciplineUid;

        private String disciplineinplan;
        private Integer disciplinetypeload;
        private Integer duration;
        private String endLesson;
        private String group;

        @JsonProperty("groupASAV_UID")
        private UUID groupAsavUid;

        @JsonProperty("groupGUID")
        private UUID groupGuid;

        @JsonProperty("groupHR_UID")
        private UUID groupHrUid;

        @JsonProperty("groupMDM_UID")
        private UUID groupMdmUid;

        private Integer groupOid;

        @JsonProperty("groupSP_Uid")
        private UUID groupSpUid;

        @JsonProperty("groupUID")
        private UUID groupUid;

        private String group_facultyASAV_UID;
        private String group_facultyHR_UID;
        private String group_facultyUID;

        @JsonProperty("group_faculty_SP_Uid")
        private UUID groupFacultySpUid;

        private String group_facultyname;
        private Integer group_facultyoid;
        private Integer hideincapacity;
        @JsonProperty("isBan")
        private Boolean isBan;
        private String kindOfWork;
        private Integer kindOfWorkComplexity;
        private Integer kindOfWorkOid;

        @JsonProperty("kindOfWorkUid")
        private UUID kindOfWorkUid;

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
        private Integer lessonNumberEnd;
        private Integer lessonNumberStart;
        private Integer lessonOid;
        private List<Object> listGroups;
        private List<Lecturer> listOfLecturers;
        private List<SubGroup> listSubGroups;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z00:00'")
        private LocalDateTime modifieddate;

        private String note;
        private String note_description;
        private Integer openlesson;
        private Integer packageNumber;
        private Integer parentSchedule_Status;
        private String parentschedule;

        @JsonProperty("personHR_Person_ID")
        private UUID personHrPersonId;

        @JsonProperty("personMDM_Person_UID")
        private UUID personMdmPersonUid;

        private String replaces;
        private String specializationContent;
        private String specialization_name;
        private Integer specialization_oid;
        private String stream;
        private Integer streamOid;

        @JsonProperty("streamUid")
        private UUID streamUid;

        private Integer stream_facultyoid;
        private String subGroup;
        private Integer subGroupOid;

        @JsonProperty("subgroupGUID")
        private UUID subgroupGuid;

        @JsonProperty("subgroupUid")
        private UUID subgroupUid;

        private Integer subgroup_facultyoid;
        private Integer subgroup_groupOid;
        private String subject;
        private String tableofLessonsName;
        private Integer tableofLessonsOid;
        private Integer typeOfContingent;
        private String url1;
        private String url1_description;
        private String url2;
        private String url2_description;

        // Конструкторы
        public OmstuLesson() {
        }

        // Геттеры и сеттеры
        public Integer getSpDisciplineInPlanId() {
            return spDisciplineInPlanId;
        }

        public void setSpDisciplineInPlanId(Integer spDisciplineInPlanId) {
            this.spDisciplineInPlanId = spDisciplineInPlanId;
        }

        public String getAuditorium() {
            return auditorium;
        }

        public void setAuditorium(String auditorium) {
            this.auditorium = auditorium;
        }

        public Integer getAuditoriumAmount() {
            return auditoriumAmount;
        }

        public void setAuditoriumAmount(Integer auditoriumAmount) {
            this.auditoriumAmount = auditoriumAmount;
        }

        public UUID getAuditoriumGuid() {
            return auditoriumGuid;
        }

        public void setAuditoriumGuid(UUID auditoriumGuid) {
            this.auditoriumGuid = auditoriumGuid;
        }

        public Integer getAuditoriumOid() {
            return auditoriumOid;
        }

        public void setAuditoriumOid(Integer auditoriumOid) {
            this.auditoriumOid = auditoriumOid;
        }

        public Integer getAuditoriumfloor() {
            return auditoriumfloor;
        }

        public void setAuditoriumfloor(Integer auditoriumfloor) {
            this.auditoriumfloor = auditoriumfloor;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getBeginLesson() {
            return beginLesson;
        }

        public void setBeginLesson(String beginLesson) {
            this.beginLesson = beginLesson;
        }

        public String getBuilding() {
            return building;
        }

        public void setBuilding(String building) {
            this.building = building;
        }

        public Integer getBuildingGid() {
            return buildingGid;
        }

        public void setBuildingGid(Integer buildingGid) {
            this.buildingGid = buildingGid;
        }

        public Integer getBuildingOid() {
            return buildingOid;
        }

        public void setBuildingOid(Integer buildingOid) {
            this.buildingOid = buildingOid;
        }

        public Integer getContentOfLoadOid() {
            return contentOfLoadOid;
        }

        public void setContentOfLoadOid(Integer contentOfLoadOid) {
            this.contentOfLoadOid = contentOfLoadOid;
        }

        public UUID getContentOfLoadUid() {
            return contentOfLoadUid;
        }

        public void setContentOfLoadUid(UUID contentOfLoadUid) {
            this.contentOfLoadUid = contentOfLoadUid;
        }

        public String getContentTableOfLessonsName() {
            return contentTableOfLessonsName;
        }

        public void setContentTableOfLessonsName(String contentTableOfLessonsName) {
            this.contentTableOfLessonsName = contentTableOfLessonsName;
        }

        public Integer getContentTableOfLessonsOid() {
            return contentTableOfLessonsOid;
        }

        public void setContentTableOfLessonsOid(Integer contentTableOfLessonsOid) {
            this.contentTableOfLessonsOid = contentTableOfLessonsOid;
        }

        public Integer getCourseContent() {
            return courseContent;
        }

        public void setCourseContent(Integer courseContent) {
            this.courseContent = courseContent;
        }

        public LocalDateTime getCreateddate() {
            return createddate;
        }

        public void setCreateddate(LocalDateTime createddate) {
            this.createddate = createddate;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public String getDateOfNest() {
            return dateOfNest;
        }

        public void setDateOfNest(String dateOfNest) {
            this.dateOfNest = dateOfNest;
        }

        public Integer getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(Integer dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public String getDayOfWeekString() {
            return dayOfWeekString;
        }

        public void setDayOfWeekString(String dayOfWeekString) {
            this.dayOfWeekString = dayOfWeekString;
        }

        public String getDetailInfo() {
            return detailInfo;
        }

        public void setDetailInfo(String detailInfo) {
            this.detailInfo = detailInfo;
        }

        public String getDiscipline() {
            return discipline;
        }

        public void setDiscipline(String discipline) {
            this.discipline = discipline;
        }

        public UUID getDisciplineAsavUid() {
            return disciplineAsavUid;
        }

        public void setDisciplineAsavUid(UUID disciplineAsavUid) {
            this.disciplineAsavUid = disciplineAsavUid;
        }

        public UUID getDisciplineMdmUid() {
            return disciplineMdmUid;
        }

        public void setDisciplineMdmUid(UUID disciplineMdmUid) {
            this.disciplineMdmUid = disciplineMdmUid;
        }

        public Integer getDisciplineOid() {
            return disciplineOid;
        }

        public void setDisciplineOid(Integer disciplineOid) {
            this.disciplineOid = disciplineOid;
        }

        public UUID getDisciplineUid() {
            return disciplineUid;
        }

        public void setDisciplineUid(UUID disciplineUid) {
            this.disciplineUid = disciplineUid;
        }

        public String getDisciplineinplan() {
            return disciplineinplan;
        }

        public void setDisciplineinplan(String disciplineinplan) {
            this.disciplineinplan = disciplineinplan;
        }

        public Integer getDisciplinetypeload() {
            return disciplinetypeload;
        }

        public void setDisciplinetypeload(Integer disciplinetypeload) {
            this.disciplinetypeload = disciplinetypeload;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getEndLesson() {
            return endLesson;
        }

        public void setEndLesson(String endLesson) {
            this.endLesson = endLesson;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public UUID getGroupAsavUid() {
            return groupAsavUid;
        }

        public void setGroupAsavUid(UUID groupAsavUid) {
            this.groupAsavUid = groupAsavUid;
        }

        public UUID getGroupGuid() {
            return groupGuid;
        }

        public void setGroupGuid(UUID groupGuid) {
            this.groupGuid = groupGuid;
        }

        public UUID getGroupHrUid() {
            return groupHrUid;
        }

        public void setGroupHrUid(UUID groupHrUid) {
            this.groupHrUid = groupHrUid;
        }

        public UUID getGroupMdmUid() {
            return groupMdmUid;
        }

        public void setGroupMdmUid(UUID groupMdmUid) {
            this.groupMdmUid = groupMdmUid;
        }

        public Integer getGroupOid() {
            return groupOid;
        }

        public void setGroupOid(Integer groupOid) {
            this.groupOid = groupOid;
        }

        public UUID getGroupSpUid() {
            return groupSpUid;
        }

        public void setGroupSpUid(UUID groupSpUid) {
            this.groupSpUid = groupSpUid;
        }

        public UUID getGroupUid() {
            return groupUid;
        }

        public void setGroupUid(UUID groupUid) {
            this.groupUid = groupUid;
        }

        public String getGroup_facultyASAV_UID() {
            return group_facultyASAV_UID;
        }

        public void setGroup_facultyASAV_UID(String group_facultyASAV_UID) {
            this.group_facultyASAV_UID = group_facultyASAV_UID;
        }

        public String getGroup_facultyHR_UID() {
            return group_facultyHR_UID;
        }

        public void setGroup_facultyHR_UID(String group_facultyHR_UID) {
            this.group_facultyHR_UID = group_facultyHR_UID;
        }

        public String getGroup_facultyUID() {
            return group_facultyUID;
        }

        public void setGroup_facultyUID(String group_facultyUID) {
            this.group_facultyUID = group_facultyUID;
        }

        public UUID getGroupFacultySpUid() {
            return groupFacultySpUid;
        }

        public void setGroupFacultySpUid(UUID groupFacultySpUid) {
            this.groupFacultySpUid = groupFacultySpUid;
        }

        public String getGroup_facultyname() {
            return group_facultyname;
        }

        public void setGroup_facultyname(String group_facultyname) {
            this.group_facultyname = group_facultyname;
        }

        public Integer getGroup_facultyoid() {
            return group_facultyoid;
        }

        public void setGroup_facultyoid(Integer group_facultyoid) {
            this.group_facultyoid = group_facultyoid;
        }

        public Integer getHideincapacity() {
            return hideincapacity;
        }

        public void setHideincapacity(Integer hideincapacity) {
            this.hideincapacity = hideincapacity;
        }

        public Boolean getBan() {
            return isBan;
        }

        public void setBan(Boolean ban) {
            isBan = ban;
        }

        public String getKindOfWork() {
            return kindOfWork;
        }

        public void setKindOfWork(String kindOfWork) {
            this.kindOfWork = kindOfWork;
        }

        public Integer getKindOfWorkComplexity() {
            return kindOfWorkComplexity;
        }

        public void setKindOfWorkComplexity(Integer kindOfWorkComplexity) {
            this.kindOfWorkComplexity = kindOfWorkComplexity;
        }

        public Integer getKindOfWorkOid() {
            return kindOfWorkOid;
        }

        public void setKindOfWorkOid(Integer kindOfWorkOid) {
            this.kindOfWorkOid = kindOfWorkOid;
        }

        public UUID getKindOfWorkUid() {
            return kindOfWorkUid;
        }

        public void setKindOfWorkUid(UUID kindOfWorkUid) {
            this.kindOfWorkUid = kindOfWorkUid;
        }

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

        public Integer getLessonNumberEnd() {
            return lessonNumberEnd;
        }

        public void setLessonNumberEnd(Integer lessonNumberEnd) {
            this.lessonNumberEnd = lessonNumberEnd;
        }

        public Integer getLessonNumberStart() {
            return lessonNumberStart;
        }

        public void setLessonNumberStart(Integer lessonNumberStart) {
            this.lessonNumberStart = lessonNumberStart;
        }

        public Integer getLessonOid() {
            return lessonOid;
        }

        public void setLessonOid(Integer lessonOid) {
            this.lessonOid = lessonOid;
        }

        public List<Object> getListGroups() {
            return listGroups;
        }

        public void setListGroups(List<Object> listGroups) {
            this.listGroups = listGroups;
        }

        public List<Lecturer> getListOfLecturers() {
            return listOfLecturers;
        }

        public void setListOfLecturers(List<Lecturer> listOfLecturers) {
            this.listOfLecturers = listOfLecturers;
        }

        public List<SubGroup> getListSubGroups() {
            return listSubGroups;
        }

        public void setListSubGroups(List<SubGroup> listSubGroups) {
            this.listSubGroups = listSubGroups;
        }

        public LocalDateTime getModifieddate() {
            return modifieddate;
        }

        public void setModifieddate(LocalDateTime modifieddate) {
            this.modifieddate = modifieddate;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getNote_description() {
            return note_description;
        }

        public void setNote_description(String note_description) {
            this.note_description = note_description;
        }

        public Integer getOpenlesson() {
            return openlesson;
        }

        public void setOpenlesson(Integer openlesson) {
            this.openlesson = openlesson;
        }

        public Integer getPackageNumber() {
            return packageNumber;
        }

        public void setPackageNumber(Integer packageNumber) {
            this.packageNumber = packageNumber;
        }

        public Integer getParentSchedule_Status() {
            return parentSchedule_Status;
        }

        public void setParentSchedule_Status(Integer parentSchedule_Status) {
            this.parentSchedule_Status = parentSchedule_Status;
        }

        public String getParentschedule() {
            return parentschedule;
        }

        public void setParentschedule(String parentschedule) {
            this.parentschedule = parentschedule;
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

        public String getReplaces() {
            return replaces;
        }

        public void setReplaces(String replaces) {
            this.replaces = replaces;
        }

        public String getSpecializationContent() {
            return specializationContent;
        }

        public void setSpecializationContent(String specializationContent) {
            this.specializationContent = specializationContent;
        }

        public String getSpecialization_name() {
            return specialization_name;
        }

        public void setSpecialization_name(String specialization_name) {
            this.specialization_name = specialization_name;
        }

        public Integer getSpecialization_oid() {
            return specialization_oid;
        }

        public void setSpecialization_oid(Integer specialization_oid) {
            this.specialization_oid = specialization_oid;
        }

        public String getStream() {
            return stream;
        }

        public void setStream(String stream) {
            this.stream = stream;
        }

        public Integer getStreamOid() {
            return streamOid;
        }

        public void setStreamOid(Integer streamOid) {
            this.streamOid = streamOid;
        }

        public UUID getStreamUid() {
            return streamUid;
        }

        public void setStreamUid(UUID streamUid) {
            this.streamUid = streamUid;
        }

        public Integer getStream_facultyoid() {
            return stream_facultyoid;
        }

        public void setStream_facultyoid(Integer stream_facultyoid) {
            this.stream_facultyoid = stream_facultyoid;
        }

        public String getSubGroup() {
            return subGroup;
        }

        public void setSubGroup(String subGroup) {
            this.subGroup = subGroup;
        }

        public Integer getSubGroupOid() {
            return subGroupOid;
        }

        public void setSubGroupOid(Integer subGroupOid) {
            this.subGroupOid = subGroupOid;
        }

        public UUID getSubgroupGuid() {
            return subgroupGuid;
        }

        public void setSubgroupGuid(UUID subgroupGuid) {
            this.subgroupGuid = subgroupGuid;
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

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTableofLessonsName() {
            return tableofLessonsName;
        }

        public void setTableofLessonsName(String tableofLessonsName) {
            this.tableofLessonsName = tableofLessonsName;
        }

        public Integer getTableofLessonsOid() {
            return tableofLessonsOid;
        }

        public void setTableofLessonsOid(Integer tableofLessonsOid) {
            this.tableofLessonsOid = tableofLessonsOid;
        }

        public Integer getTypeOfContingent() {
            return typeOfContingent;
        }

        public void setTypeOfContingent(Integer typeOfContingent) {
            this.typeOfContingent = typeOfContingent;
        }

        public String getUrl1() {
            return url1;
        }

        public void setUrl1(String url1) {
            this.url1 = url1;
        }

        public String getUrl1_description() {
            return url1_description;
        }

        public void setUrl1_description(String url1_description) {
            this.url1_description = url1_description;
        }

        public String getUrl2() {
            return url2;
        }

        public void setUrl2(String url2) {
            this.url2 = url2;
        }

        public String getUrl2_description() {
            return url2_description;
        }

        public void setUrl2_description(String url2_description) {
            this.url2_description = url2_description;
        }

    @Override
    public String toString() {
        return String.format(
                "\uD83D\uDD70\uFE0F: %s\n\uD83D\uDCDA: %s\n\uD83D\uDC68\u200D\uD83C\uDFEB: %s\n\uD83C\uDFDB: %s\n№%d\n\n",
                beginLesson+" - "+endLesson,
                discipline,
                lecturer,
                auditorium,
                contentTableOfLessonsOid
                );
    }


    @Override
    public int compareTo(OmstuLesson o) {
        return this.getContentTableOfLessonsOid().compareTo(o.getContentTableOfLessonsOid());
    }
}
