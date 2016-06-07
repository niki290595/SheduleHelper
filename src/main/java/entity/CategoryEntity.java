package entity;

import com.sun.org.glassfish.gmbal.Description;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "category", schema = "dbo", catalog = "scheduledb")
@Description(value = "Категории пользователей")
public class CategoryEntity implements Comparable<CategoryEntity> {

    public static enum CategoryType {
        ADMIN, OWNER, STAFF;

        static int admin = "ADMIN".hashCode();
        static int owner = "OWNER".hashCode();
        static int staff = "STAFF".hashCode();

        public static CategoryType get(String s) {
            int hashCode = s.hashCode();
            if (hashCode == admin) return ADMIN;
            if (hashCode == owner) return OWNER;
            if (hashCode == staff) return STAFF;
            return null;
        }
    }

    @Description(value = "id")
    private Integer id;

    @Description(value = "Описание")
    private String description;

    public CategoryEntity() {
    }

    public CategoryEntity(String description) {
        this.description = description;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryType categoryType() {
        return CategoryType.get(getDescription());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryEntity that = (CategoryEntity) o;

        return id.equals(that.id);
        /*
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;
        */
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return description;
    }

    public int compareTo(CategoryEntity o) {
        return this.getDescription().compareTo(o.getDescription());
    }
}
