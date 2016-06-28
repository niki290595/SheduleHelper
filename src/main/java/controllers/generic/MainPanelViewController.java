package controllers.generic;

import authorization.CategoryEntity;
import com.sun.jnlp.ApiDialog;
import controllers.DialogController;
import customgui.CustomRadioMenuItem;
import authorization.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import orm.HibernateGenericDao;
import other.ClassCollector;
import other.Session;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

/**
 * Created by User on 07.06.2016.
 */
public class MainPanelViewController implements Initializable {
    private static Stage stage;
    private static UserEntity user;
    private static ClassCollector classCollector = ClassCollector.INSTANCE;
    private static ToggleGroup group;

    @FXML Menu editMenu;
    @FXML Label status;
    @FXML Menu tablesMenu;
    @FXML TableView table;

    public MainPanelViewController() {
    }

    public static void open(Stage parentStage) throws IOException {
        MainPanelViewController.user = Session.user;
        Parent root = FXMLLoader.load(MainPanelViewController.class.getClassLoader().getResource("views/generic/main-panel.view.fxml"));
        stage = new Stage();
        stage.setTitle("Добро пожаловать " + Session.user.getLogin());
        stage.setScene(new Scene(root));
        parentStage.hide();
        stage.show();
        new Alert(Alert.AlertType.INFORMATION, "Добро пожаловать " + user.getLogin()).show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        group = new ToggleGroup();
        try {
            for (Class aClass : classCollector.getClasses("entity")) {
                String des = classCollector.getDescription(aClass);
                if (des == null) continue;

                CustomRadioMenuItem item = new CustomRadioMenuItem(des, aClass);
                item.setToggleGroup(group);

                item.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    initTable(item.getCl());
                    status.setText("Открыта таблица \"" + item.getText() + "\"");
                });
                tablesMenu.getItems().add(item);
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        if (user.getCategory().categoryType().equals(CategoryEntity.CategoryType.STAFF)) {
            editMenu.setVisible(false);
        }
        group.selectToggle(group.getToggles().get(1));
    }

    private void initTable(Class cl) {
        while (table.getColumns().size() != 0)
            table.getColumns().remove(0);
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            String des = classCollector.getDescription(field);
            TableColumn column = new TableColumn(des);
            column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
            table.getColumns().add(column);
        }
        ((TableColumn) table.getColumns().get(0)).setVisible(false);
        Collection collection = HibernateGenericDao.readCollection(cl);
        ObservableList list = FXCollections.observableArrayList();
        collection.forEach(list::add);
        Collections.sort(list);
        table.setItems(list);
    }


    public void add(ActionEvent actionEvent) throws IOException {
        Class aClass = ((CustomRadioMenuItem) group.getSelectedToggle()).getCl();
        ItemEditorViewController.setClass(aClass);
        ItemEditorViewController.open(stage, ItemEditorViewController.OperationType.ADD, aClass);
        if (ItemEditorViewController.getResult() == ApiDialog.DialogResult.OK) {
            status.setText("Запись добавлена");
            Class selectedClass = ((CustomRadioMenuItem) group.getSelectedToggle()).getCl();
            initTable(selectedClass);
        } else
            status.setText("Операция отменена");
    }

    public void edit(ActionEvent actionEvent) throws IOException {
        Object obj = table.getSelectionModel().getSelectedItem();
        if (obj == null) {
            status.setText("Ничего не выбрано");
            return;
        }

        ItemEditorViewController.open(stage, ItemEditorViewController.OperationType.EDIT, obj);
        if (ItemEditorViewController.getResult() == ApiDialog.DialogResult.OK) {
            status.setText("Запись отредактирована");
            Class selectedClass = ((CustomRadioMenuItem) group.getSelectedToggle()).getCl();
            initTable(selectedClass);
        } else
            status.setText("Операция отменена");
    }

    public void del(ActionEvent actionEvent) {
        Object obj = table.getSelectionModel().getSelectedItem();
        if (obj == null) {
            status.setText("Ничего не выбрано");
            return;
        }

        HibernateGenericDao.delObject(obj);
        Class selectedClass = ((CustomRadioMenuItem) group.getSelectedToggle()).getCl();
        initTable(selectedClass);
        status.setText("Запись удалена");
    }

    public void exit(ActionEvent actionEvent) {
        stage.close();
    }
}
