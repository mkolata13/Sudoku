package pl.comp.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void start(Stage stage) {
        try {
            Locale.setDefault(new Locale("pl", "PL"));

            ResourceBundle bundle = ResourceBundle.getBundle("pl.comp.view.messages", Locale.getDefault());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"), bundle);
            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);

            stage.setTitle(bundle.getString("app.title"));
            stage.show();
        } catch (Exception e) {
            logger.error("Wystąpił wyjątek podczas uruchamiania aplikacji", e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
