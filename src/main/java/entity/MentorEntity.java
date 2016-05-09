package entity;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "mentor", schema = "scheduledb")
public class MentorEntity {
    private Integer id;
    private DisciplineEntity discipline;
    private DisciplineTypeEntity disciplineType;
    private TeacherEntity teacher;

    public MentorEntity() {
    }

    public MentorEntity(DisciplineEntity discipline, DisciplineTypeEntity disciplineType, TeacherEntity teacher) {
        this.discipline = discipline;
        this.disciplineType = disciplineType;
        this.teacher = teacher;
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

    @ManyToOne
    @JoinColumn(name = "idDiscipline")
    public DisciplineEntity getDiscipline() {
        return discipline;
    }

    public void setDiscipline(DisciplineEntity discipline) {
        this.discipline = discipline;
    }

    @ManyToOne
    @JoinColumn(name = "idDisciplineType")
    public DisciplineTypeEntity getDisciplineType() {
        return disciplineType;
    }

    public void setDisciplineType(DisciplineTypeEntity disciplineType) {
        this.disciplineType = disciplineType;
    }

    @ManyToOne
    @JoinColumn(name = "idTeacher")
    public TeacherEntity getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherEntity teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MentorEntity that = (MentorEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (discipline != null ? !discipline.equals(that.discipline) : that.discipline != null) return false;
        if (disciplineType != null ? !disciplineType.equals(that.disciplineType) : that.disciplineType != null)
            return false;
        if (teacher != null ? !teacher.equals(that.teacher) : that.teacher != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (discipline != null ? discipline.hashCode() : 0);
        result = 31 * result + (disciplineType != null ? disciplineType.hashCode() : 0);
        result = 31 * result + (teacher != null ? teacher.hashCode() : 0);
        return result;
    }
}
