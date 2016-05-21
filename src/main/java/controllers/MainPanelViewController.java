package controllers;

import customgui.NewTreeItem;
import customgui.NewTreeItemWithChildFactory;
import customgui.ScheduleLabel;
import entity.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import orm.Repository;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
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
    @FXML TreeView<Object> treeView;

    Label[][] timeTable;
    Label[][] schedule;
    ScheduleItemEntity[][] scheduleItems;
    ScheduleLabel contextMenuSrc;

    public MainPanelViewController() {
    }

    public MainPanelViewController(Stage parentStage, UserEntity user) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/main-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Добро пожаловать " + user.getLogin());
        stage.setScene(new Scene(root));
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
            timeTable[0][id - 1].setText(item.toString());
            timeTable[1][id - 1].setText(item.toString());
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
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }

    private Label createLabel(String text, int dayOfWeek, int idTime) {
        Label label = new ScheduleLabel(text, dayOfWeek, idTime);
        label.setVisible(true);
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(16.0);
        label.setPrefWidth(230);
        //label.setContextMenu();
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ScheduleLabel src = (ScheduleLabel) event.getSource();
                if (event.getButton() == MouseButton.PRIMARY) {
                    src = (ScheduleLabel) event.getSource();
                    TreeItem<Object> item = treeView.getSelectionModel().getSelectedItem();
                    if (item != null) {
                        /*
                        try {
                            openScheduleEdit(src, item.getValue());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        */
                    }
                } else if (!src.getText().equals("-")) {
                    contextMenuSrc = src;
                    //menu.show(src, Side.BOTTOM, event.getSceneX() - src.getLayoutX(), event.getSceneY() - src.getLayoutY());
                }
            }
        });
        return label;
    }

    private void initSchedule() {
        schedule = new Label[6][7];

        for (int i = 0; i < schedule.length; i++) {
            for (int j = 0; j < schedule[0].length; j++) {
                schedule[i][j] = createLabel("lesson" + i + j, i + 1, j + 1);
                scheduleGridPane.add(schedule[i][j],
                        i + 1 + (i < 3 ? 0 : -3),
                        j + 1 + (i < 3 ? 0 : 9));
                scheduleGridPane.setHalignment(schedule[i][j], HPos.CENTER);
            }
        }

        switchEvenBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeSchedule(treeView.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void initTree() {
        TreeItem<Object> rootItem = new TreeItem<>("Система");
        rootItem.setExpanded(true);
        rootItem.addEventHandler(TreeItem.<Object> branchExpandedEvent(), event -> {
            TreeItem<Object> item = event.getTreeItem();
            String value = item.getValue().toString();

            if (value.equals("Учителя")) {
                ((NewTreeItem<Object, TeacherEntity>) item).visibleChild();
            } else if (value.equals("Аудитории")) {
                ((NewTreeItem<Object, AudienceEntity>) item).visibleChild();
            } else if (value.equals("Направления")) {
                ((NewTreeItemWithChildFactory<Object, DirectionEntity>) item).visibleChild();
            } else if (item.getValue() instanceof DirectionEntity) {
                ((NewTreeItem<Object, DirectionEntity>) item).visibleChild();
            }
        });


        TreeItem<Object> teacherItem = null;
        TreeItem<Object> audienceItem = null;
        TreeItem<Object> directionItem = null;
        try {
            teacherItem = new NewTreeItem<Object, TeacherEntity>("Учителя",
                    db.getClass().getMethod("getTeacherData"));

            audienceItem = new NewTreeItem<Object, AudienceEntity>("Аудитории",
                    db.getClass().getMethod("getAudienceData"));

            Class[] paramTypes = new Class[] {DirectionEntity.class};
            directionItem = new NewTreeItemWithChildFactory<>("Направления",
                    db.getClass().getMethod("getDirectionData"),
                    db.getClass().getMethod("getGroupData", paramTypes));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        rootItem.getChildren().add(teacherItem);
        rootItem.getChildren().add(audienceItem);
        rootItem.getChildren().add(directionItem);

        treeView.setRoot(rootItem);
        treeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, item) -> changeSchedule(item));
    }

    private void changeSchedule(TreeItem<Object> item) {
        System.out.println("change scheduler");
        /*
        Object obj = item.getValue();
        if (obj instanceof String || obj instanceof DirectionEntity) {
            scheduleGridPane.setVisible(false);
            return;
        }

        scheduleGridPane.setVisible(true);
        scheduleItems = db.getScheduleItemData(obj, getWeekOdd());
        for (int i = 0; i < scheduleItems.length; i++) {
            for (int j = 0; j < scheduleItems[0].length; j++) {
                if (scheduleItems[i][j] != null) {
                    String text = scheduleItems[i][j].toString(obj);
                    schedule[i][j].setText(text);
                    schedule[i][j].setTooltip(new Tooltip(text));
                    if (ruleForINSERT) {
                        schedule[i][j].setContextMenu(menu);
                    }
                } else {
                    schedule[i][j].setText("-");
                }
            }
        }

        menu.getItems().get(2).setDisable(!(obj instanceof Group));
        */
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

    public void openAudiencesPanel(ActionEvent actionEvent) throws IOException {
        new AudiencesPanelViewController(stage);
    }

    public void openTimePanel(ActionEvent actionEvent) throws IOException {
        new TimePanelViewController(stage);
        initTimeTable();
    }

    public void openDisciplinesPanel(ActionEvent actionEvent) throws IOException {
        new DisciplinesPanelViewController(stage);
    }

    public void openGroupPanel(ActionEvent actionEvent) throws IOException {
        new GroupsPanelViewController(stage);
    }

    public void exit(ActionEvent actionEvent) {
        stage.close();
    }
}
