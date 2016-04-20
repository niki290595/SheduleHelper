package entity;

import javax.persistence.*;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "navigator", schema = "scheduledb")
public class NavigatorEntity {
    private Integer id;
    private Integer dayOfWeek;
    private TimeTableEntity time;
    private Integer weekOdd;
    private AudienceEntity audience;
    private MentorEntity mentor;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dayOfWeek")
    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @ManyToOne
    @JoinColumn(name = "idTime")
    public TimeTableEntity getTime() {
        return time;
    }

    public void setTime(TimeTableEntity time) {
        this.time = time;
    }

    @Basic
    @Column(name = "weekOdd")
    public Integer getWeekOdd() {
        return weekOdd;
    }

    public void setWeekOdd(Integer weekOdd) {
        this.weekOdd = weekOdd;
    }

    @ManyToOne
    @Column(name = "idAudience")
    public AudienceEntity getAudience() {
        return audience;
    }

    public void setAudience(AudienceEntity audience) {
        this.audience = audience;
    }

    @ManyToOne
    @Column(name = "idMentor")
    public MentorEntity getMentor() {
        return mentor;
    }

    public void setMentor(MentorEntity mentor) {
        this.mentor = mentor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NavigatorEntity that = (NavigatorEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dayOfWeek != null ? !dayOfWeek.equals(that.dayOfWeek) : that.dayOfWeek != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (weekOdd != null ? !weekOdd.equals(that.weekOdd) : that.weekOdd != null) return false;
        if (audience != null ? !audience.equals(that.audience) : that.audience != null) return false;
        if (mentor != null ? !mentor.equals(that.mentor) : that.mentor != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dayOfWeek != null ? dayOfWeek.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (weekOdd != null ? weekOdd.hashCode() : 0);
        result = 31 * result + (audience != null ? audience.hashCode() : 0);
        result = 31 * result + (mentor != null ? mentor.hashCode() : 0);
        return result;
    }
}
