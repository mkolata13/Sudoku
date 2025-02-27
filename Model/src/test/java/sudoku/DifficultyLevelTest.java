package sudoku;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DifficultyLevelTest {

    @Test
    void getFieldsToRemoveTest() {
        assertThat(DifficultyLevel.EASY.getFieldsToRemove(), equalTo(20));
        assertThat(DifficultyLevel.MEDIUM.getFieldsToRemove(), equalTo(35));
        assertThat(DifficultyLevel.HARD.getFieldsToRemove(), equalTo(55));
    }

    @Test
    void difficultyLevelHandlerTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        DifficultyLevel.EASY.difficultyLevelHandler(sudokuBoard);
        DifficultyLevel.MEDIUM.difficultyLevelHandler(sudokuBoard);
        DifficultyLevel.HARD.difficultyLevelHandler(sudokuBoard);
    }

}
