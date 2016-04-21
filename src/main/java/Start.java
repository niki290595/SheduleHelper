import controllers.LoginViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import orm.HibernateGenericDao;

/**
 * Created by User on 21.04.2016.
 */
public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new LoginViewController(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        HibernateGenericDao.getSessionFactory().close();
    }
}