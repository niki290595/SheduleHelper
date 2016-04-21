package entity;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "user", schema = "scheduledb", catalog = "")
public class UserEntity implements Comparable<UserEntity> {
    private Integer id;
    private String login;
    private String pass;
    private CategoryEntity category;
    private String salt;
    private Date dateCreation;
    private Date dateModification;

    public UserEntity() {
    }

    public UserEntity(String login, String pass, CategoryEntity category, String salt, Date dateCreation) {
        this.login = login;
        this.pass = pass;
        this.category = category;
        this.salt = salt;
        this.dateCreation = dateCreation;
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
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "pass")
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @ManyToOne
    @JoinColumn(name = "idCategory")
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    @Basic
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "dateCreation")
    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Basic
    @Column(name = "dateModification")
    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (pass != null ? !pass.equals(that.pass) : that.pass != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (salt != null ? !salt.equals(that.salt) : that.salt != null) return false;
        if (dateCreation != null ? !dateCreation.equals(that.dateCreation) : that.dateCreation != null) return false;
        return dateModification != null ? dateModification.equals(that.dateModification) : that.dateModification == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (pass != null ? pass.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (dateCreation != null ? dateCreation.hashCode() : 0);
        result = 31 * result + (dateModification != null ? dateModification.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return login;
    }

    public int compareTo(UserEntity o) {
        return this.getLogin().compareTo(o.getLogin());
    }
}
