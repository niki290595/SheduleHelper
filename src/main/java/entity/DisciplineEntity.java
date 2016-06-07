package entity;

import com.sun.org.glassfish.gmbal.Description;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "discipline", schema = "dbo", catalog = "scheduledb")
@Description(value = "Дисциплины")
public class DisciplineEntity implements Comparable<DisciplineEntity> {
    private Integer id;
    private String name;

    public DisciplineEntity() {
    }

    public DisciplineEntity(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisciplineEntity that = (DisciplineEntity) o;

        return id.equals(that.id);

        /*
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
        */
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(DisciplineEntity o) {
        String s1 = this.getName();
        String s2 = o.getName();
        return s1.compareTo(s2);
    }
}
