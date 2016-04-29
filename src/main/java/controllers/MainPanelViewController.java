package controllers;

import customgui.MenuItemGroup;
import customgui.ScheduleLabel;
import entity.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by User on 21.04.2016.
 */
public class MainPanelViewController implements Initializable {
    private static Stage stage;
    private static UserEntity user;
    private static final Repository db = Repository.INSTANCE;

    @FXML GridPane scheduleGridPane;
    @FXML ToggleButton switchEvenBtn;

    Label[][] timeTable;
    ScheduleItemEntity[][] scheduleItems;
    ScheduleLabel contextMenuSrc;

    public MainPanelViewController() {
    }

    public MainPanelViewController(Stage parentStage, UserEntity user) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/main-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Добро пожаловать " + user.getLogin());
        stage.setScene(new Scene(root, 1024, 768));
        MainPanelViewController.user = user;
        parentStage.hide();
        stage.show();
        (new DialogController(stage, DialogController.Type.INFO))
            .setTitle("Уведомление")
            .setMsg("Добро пожаловать " + user.getLogin()).show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTimeTable();
        initSchedule();
        initTree();
        //initContextMenu();
        scheduleGridPane.setVisible(true);
    }

    private void initTimeTable() {
        if (timeTable == null) {
            createTimeTable();
        }

        Map<Integer, TimeTableEntity> timeTableData = db.getTimeTableData();
        for (Integer id : timeTableData.keySet()) {
            TimeTableEntity item = timeTableData.get(id);
            timeTable[0][id - 1].setText(item.getTimeBegin() + " - " + item.getTimeEnd());
            timeTable[1][id - 1].setText(item.getTimeBegin() + " - " + item.getTimeEnd());
        }
    }

    private void createTimeTable() {
        timeTable = new Label[2][7];
        for (int i = 0; i < timeTable[0].length; i++) {
            timeTable[0][i] = createLabel("-");
            scheduleGridPane.add(timeTable[0][i], 0, i + 1);
            timeTable[1][i] = createLabel("-");
            scheduleGridPane.add(timeTable[1][i], 0, i + 10);
        }
    }

    private Label createLabel(String text) {
        Label label = new Label();
        label.setText(text);
        label.setVisible(true);
        label.setAlignment(Pos.CENTER);
        return label;
    }

    private void initSchedule() {

    }

    private void initTree() {

    }
/*
    private void initContextMenu() {
        MenuItem[] menuItems = new MenuItem[3];
        List<DirectionEntity> directionList = db.getDirectionData();
        MenuItem[] directionMenuItems = new MenuItem[directionList.size()];
        for (int i = 0; i < directionMenuItems.length; i++) {
            DirectionEntity direction = directionList.get(i);
            List<GroupEntity> groupList = db.getGroupData(direction);
            MenuItem[] groupMenuItems = new MenuItem[groupList.size()];

            for (int j = 0; j < groupList.size(); j++) {
                GroupEntity group = groupList.get(j);
                groupMenuItems[j] = new MenuItemGroup(group);
                groupMenuItems[j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        ScheduleItemEntity item = scheduleItems[contextMenuSrc.getDayOfWeek() - 1][contextMenuSrc.getIdTime()-1];
                        if (!item.groupList().contains(((MenuItemGroup) event.getSource()).getGroup())) {
                            //проверить расписание второй группы надо
                            db.addScheduleItem2(item.getNavigator().getDayOfWeek(),
                                    item.getNavigator().getTime(),
                                    item.getNavigator().getWeekOdd(),
                                    item.getNavigator().getAudience(),
                                    item.getNavigator().getMentor().getDiscipline(),
                                    item.getNavigator().getMentor().getDisciplineType(),
                                    ((MenuItemGroup) event.getSource()).getGroup(),
                                    item.getNavigator().getMentor().getTeacher());

                            try {
                                new DialogController(stage, DialogController.Type.INFO)
                                        .setTitle("Уведомление")
                                        .setMsg("Запись успешно добавлена").show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            directionMenuItems[i] = new Menu(direction.getName(), null, groupMenuItems);
        }
        menuItems[0] = new Menu("Добавить группе", null, directionMenuItems);

        menuItems[1] = new MenuItem("Изменить");
        menuItems[1].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    openScheduleEdit(contextMenuSrc, treeView.getSelectionModel().getSelectedItem().getValue());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        menuItems[2] = new MenuItem("Удалить");
        menuItems[2].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Object obj = treeView.getSelectionModel().getSelectedItem().getValue();
                if (obj instanceof GroupEntity) {
                    Main.getInstance().delSchedule(scheduleItems[contextMenuSrc.getDayOfWeek() - 1][contextMenuSrc.getIdTime() - 1], (Group) obj);
                    scheduleItems[contextMenuSrc.getDayOfWeek() - 1][contextMenuSrc.getIdTime() - 1] = null;
                    contextMenuSrc.setText("-");
                    try {
                        new DialogController(stage, DialogController.Type.INFO)
                                .setTitle("Уведомление")
                                .setMsg("Запись успешно удалена");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        menu = new ContextMenu(menuItems);
    }
*/

    public void openTeachersPanel(ActionEvent actionEvent) throws IOException {
        new TeachersPanelViewController(stage);
    }
}
