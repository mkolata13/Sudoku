package sudoku;

public class JdbcException extends DaoException {
    public JdbcException(Throwable cause, String message) {
        super(cause, message);
    }
}
