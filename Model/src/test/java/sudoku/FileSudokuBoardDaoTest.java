package sudoku;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileSudokuBoardDaoTest {

    @Test
    public void writeTest() throws DaoException{
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        try(Dao <SudokuBoard> testDao = SudokuBoardDaoFactory.getFileDao("xxx")){
            testDao.write(testBoard);
            SudokuBoard testBoard2 =testDao.read();
            assertEquals(testBoard, testBoard2);
        }
        catch (Exception e){
            throw new DaoException(e,FileSudokuBoardDao.class.getSimpleName()+e.getMessage());
        }
    }

    @Test
    public void writeIOExceptionTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        assertThrows(Exceptions.class, () -> {
            try(Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("?")) {
                fileSudokuBoardDao.write(sudokuBoard);
            }
        });
    }

    @Test
    public void readTest() throws DaoException {
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());

        try(Dao <SudokuBoard> testDao = SudokuBoardDaoFactory.getFileDao("test")){
            testDao.write(testBoard);
            SudokuBoard testBoard2 = testDao.read();
            assertEquals(testBoard, testBoard2);
        }
        catch (Exception e){
            throw new DaoException(e,FileSudokuBoardDao.class.getSimpleName()+e.getMessage());
        }
    }

    @Test
    public void readIOExceptionTest() {
        assertThrows(Exceptions.class, () -> {
            try(Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("yyy")) {
                fileSudokuBoardDao.read();
            }
        });
    }

    @Test
    public void closeTest() throws Exception {
        try(Dao<SudokuBoard> fileSudokuBoardDao = SudokuBoardDaoFactory.getFileDao("xxx")) {
            fileSudokuBoardDao.close();
            assertThrows(Exceptions.class, fileSudokuBoardDao::read);
        }
    }

    @AfterEach
    public void deleteFile() {
        File file = new File("xxx.txt");
        file.delete();
    }
}