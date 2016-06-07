package entity;

import com.sun.org.glassfish.gmbal.Description;
import orm.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 20.04.2016.
 */
@Entity
@Table(name = "scheduleitem", schema = "dbo", catalog = "scheduledb")
@Description(value = "Позиция в расписании")
public class ScheduleItemEntity implements Comparable<ScheduleItemEntity> {
    private Integer id;
    private NavigatorEntity navigator;
    private GroupEntity group;

    public ScheduleItemEntity() {
    }

    public ScheduleItemEntity(NavigatorEntity navigator, GroupEntity group) {
        this.navigator = navigator;
        this.group = group;
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

        return id.equals(that.id);

        /*
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (navigator != null ? !navigator.equals(that.navigator) : that.navigator != null) return false;
        if (group != null ? !group.equals(that.group) : that.group != null) return false;

        return true;
        */
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (navigator != null ? navigator.hashCode() : 0);
        result = 31 * result + (group != null ? group.hashCode() : 0);
        return result;
    }

    public List<GroupEntity> groupList() {
        List<GroupEntity> groups = Repository.INSTANCE.getScheduleItemData()
                .stream().filter(item -> item.getNavigator().equals(getNavigator()))
                .map(ScheduleItemEntity::getGroup).collect(Collectors.toList());
        return groups;
    }

    public String toString(Object obj) {
        if (obj instanceof TeacherEntity) { return toStringForTeacher();
        } else if (obj instanceof AudienceEntity) { return toStringForAudience();
        } else if (obj instanceof GroupEntity) { return toStringForGroup(); }
        return "--";
    }

    public String toStringForTeacher() {
        List<GroupEntity> groupList = groupList();
        return navigator.getMentor().getDiscipline() + " - " + groupsString() + " - " + navigator.getAudience();
    }

    public String toStringForAudience() {
        return navigator.getMentor().getDiscipline() + " - " + navigator.getMentor().getTeacher().shortName();
    }

    public String groupsString() {
        String res = "";
        List<GroupEntity> groupList = groupList();
        for (int i = 0; i < groupList.size(); i++) {
            res += groupList.get(i).toString();
            if (i < groupList.size() - 1) {
                res += ", ";
            }
        }
        return res;
    }

    public String toStringForGroup() {
        return navigator.getMentor().getDiscipline() + "(" +
                navigator.getMentor().getDisciplineType() + ")" + " - " +
                navigator.getMentor().getTeacher().firstName() + " (" +
                navigator.getAudience() + ")";
    }

    @Override
    public int compareTo(ScheduleItemEntity o) {
        return id.compareTo(o.id);
    }
}
