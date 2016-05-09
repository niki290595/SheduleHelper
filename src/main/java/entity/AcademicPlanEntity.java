package entity;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "academicplan", schema = "scheduledb")
public class AcademicPlanEntity {
    private Integer id;
    private DirectionEntity direction;
    private DisciplineEntity discipline;
    private Integer lection;
    private Integer practiсe;
    private Integer lab;

    public AcademicPlanEntity() {
    }

    public AcademicPlanEntity(DirectionEntity direction, DisciplineEntity discipline) {
        this.direction = direction;
        this.discipline = discipline;
    }

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "idDirection")
    public DirectionEntity getDirection() {
        return direction;
    }

    public void setDirection(DirectionEntity direction) {
        this.direction = direction;
    }

    @ManyToOne
    @JoinColumn(name = "idDiscipline")
    public DisciplineEntity getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineEntity discipline) {
        this.discipline = discipline;
    }

    @Basic
    @Column(name = "lection")
    public Integer getLection() {
        return lection;
    }

    public void setLection(Integer lection) {
        this.lection = lection;
    }

    @Basic
    @Column(name = "practiсe")
    public Integer getPractiсe() {
        return practiсe;
    }

    public void setPractiсe(Integer practiсe) {
        this.practiсe = practiсe;
    }

    @Basic
    @Column(name = "lab")
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

        AcademicPlanEntity that = (AcademicPlanEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (direction != null ? !direction.equals(that.direction) : that.direction != null) return false;
        if (discipline != null ? !discipline.equals(that.discipline) : that.discipline != null) return false;
        if (lection != null ? !lection.equals(that.lection) : that.lection != null) return false;
        if (practiсe != null ? !practiсe.equals(that.practiсe) : that.practiсe != null) return false;
        if (lab != null ? !lab.equals(that.lab) : that.lab != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + (discipline != null ? discipline.hashCode() : 0);
        result = 31 * result + (lection != null ? lection.hashCode() : 0);
        result = 31 * result + (practiсe != null ? practiсe.hashCode() : 0);
        result = 31 * result + (lab != null ? lab.hashCode() : 0);
        return result;
    }
}
