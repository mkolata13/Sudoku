package sudoku;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeExceptions extends RuntimeException {
    private static final Logger logger = LoggerFactory.getLogger(RuntimeExceptions.class);

    public RuntimeExceptions(Throwable msg) {
        super(msg);
        try {
            logger.error("Wystąpił problem z czasem wykonania " + msg);
        } catch (Exception e) {
            throw new RuntimeExceptions(e);
        }
    }
}