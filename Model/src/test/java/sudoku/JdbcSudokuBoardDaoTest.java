package sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuBoardDaoTest {
    private SudokuBoard board;
    private Dao<SudokuBoard> dao;

    @BeforeEach
    public void setUp() throws JdbcException {
        board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        dao = new JdbcSudokuBoardDao("testBoard");
    }

    @Test
    public void writeAndReadTest() throws Exceptions {
        assertDoesNotThrow(() -> dao.write(board));
        SudokuBoard readBoard = dao.read();
        assertEquals(board, readBoard);
    }

    @Test
    public void exceptionHandlingTest() {
        assertThrows(DaoException.class, () -> {
            Dao<SudokuBoard> wrongDao = new JdbcSudokuBoardDao(null);
            wrongDao.write(board);
        });
    }

    @Test
    public void testGetAvailableBoards() throws Exception {
        List<String> availableBoards = JdbcSudokuBoardDao.getAvailableBoards();

        assertFalse(availableBoards.isEmpty());
        assertTrue(availableBoards.contains("testBoard"));
    }
}