package sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloneException extends CloneNotSupportedException {
    private static final Logger logger = LoggerFactory.getLogger(CloneException.class);

    public CloneException(String msg) {
        super(msg);
        try {
            logger.error("Wystąpił błąd podczas klonowania obiektu: " + msg);
        } catch (Exception e) {
            throw new RuntimeExceptions(e);
        }
    }
}
