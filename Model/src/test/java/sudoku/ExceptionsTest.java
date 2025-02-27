package sudoku;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExceptionsTest {

    @Test
    public void RuntimeExceptionsTest() {
        assertThrows(RuntimeExceptions.class, () -> {
            throw new RuntimeExceptions(new Exception());
        });
        try {
            throw new RuntimeExceptions(new Exception());
        } catch (Exception e) {
            assertEquals("java.lang.Exception", e.getMessage());
        }
    }

    @Test
    public void CloneExceptionTest() {
        assertThrows(CloneException.class, () -> {
            throw new CloneException("test");
        });
        try {
            throw new RuntimeExceptions(new Exception());
        } catch (Exception e) {
            assertEquals("java.lang.Exception", e.getMessage());
        }
    }

}
