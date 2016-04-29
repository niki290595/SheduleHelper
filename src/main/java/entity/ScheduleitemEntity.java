package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "scheduleitem", schema = "scheduledb")
public class ScheduleItemEntity {
    private Integer id;
    private NavigatorEntity navigator;
    private GroupEntity group;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "idNavigator")
    public NavigatorEntity getNavigator() {
        return navigator;
    }

    public void setNavigator(NavigatorEntity navigator) {
        this.navigator = navigator;
    }

    @ManyToOne
    @JoinColumn(name = "idGroup")
    public GroupEntity getGroup() {
        return group;
    }

    public void setGroup(GroupEntity group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleItemEntity that = (ScheduleItemEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (navigator != null ? !navigator.equals(that.navigator) : that.navigator != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (navigator != null ? navigator.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }

    public List<GroupEntity> groupList() {
        List<GroupEntity> groups = new ArrayList<>();
        //todo getGroupEntity
        return groups;
    }
}
