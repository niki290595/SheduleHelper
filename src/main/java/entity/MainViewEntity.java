package entity;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "mainview", schema = "dbo", catalog = "scheduledb")
public class MainViewEntity {
    private String id;
    private Integer dayOfWeek;
    private Time time;
    private Integer audience;
    private Integer capacity;
    private String audienceType;
    private String discipline;
    private String disciplineType;
    private String teacherFio;
    private String academicDegree;
    private String position;
    private String phone;
    private String group;
    private Integer studentNumber;
    private String direction;
    private String disciplineInAcademicPlan;
    private Integer lection;
    private Integer practice;
    private Integer lab;

    @Basic
    @Column(name = "Day Of Week")
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Basic
    @Column(name = "Time")
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Basic
    @Column(name = "№Audience")
    public Integer getAudience() {
        return audience;
    }

    public void setAudience(Integer audience) {
        this.audience = audience;
    }

    @Basic
    @Column(name = "Capacity")
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "Audience Type")
    public String getAudienceType() {
        return audienceType;
    }

    public void setAudienceType(String audienceType) {
        this.audienceType = audienceType;
    }

    @Basic
    @Column(name = "Discipline")
    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    @Basic
    @Column(name = "Discipline Type")
    public String getDisciplineType() {
        return disciplineType;
    }

    public void setDisciplineType(String disciplineType) {
        this.disciplineType = disciplineType;
    }

    @Basic
    @Column(name = "TeacherFIO")
    public String getTeacherFio() {
        return teacherFio;
    }

    public void setTeacherFio(String teacherFio) {
        this.teacherFio = teacherFio;
    }

    @Basic
    @Column(name = "Academic Degree")
    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    @Basic
    @Column(name = "Position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "Phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "Group")
    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Basic
    @Column(name = "Student Number")
    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    @Basic
    @Column(name = "Direction")
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "Discipline In Academic Plan")
    public String getDisciplineInAcademicPlan() {
        return disciplineInAcademicPlan;
    }

    public void setDisciplineInAcademicPlan(String disciplineInAcademicPlan) {
        this.disciplineInAcademicPlan = disciplineInAcademicPlan;
    }

    @Basic
    @Column(name = "Lection")
    public Integer getLection() {
        return lection;
    }

    public void setLection(Integer lection) {
        this.lection = lection;
    }

    @Basic
    @Column(name = "Practice")
    public Integer getPractice() {
        return practice;
    }

    public void setPractice(Integer practice) {
        this.practice = practice;
    }

    @Basic
    @Column(name = "Lab")
    public Integer getLab() {
        return lab;
    }

    public void setLab(Integer lab) {
        this.lab = lab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainViewEntity that = (MainViewEntity) o;

        if (dayOfWeek != null ? !dayOfWeek.equals(that.dayOfWeek) : that.dayOfWeek != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (audience != null ? !audience.equals(that.audience) : that.audience != null) return false;
        if (capacity != null ? !capacity.equals(that.capacity) : that.capacity != null) return false;
        if (audienceType != null ? !audienceType.equals(that.audienceType) : that.audienceType != null) return false;
        if (discipline != null ? !discipline.equals(that.discipline) : that.discipline != null) return false;
        if (disciplineType != null ? !disciplineType.equals(that.disciplineType) : that.disciplineType != null)
            return false;
        if (teacherFio != null ? !teacherFio.equals(that.teacherFio) : that.teacherFio != null) return false;
        if (academicDegree != null ? !academicDegree.equals(that.academicDegree) : that.academicDegree != null)
            return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;
        if (studentNumber != null ? !studentNumber.equals(that.studentNumber) : that.studentNumber != null)
            return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (disciplineInAcademicPlan != null ? !disciplineInAcademicPlan.equals(that.disciplineInAcademicPlan) : that.disciplineInAcademicPlan != null)
            return false;
        if (lection != null ? !lection.equals(that.lection) : that.lection != null) return false;
        if (practice != null ? !practice.equals(that.practice) : that.practice != null) return false;
        if (lab != null ? !lab.equals(that.lab) : that.lab != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dayOfWeek != null ? dayOfWeek.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (audience != null ? audience.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (audienceType != null ? audienceType.hashCode() : 0);
        result = 31 * result + (discipline != null ? discipline.hashCode() : 0);
        result = 31 * result + (disciplineType != null ? disciplineType.hashCode() : 0);
        result = 31 * result + (teacherFio != null ? teacherFio.hashCode() : 0);
        result = 31 * result + (academicDegree != null ? academicDegree.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        result = 31 * result + (studentNumber != null ? studentNumber.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (disciplineInAcademicPlan != null ? disciplineInAcademicPlan.hashCode() : 0);
        result = 31 * result + (lection != null ? lection.hashCode() : 0);
        result = 31 * result + (practice != null ? practice.hashCode() : 0);
        result = 31 * result + (lab != null ? lab.hashCode() : 0);
        return result;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
