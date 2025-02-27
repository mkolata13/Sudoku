package sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class Exceptions extends IOException {
    private static final Logger logger = LoggerFactory.getLogger(Exceptions.class);

    public Exceptions(Throwable cause, String msg) {
        super(cause);
        try {
            logger.error("Wystąpił błąd IO " + msg);
        } catch (Exception e) {
            throw new RuntimeExceptions(e);
        }
    }
}