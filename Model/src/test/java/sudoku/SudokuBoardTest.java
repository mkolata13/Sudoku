package sudoku;

import com.jparams.verifier.tostring.ToStringVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SudokuBoardTest {

    @Test
    void checkingIfTwoDiffrentBoards() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        board1.solveGame();
        board2.solveGame();

        boolean boardsAreDifferent = false;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board1.getField(i, j) != board2.getField(i, j)) {
                    boardsAreDifferent = true;
                    break;
                }
            }
        }

        assertTrue(boardsAreDifferent);
    }

    @Test
    void checkingIfBoardIsValid() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        // sprawdzenie kazdego wiersza

        boolean areRowsValid = true;

        for (int i = 0; i < 9; i++) {
            if (!board.getRow(i).verify()) {
                areRowsValid = false;
                break;
            }
        }

        // sprawdzenie kazdej kolumny

        boolean areColumnsValid = true;

        for (int i = 0; i < 9; i++) {
            if (!board.getColumn(i).verify()) {
                areColumnsValid = false;
                break;
            }
        }

        // sprawdzenie kazdego boxa

        boolean areBoxesValid = true;

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                if (!board.getBox(i, j).verify()) {
                    areBoxesValid = false;
                    break;
                }
            }
        }

        assertTrue(areRowsValid && areColumnsValid && areBoxesValid);
    }

    @Test
    void testToString() {
        ToStringVerifier.forClass(SudokuBoard.class).verify();
    }

    @Test
    void testEqualsDifferentBoard() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        board1.solveGame();
        board2.solveGame();

        assertFalse(board1.equals(board2));
    }

    @Test
    void testEqualsSameBoard() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        assertTrue(board.equals(board));
    }

    @Test
    void testEqualsDifferentObjects() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        assertFalse(board.equals(board.getRow(0)));
    }

    @Test
    void testEqualsWithNoObject() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();

        assertFalse(board.equals(null));
    }

    @Test
    void testHashCode() {
        SudokuBoard board1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard board2 = new SudokuBoard(new BacktrackingSudokuSolver());
        board1.solveGame();
        board2.solveGame();

        assertNotEquals(board1.hashCode(), board2.hashCode());

        board1.setField(0, 0, 0);
    }

    @Test
    void testHashCodeConsistency() {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.solveGame();
        int initialHashCode = board.hashCode();

        assertEquals(initialHashCode, board.hashCode());

        if (board.getField(0, 0) != 0) {
            board.setField(0, 0, 0);
        } else {
            board.setField(0, 0, 1);
        }
        assertNotEquals(initialHashCode, board.hashCode());
    }

    @Test
    void testClone() throws CloneException {
        try {
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            board.solveGame();
            SudokuBoard cloned = board.clone();

            assertEquals(board, cloned);
            assertNotSame(board, cloned);

            board.setField(0, 0, 0);
            assertNotEquals(board, cloned);
        } catch (CloneNotSupportedException e) {
            throw new CloneException(SudokuBoard.class.getSimpleName() + e.getMessage());
        }
    }
}