package entity;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "group", schema = "scheduledb")
public class GroupEntity {
    private Integer id;
    private String name;
    private DirectionEntity direction;
    private Integer studentsNumber;

    @Id
    @Column(name = "id")
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

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (studentsNumber != null ? !studentsNumber.equals(that.studentsNumber) : that.studentsNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (studentsNumber != null ? studentsNumber.hashCode() : 0);
        return result;
    }
}
