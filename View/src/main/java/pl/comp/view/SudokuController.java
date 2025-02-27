package pl.comp.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;
import sudoku.*;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class SudokuController {

    @FXML
    private GridPane grid;

    @FXML
    private Button saveButton;

    @FXML
    private Button loadButton;

    private SudokuBoard board;

    private boolean[][] initiallyFilled;

    public void displayBoard() {
        if (grid == null) {
            System.out.println("Grid is null");
            return;
        }

        grid.getChildren().clear();
        grid.getRowConstraints().clear();
        grid.getColumnConstraints().clear();

        IntegerProperty[][] boardProperties = new IntegerProperty[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                IntegerProperty fieldProperty = new SimpleIntegerProperty(board.getField(i, j));
                int finalI = i;
                int finalJ = j;
                fieldProperty.addListener((observable, oldValue, newValue) -> {
                    board.setField(finalI, finalJ, newValue.intValue());
                });
                boardProperties[i][j] = fieldProperty;
            }
        }

        Pattern validEditingState = Pattern.compile("([1-9]|\\s)?");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                GridPane subGrid = new GridPane();
                subGrid.setGridLinesVisible(true);
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        TextField textField = new TextField();
                        UnaryOperator<TextFormatter.Change> filter = c -> {
                            String text = c.getControlNewText();
                            if (validEditingState.matcher(text).matches()) {
                                return c;
                            } else {
                                return null;
                            }
                        };

                        if (initiallyFilled[i * 3 + k][j * 3 + l]) {
                            textField.setEditable(false);
                            textField.setStyle("-fx-background-color: grey; -fx-opacity: 0.5;");
                        }
                        TextFormatter<String> formatter = new TextFormatter<>(filter);
                        textField.setTextFormatter(formatter);
                        textField.textProperty().bindBidirectional(boardProperties[i * 3 + k][j * 3 + l], new NumberStringConverter() {
                            @Override
                            public String toString(Number value) {
                                return (value.intValue() == 0) ? "" : super.toString(value);
                            }

                            @Override
                            public Number fromString(String value) {
                                return value.isEmpty() ? 0 : super.fromString(value);
                            }
                        });
                        subGrid.add(textField, l, k);
                    }
                }
                grid.add(subGrid, j, i);
            }
        }
    }

    public void setBoard(SudokuBoard board) {
        this.board = board;
        this.initiallyFilled = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                initiallyFilled[i][j] = board.getField(i, j) != 0;
            }
        }
        displayBoard();
    }

    @FXML
    void saveGame() throws DaoException {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.comp.view.messages", Locale.getDefault());

        TextInputDialog dialog = new TextInputDialog("board_nr_");
        dialog.setTitle(bundle.getString("savegame.title"));
        dialog.setHeaderText(bundle.getString("savewindow.title"));
        dialog.setContentText(bundle.getString("gamename.label"));

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String fileName = result.get();
            try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getJdbcDao(fileName)) {
                dao.write(board);
            } catch (Exception e) {
                throw new DaoException(e, SudokuController.class.getSimpleName());
            }
        }
    }

    @FXML
    void loadGame() throws DaoException {
        ResourceBundle bundle = ResourceBundle.getBundle("pl.comp.view.messages", Locale.getDefault());

        ChoiceDialog<String> dialog = new ChoiceDialog<>();
        dialog.setTitle(bundle.getString("loadgame.title"));
        dialog.setHeaderText(bundle.getString("loadwindow.title"));
        dialog.setContentText(bundle.getString("gamename.label"));
        dialog.getItems().addAll(JdbcSudokuBoardDao.getAvailableBoards());

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String fileName = result.get();
            try (Dao<SudokuBoard> dao = SudokuBoardDaoFactory.getJdbcDao(fileName)) {
                board = dao.read();
                setBoard(board);
                displayBoard();
            } catch (Exception e) {
                throw new DaoException(e, SudokuController.class.getSimpleName());
            }
        }
    }
}