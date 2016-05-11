package entity;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "audience", schema = "scheduledb")
public class AudienceEntity implements Comparable<AudienceEntity> {
    private String id;
    private AudienceTypeEntity audienceType;
    private Integer capacity;

    public AudienceEntity() {
    }

    public AudienceEntity(String id, AudienceTypeEntity audienceType, Integer capacity) {
        this.id = id;
        this.audienceType = audienceType;
        this.capacity = capacity;
    }

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "idAudienceType")
    public AudienceTypeEntity getAudienceType() {
        return audienceType;
    }

    public void setAudienceType(AudienceTypeEntity audienceType) {
        this.audienceType = audienceType;
    }

    @Basic
    @Column(name = "capacity")
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AudienceEntity that = (AudienceEntity) o;

        return id.equals(that.id);
        /*

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (audienceType != null ? !audienceType.equals(that.audienceType) : that.audienceType != null)
            return false;
        if (capacity != null ? !capacity.equals(that.capacity) : that.capacity != null) return false;

        return true;
        */
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (audienceType != null ? audienceType.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int compareTo(AudienceEntity o) {
        String s1 = this.getId();
        String s2 = o.getId();
        return s1.compareTo(s2);
    }
}
