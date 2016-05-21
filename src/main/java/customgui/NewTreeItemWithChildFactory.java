package customgui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import orm.Repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by User on 21.05.2016.
 */
public class NewTreeItemWithChildFactory<T, K extends T> extends TreeItem<T> {
    protected Method childFactory;
    protected T value;
    protected ObservableList<K> childCollection;
    private Method factoryInChild;
    private static Repository db = Repository.INSTANCE;

    public NewTreeItemWithChildFactory(T value, Method factory, Method factoryInChild) {
        super(value, (Node) null);

        this.value = value;
        childFactory = factory;
        this.factoryInChild = factoryInChild;
        this.getChildren().add(new TreeItem<T>(value)); //для отображение треугольника
    }

    public void visibleChild() {
        try {
            childCollection = (ObservableList<K>) childFactory.invoke(db);
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
                addChildrenListener(value);
            }
        });
    }

    private void addChildrenListener(T value){

        childCollection.forEach(child ->
                NewTreeItemWithChildFactory.this.getChildren().add(
                        new NewTreeItem<>(child, factoryInChild)));

        childCollection.addListener((ListChangeListener<K>) change -> {
            while(change.next()){
                if(change.wasAdded()){
                    change.getAddedSubList()
                            .forEach(t->
                                    NewTreeItemWithChildFactory.this.getChildren().add(
                                            new NewTreeItem<>(t, factoryInChild)));
                }

                if(change.wasRemoved()){
                    change.getRemoved().forEach(t->{
                        final List<TreeItem<T>> itemsToRemove = NewTreeItemWithChildFactory.this.getChildren()
                                .stream()
                                .filter(treeItem ->
                                        treeItem.getValue().equals(t)).collect(Collectors.toList());

                        NewTreeItemWithChildFactory.this.getChildren().removeAll(itemsToRemove);
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
