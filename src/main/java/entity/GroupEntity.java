package entity;

import com.sun.org.glassfish.gmbal.Description;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "group1", schema = "dbo", catalog = "scheduledb")
@Description(value = "Группы")
public class GroupEntity implements Comparable<GroupEntity>{
    private Integer id;
    private String name;
    private DirectionEntity direction;
    private Integer studentsNumber;

    public GroupEntity() {
    }

    public GroupEntity(String name, DirectionEntity direction, Integer studentsNumber) {
        this.name = name;
        this.direction = direction;
        this.studentsNumber = studentsNumber;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "idDirection")
    public DirectionEntity getDirection() {
        return direction;
    }

    public void setDirection(DirectionEntity direction) {
        this.direction = direction;
    }

    @Basic
    @Column(name = "studentsNumber")
    public Integer getStudentsNumber() {
        return studentsNumber;
    }

    public void setStudentsNumber(Integer studentsNumber) {
        this.studentsNumber = studentsNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupEntity that = (GroupEntity) o;

        return id.equals(that.id);

        /*
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (studentsNumber != null ? !studentsNumber.equals(that.studentsNumber) : that.studentsNumber != null)
            return false;

        return true;
        */
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (studentsNumber != null ? studentsNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(GroupEntity o) {
        String s1 = this.getName();
        String s2 = o.getName();
        return s1.compareTo(s2);
    }
}
