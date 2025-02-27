package pl.comp.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sudoku.BacktrackingSudokuSolver;
import sudoku.DifficultyLevel;
import sudoku.Exceptions;
import sudoku.SudokuBoard;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    @FXML
    private ComboBox<DifficultyLevel> difficultyLevelChoice;

    @FXML
    private Button startButton;

    @FXML
    private Button polishButton;

    @FXML
    private Button englishButton;

    @FXML
    private Button authorsButton;

    @FXML
    public void initialize() throws IOException{
        try {
            difficultyLevelChoice.getItems().setAll(DifficultyLevel.values());
        } catch (Exception e) {
            logger.error("Błąd inicjalizacji poziomu trudności", e);
        }
    }

    @FXML
    void onStartButtonClick() {
        try {
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            board.solveGame();

            DifficultyLevel selectedDifficulty = difficultyLevelChoice.getValue();
            if (selectedDifficulty == null) {
                selectedDifficulty = DifficultyLevel.EASY;
            }
            selectedDifficulty.difficultyLevelHandler(board);

            ResourceBundle bundle = ResourceBundle.getBundle("pl.comp.view.messages", Locale.getDefault());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/comp/view/SudokuScene.fxml"), bundle);
            Parent sudokuRoot = loader.load();

            SudokuController sudokuController = loader.getController();
            sudokuController.setBoard(board);
            sudokuController.displayBoard();

            Scene sudokuScene = new Scene(sudokuRoot);
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(sudokuScene);
        } catch (IOException e) {
            logger.error("Bląd podczas wczytywania SudokuScene", e);
        }
    }

    @FXML
    void onPolishButtonClick() throws IOException{
        try {
            Locale.setDefault(new Locale("pl", "PL"));
            reloadScene();
        } catch (Exceptions e) {
            logger.error("Błąd podczas ponownego ładowania sceny z ustawionym polskim", e);
        }
    }

    @FXML
    void onEnglishButtonClick() throws IOException {
        try {
            Locale.setDefault(Locale.ENGLISH);
            reloadScene();
        } catch (Exceptions e) {
            logger.error("Błąd podczas ponownego ładowania sceny z ustawionym angielskim", e);
        }
    }

    @FXML
    void onAuthorsButtonClick() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("pl.comp.view.Authors", Locale.getDefault());
            String authors = bundle.getString("authors");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(bundle.getString("app.title"));
            alert.setHeaderText(null);
            alert.setContentText(authors);

            alert.showAndWait();
        } catch (Exception e) {
            logger.error("Błąd podczas wyświetlania informacji o autorach", e);
        }
    }

    private void reloadScene() throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.comp.view.messages", Locale.getDefault());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pl/comp/view/MainScene.fxml"), bundle);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) startButton.getScene().getWindow();
        stage.setScene(scene);
    }
}
