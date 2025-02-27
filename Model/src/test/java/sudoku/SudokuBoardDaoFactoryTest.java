package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardDaoFactoryTest {

    @Test
    public void getFileDaoTest() {
        assertNotNull(SudokuBoardDaoFactory.getFileDao("abc"));
    }

    @Test
    public void getJdbcDaoTest() throws JdbcException {
        assertNotNull(SudokuBoardDaoFactory.getJdbcDao("abc"));
    }

    @Test
    public void SudokuBoardDaoFactoryConstructorTest() {
        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        assertNotNull(sudokuBoardDaoFactory);
    }
}
