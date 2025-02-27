package sudoku;

public class SudokuBoardDaoFactory {
    public static Dao<SudokuBoard> getFileDao(String fileName) {
        return FileSudokuBoardDao.create(fileName);
    }

    public static Dao<SudokuBoard> getJdbcDao(String fileName) throws JdbcException {
        return JdbcSudokuBoardDao.create(fileName);
    }
}