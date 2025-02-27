package sudoku;

import com.jparams.verifier.tostring.ToStringVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuCheckTest {

    @Test
    void verifyWithInvalidBoardTest() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        board.setField(0,0,1);
        board.setField(0,1,1);
        board.setField(1,0,1);

        assertFalse(board.getRow(0).verify());
        assertFalse(board.getColumn(0).verify());
        assertFalse(board.getBox(0,0).verify());
    }

    @Test
    void verifyWithValueEqualZero() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        board.setField(0,0,0);
        assertTrue(board.getBox(0,0).verify());
    }

    @Test
    void testToString() {
        ToStringVerifier.forClass(SudokuRow.class).verify();
        ToStringVerifier.forClass(SudokuColumn.class).verify();
        ToStringVerifier.forClass(SudokuBox.class).verify();
    }
    @Test
    void testEqualsDifferentFields() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        SudokuRow row1 = board.getRow(0);
        SudokuRow row2 = board.getRow(1);
        SudokuColumn column1 = board.getColumn(0);
        SudokuColumn column2 = board.getColumn(1);
        SudokuBox box1 = board.getBox(0,0);
        SudokuBox box2 = board.getBox(3,3);

        assertFalse(row1.equals(row2));
        assertFalse(column1.equals(column2));
        assertFalse(box1.equals(box2));
    }

    @Test
    void testEqualsSameFields() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        SudokuRow row = board.getRow(0);
        SudokuColumn column = board.getColumn(0);
        SudokuBox box = board.getBox(0,0);

        assertTrue(row.equals(row));
        assertTrue(column.equals(column));
        assertTrue(box.equals(box));
    }

    @Test
    void testEqualsDifferentObjects() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        SudokuRow row = board.getRow(0);
        SudokuColumn column = board.getColumn(0);
        SudokuBox box = board.getBox(0,0);

        assertFalse(row.equals(board));
        assertFalse(column.equals(board));
        assertFalse(box.equals(board));
    }

    @Test
    void testEqualsWithNoObject() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        assertFalse(board.getColumn(0).equals(null));
        assertFalse(board.getBox(0,0).equals(null));
        assertFalse(board.getRow(0).equals(null));
    }

    @Test
    void testHashCode() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        assertNotEquals(board.getRow(0).hashCode(), board.getRow(1).hashCode());
        assertNotEquals(board.getColumn(0).hashCode(), board.getColumn(1).hashCode());
        assertNotEquals(board.getBox(0,0).hashCode(), board.getBox(3,3).hashCode());
    }

    @Test
    void testHashCodeConsistency() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        int initialHashCode = board.getRow(0).hashCode();
        assertEquals(initialHashCode, board.getRow(0).hashCode());

        if (board.getField(0, 0) != 0) {
            board.setField(0, 0, 0);
        } else {
            board.setField(0, 0, 1);
        }
        assertNotEquals(initialHashCode, board.getRow(0).hashCode());
    }

    @Test
    void testClone() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        try {
            SudokuRow row = board.getRow(0);
            SudokuRow rowClone = row.clone();

            SudokuColumn column = board.getColumn(0);
            SudokuColumn columnClone = column.clone();

            SudokuBox box = board.getBox(0,0);
            SudokuBox boxClone = box.clone();

            assertNotSame(row, rowClone);
            assertNotSame(column, columnClone);
            assertNotSame(box, boxClone);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}