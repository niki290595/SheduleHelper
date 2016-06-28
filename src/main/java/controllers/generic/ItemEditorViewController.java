package controllers.generic;

import com.sun.jnlp.ApiDialog;
import com.sun.org.apache.bcel.internal.generic.InstructionComparator;
import controllers.DialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import orm.HibernateGenericDao;
import other.ClassCollector;
import other.Session;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.Time;
import java.util.Collection;
import java.util.ResourceBundle;

import static other.Session.user;

/**
 * Created by User on 19.06.2016.
 */
public class ItemEditorViewController implements Initializable {
    private static Stage stage;
    private static Object obj;
    private static Class aClass;
    private static ClassCollector classCollector = ClassCollector.INSTANCE;
    private static OperationType type;

    enum OperationType {
        ADD, EDIT;
    }

    @FXML VBox itemsBox;
    private static ApiDialog.DialogResult result;

    public ItemEditorViewController() {
    }

    public static void open(Stage parentStage, OperationType type, Object obj) throws IOException {
        ItemEditorViewController.obj = obj;
        ItemEditorViewController.type = type;
        switch (type) {
            case ADD:
                aClass = (Class) obj;
                ItemEditorViewController.obj = null;
                break;
            case EDIT:
                ItemEditorViewController.obj = obj;
        }
        Parent root = FXMLLoader.load(MainPanelViewController.class.getClassLoader().getResource("views/generic/item-editor.view.fxml"));
        stage = new Stage();
        stage.setTitle(type.equals(OperationType.EDIT) ? "Изменить " + obj : "Добавить " + classCollector.getDescription(aClass));
        stage.setScene(new Scene(root));
        stage.initOwner(parentStage);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public static ApiDialog.DialogResult getResult() {
        return result;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (obj != null) aClass = obj.getClass();

        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                addItem(field);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        itemsBox.getChildren().remove(0, 2);
    }

    private void addItem(Field field) throws IllegalAccessException {
        String description = classCollector.getDescription(field);
        itemsBox.getChildren().add(new Label(description));

        if (field.getType().equals(Integer.class) ||
                field.getType().equals(String.class) ||
                field.getType().equals(Time.class)) {
            TextField txtFld = new TextField();
            txtFld.setId(description + "Field");
            if (obj != null) {
                field.setAccessible(true);
                if (field.get(obj) != null)
                    txtFld.setText(field.get(obj).toString());
            }
            itemsBox.getChildren().add(txtFld);
        } else {
            Collection collection = HibernateGenericDao.readCollection(field.getType());
            ObservableList list = FXCollections.observableArrayList();
            collection.forEach(list::add);
            ComboBox box = new ComboBox(list);
            if (obj != null) {
                field.setAccessible(true);
                box.getSelectionModel().select(field.get(obj));
            }
            itemsBox.getChildren().add(box);
        }
    }

    public static void setClass(Class aClass) {
        ItemEditorViewController.aClass = aClass;
    }

    public void apply(ActionEvent actionEvent) throws InstantiationException, IllegalAccessException {

        switch (type) {
            case ADD: add(); break;
            case EDIT: edit();
        }
    }

    private void add() throws IllegalAccessException, InstantiationException {
        Object[] objects = new Object[itemsBox.getChildren().size() / 2];
        for (int i = 0; i < objects.length; i++) {
            Node item = itemsBox.getChildren().get(2 * i + 1);
            if (item instanceof TextField && ((TextField) item).getText().length() == 0 ||
                    item instanceof ComboBox && ((ComboBox) item).getSelectionModel().getSelectedItem() == null) {
                new Alert(Alert.AlertType.ERROR, "Проверьте введенные данные: есть незаполненные поля").show();
                return;
            }
            objects[i] = item instanceof TextField ? 
                    ((TextField) item).getText() : 
                    ((ComboBox) item).getSelectionModel().getSelectedItem();
        }
        
        Object obj = aClass.newInstance();
        for (int i = 0; i < objects.length; i++) {
            Field field = aClass.getDeclaredFields()[i + 1];
            field.setAccessible(true);
            try {
                if (field.getType().equals(Integer.class)) {
                    field.set(obj, Integer.parseInt(objects[i].toString()));
                } else {
                    field.set(obj, objects[i]);
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Проверьте введенные данные: " + classCollector.getDescription(field)).show();
                return;
            }
        }
        HibernateGenericDao.addObject(obj);
        result = ApiDialog.DialogResult.OK;
        stage.close();
    }

    private void edit() throws IllegalAccessException {
        Object[] objects = new Object[itemsBox.getChildren().size() / 2];
        for (int i = 0; i < objects.length; i++) {
            Node item = itemsBox.getChildren().get(2 * i + 1);
            objects[i] = item instanceof TextField ?
                    ((TextField) item).getText() :
                    ((ComboBox) item).getSelectionModel().getSelectedItem();
        }

        for (int i = 0; i < objects.length; i++) {
            Field field = aClass.getDeclaredFields()[i + 1];
            field.setAccessible(true);
            try {
                if (field.getType().equals(Time.class)) {
                    field.set(obj, Time.valueOf(objects[i].toString()));
                } else if (field.getType().equals(Integer.class)) {
                    field.set(obj, Integer.valueOf(objects[i].toString()));
                } else {
                    field.set(obj, objects[i]);
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Проверьте введенные данные: " + classCollector.getDescription(field)).show();
                return;
            }
        }
        HibernateGenericDao.editObject(obj);
        result = ApiDialog.DialogResult.OK;
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        stage.close();
        result = ApiDialog.DialogResult.CANCEL;
    }
}
