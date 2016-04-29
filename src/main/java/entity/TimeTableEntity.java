package entity;

import javax.persistence.*;
import java.sql.Time;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "timetable", schema = "scheduledb")
public class TimeTableEntity {
    private Integer id;
    private Time timeBegin;
    private Time timeEnd;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "timeBegin")
    public Time getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Time time) {
        this.timeBegin = time;
    }

    @Basic
    @Column(name = "timeEnd")
    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time time) {
        this.timeEnd = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeTableEntity that = (TimeTableEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (timeBegin != null ? !timeBegin.equals(that.timeBegin) : that.timeBegin != null) return false;
        if (timeEnd != null ? !timeEnd.equals(that.timeEnd) : that.timeEnd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (timeBegin != null ? timeBegin.hashCode() : 0);
        result = 31 * result + (timeEnd != null ? timeEnd.hashCode() : 0);
        return result;
    }
}
