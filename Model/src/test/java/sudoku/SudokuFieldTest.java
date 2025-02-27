package sudoku;

import com.jparams.verifier.tostring.ToStringVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuFieldTest {

    @Test
    void setFieldValueInvalidValueTest() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            board.setField(1,1,-5);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            board.setField(1, 1, 11);
        });

        assertEquals(exception.getMessage(),"Podana wartosc musi byc z przedzialu 0-9");
    }

    @Test
    void testToString() {
        ToStringVerifier.forClass(SudokuField.class).verify();
    }

    @Test
    void testEqualsDifferentField() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(1);
        field2.setFieldValue(5);

        assertFalse(field1.equals(field2));
    }

    @Test
    void testEqualsSameField() {
        SudokuField field = new SudokuField();
        field.setFieldValue(1);

        assertTrue(field.equals(field));
    }

    @Test
    void testEqualsDifferentObjects() {
        SudokuField field = new SudokuField();
        field.setFieldValue(1);

        assertFalse(field.equals(new Object()));
    }

    @Test
    void testEqualsWithNoObject() {
        SudokuField field = new SudokuField();
        field.setFieldValue(1);

        assertFalse(field.equals(null));
    }
    @Test
    void testHashCode() {
        SudokuField field1 = new SudokuField();
        SudokuField field2 = new SudokuField();
        field1.setFieldValue(1);
        field2.setFieldValue(5);

        assertNotEquals(field1.hashCode(), field2.hashCode());
    }

    @Test
    void testHashCodeConsistency() {
        SudokuField field = new SudokuField();
        field.setFieldValue(1);
        int initialHashCode = field.hashCode();

        assertEquals(initialHashCode, field.hashCode());

        field.setFieldValue(5);
        assertNotEquals(initialHashCode, field.hashCode());
    }

    @Test
    void compareToTest() throws CloneNotSupportedException {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(1);
        SudokuField field2 = field1.clone();

        assertEquals(0, field1.compareTo(field2));

        field2.setFieldValue(2);

        assertTrue(field1.compareTo(field2) < 0);
        assertTrue(field2.compareTo(field1) > 0);


        assertThrows(NullPointerException.class, () -> {
            field1.compareTo(null);
        });

        field2.setFieldValue(1);
        assertEquals(field1, field2);
    }

    @Test
    void cloneTest() throws CloneNotSupportedException {
        SudokuField field1 = new SudokuField();
        field1.setFieldValue(1);
        SudokuField clonedField = field1.clone();

        assertEquals(field1, clonedField);
        assertNotSame(field1, clonedField);

        field1.setFieldValue(2);

        assertNotEquals(field1, clonedField);
    }
}