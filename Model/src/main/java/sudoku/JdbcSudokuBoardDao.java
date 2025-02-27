package sudoku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {
    private final String boardName;

    private static final String dbPath =  "jdbc:derby:" + System.getProperty("user.dir") + "//Database;create=true";

    public JdbcSudokuBoardDao(String boardName) throws JdbcException {
        if (boardName == null) {
            throw new JdbcException(new Exception("Board name cannot be null"), "Board name cannot be null");
        }
        this.boardName = boardName;
        createSudokuBoardsTable();
        createBoardFieldsTable();
    }

    private static void createSudokuBoardsTable() throws JdbcException {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            try {
                statement.executeUpdate("CREATE TABLE SUDOKU_BOARDS (boardName VARCHAR(255), boardID VARCHAR(36))");
            } catch (SQLException exception) {
                if (!exception.getSQLState().equals("X0Y32")) {
                    throw exception;
                }
            }
            connection.commit();
        } catch (SQLException exception) {
            throw new JdbcException(exception, "Problem with creating SUDOKU_BOARDS table");
        }
    }

    private static void createBoardFieldsTable() throws JdbcException {
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false);
            try {
                statement.executeUpdate("CREATE TABLE BOARD_FIELDS ("
                        + "boardID VARCHAR(36), row INT, col INT, value INT)");
            } catch (SQLException exception) {
                if (!exception.getSQLState().equals("X0Y32")) {
                    throw exception;
                }
            }
            connection.commit();
        } catch (SQLException exception) {
            throw new JdbcException(exception, "Problem with creating BOARD_FIELDS table");
        }
    }

    @Override
    public SudokuBoard read() throws JdbcException {
        SudokuBoard board = null;
        try (Connection connection = DriverManager.getConnection(dbPath);
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM BOARD_FIELDS"
                             + " JOIN SUDOKU_BOARDS ON SUDOKU_BOARDS.boardID = BOARD_FIELDS.boardID"
                             + " WHERE boardName = ?")) {
            ps.setString(1, boardName);
            try (ResultSet fields = ps.executeQuery()) {
                if (fields.next()) {
                    board = new SudokuBoard(new BacktrackingSudokuSolver());
                    do {
                        int row = fields.getInt("row");
                        int col = fields.getInt("col");
                        int value = fields.getInt("value");
                        board.setField(row, col, value);
                    } while (fields.next());
                }
            }
        } catch (Exception exception) {
            throw new JdbcException(exception, "Problem with reading board from database");
        }
        return board;
    }

    @Override
    public void write(SudokuBoard sudokuBoard) throws JdbcException {
        String boardID = UUID.randomUUID().toString();
        try (Connection connection = DriverManager.getConnection(dbPath)) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM SUDOKU_BOARDS WHERE boardName = ?")) {
                ps.setString(1, boardName);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO SUDOKU_BOARDS (boardName, boardID) VALUES (?, ?)")) {
                ps.setString(1, boardName);
                ps.setString(2, boardID);
                ps.executeUpdate();
            }
            connection.commit();
        } catch (SQLException exception) {
            throw new JdbcException(exception, "Problem with writing board to database");
        }

        try (Connection connection = DriverManager.getConnection(dbPath)) {
            connection.setAutoCommit(false);
            try (PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM BOARD_FIELDS WHERE boardID = ?")) {
                ps.setString(1, boardID);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO BOARD_FIELDS (boardID, row, col, value) VALUES (?, ?, ?, ?)")) {
                for (int row = 0; row < SudokuBoard.BOARD_SIZE; row++) {
                    for (int col = 0; col < SudokuBoard.BOARD_SIZE; col++) {
                        int value = sudokuBoard.getField(row, col);
                        ps.setString(1, boardID);
                        ps.setInt(2, row);
                        ps.setInt(3, col);
                        ps.setInt(4, value);
                        ps.executeUpdate();
                    }
                }
            }
            connection.commit();
        } catch (SQLException exception) {
            throw new JdbcException(exception, "Problem with updating board fields in database");
        }
    }

    public static List<String> getAvailableBoards() throws JdbcException {
        List<String> availableBoards = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbPath);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT boardName FROM SUDOKU_BOARDS")) {
            while (resultSet.next()) {
                availableBoards.add(resultSet.getString("boardName"));
            }
        } catch (SQLException exception) {
            throw new JdbcException(exception, "Problem with getting available boards from database");
        }
        return availableBoards;
    }

    @Override
    public void close() throws Exception {
    }

    static JdbcSudokuBoardDao create(String boardName) throws JdbcException {
        return new JdbcSudokuBoardDao(boardName);
    }
}