package entity;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "teacher", schema = "scheduledb")
public class TeacherEntity implements Comparable<TeacherEntity> {
    private Integer id;
    private String fio;
    private String academicDegree;
    private String position;
    private String phone;

    public TeacherEntity() {
    }

    public TeacherEntity(String fio, String academicDegree, String position, String phone) {
        this.fio = fio;
        this.academicDegree = academicDegree;
        this.position = position;
        this.phone = phone;
    }

    public TeacherEntity(Integer id, String fio, String academicDegree, String position, String phone) {
        this.id = id;
        this.fio = fio;
        this.academicDegree = academicDegree;
        this.position = position;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FIO")
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Basic
    @Column(name = "academicDegree")
    public String getAcademicDegree() {
        return academicDegree;
    }

    public void setAcademicDegree(String academicDegree) {
        this.academicDegree = academicDegree;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeacherEntity that = (TeacherEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (fio != null ? !fio.equals(that.fio) : that.fio != null) return false;
        if (academicDegree != null ? !academicDegree.equals(that.academicDegree) : that.academicDegree != null)
            return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (academicDegree != null ? academicDegree.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    public String shortName() {
        String[] name = fio.split(" ");
        return name[0] + " " + name[1].substring(0,1) + ". " + name[2].substring(0,1) + ".";
    }

    public String firstName() {
        String[] name = fio.split(" ");
        return name[0];
    }

    @Override
    public int compareTo(TeacherEntity o) {
        String s1 = this.shortName();
        String s2 = o.shortName();
        return s1.compareTo(s2);
    }
}
