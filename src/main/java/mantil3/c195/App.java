package mantil3.c195;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mantil3.c195.helper.JDBC;
import java.io.IOException;
import java.util.Locale; //Don't remove, needed if Locale is changed manually
import java.util.ResourceBundle;

/**
 * Main class </br>
 * Launches Application
 */
public class App extends Application {

    /**
     * Runs on application start up<br>
     * @param stage         the main stage
     * @throws IOException  thrown when loading stage
     */
    @Override public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        //Locale.setDefault(new Locale("fr", "CA"));
        ResourceBundle rb = ResourceBundle.getBundle("languageFiles/rb");
        fxmlLoader.setResources(rb);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(rb.getString("Title"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main<br>
     * @param args The command line parameters.
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        launch();
        JDBC.closeConnection();
    }
}