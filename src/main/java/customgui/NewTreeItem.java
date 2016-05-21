package customgui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import orm.Repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 21.05.2016.
 */
public class NewTreeItem<T, K extends T> extends TreeItem<T> {
    protected Method childFactory;
    protected T value;
    protected ObservableList<K> childCollection;
    private static Repository db = Repository.INSTANCE;

    public NewTreeItem(T value, Method factory) {
        super(value, (Node) null);

        this.value = value;
        childFactory = factory;
        this.getChildren().add(new TreeItem<T>(value)); //для отображение треугольника
    }

    public void visibleChild() {
        try {
            Object[] args = (childFactory.getParameters().length == 0) ? null : new Object[] {value};
            childCollection = (ObservableList<K>) childFactory.invoke(db, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        this.getChildren().clear();

        if(value != null) {
            addChildrenListener(value);
        }

        valueProperty().addListener((obs, oldValue, newValue)->{
            if(newValue != null){
                addChildrenListener(newValue);
            }
        });
    }

    private void addChildrenListener(T value){

        for (K child : childCollection) {
            TreeItem<T> childTreeItem = new TreeItem<>(child);
            childTreeItem.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Done");
                }
            });
            NewTreeItem.this.getChildren().add(childTreeItem);
        }
/*
        childCollection.forEach(child ->
                TreeItem<Object> item =
                NewTreeItem.this.getChildren().add(
                        new TreeItem<>(child)));
*/
        childCollection.addListener((ListChangeListener<K>) change -> {
            while(change.next()){
                if(change.wasAdded()){
                    change.getAddedSubList()
                            .forEach(t->
                                    NewTreeItem.this.getChildren().add(
                                            new TreeItem<>(t)));
                }

                if(change.wasRemoved()){
                    change.getRemoved().forEach(t->{
                        final List<TreeItem<T>> itemsToRemove = NewTreeItem.this.getChildren()
                                .stream()
                                .filter(treeItem ->
                                        treeItem.getValue().equals(t)).collect(Collectors.toList());

                        NewTreeItem.this.getChildren().removeAll(itemsToRemove);
                    });
                }
                this.getChildren().sort((o1, o2) -> {
                    String s1 = o1.getValue().toString();
                    String s2 = o2.getValue().toString();
                    return s1.compareTo(s2);
                });
            }
        });
    }
}