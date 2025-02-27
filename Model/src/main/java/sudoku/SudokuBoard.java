package sudoku;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SudokuBoard implements Serializable, Cloneable {

    static final int BOARD_SIZE = 9;
    private List<List<SudokuField>> board;
    private transient SudokuSolver solver;

    public SudokuBoard(SudokuSolver solver) {
        board = Arrays.asList(new List[BOARD_SIZE]);
        for (int i = 0; i < BOARD_SIZE; i++) {
            board.set(i, Arrays.asList(new SudokuField[BOARD_SIZE]));
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board.get(i).set(j, new SudokuField());
            }
        }
        this.solver = Objects.requireNonNullElseGet(solver, BacktrackingSudokuSolver::new);
    }

    public int getField(int row, int col) {
        return this.board.get(row).get(col).getFieldValue();
    }

    public void setField(int row, int col, int number) {
        this.board.get(row).get(col).setFieldValue(number);
    }

    public void solveGame() {
        solver.solve(this);
    }

    public SudokuRow getRow(int y) {
        SudokuField[] row = new SudokuField[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            row[i] = this.board.get(y).get(i);
        }
        return new SudokuRow(row);
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] column = new SudokuField[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            column[i] = this.board.get(i).get(x);
        }
        return new SudokuColumn(column);
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] box = new SudokuField[BOARD_SIZE];
        int rowBoxStart = y - y % 3;
        int columnBoxStart = x - x % 3;
        int index = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                box[index] = this.board.get(i + columnBoxStart).get(j + rowBoxStart);
                index++;
            }
        }

        return new SudokuBox(box);
    }

    boolean isValidMove(int number, int row, int column) {
        return !this.getRow(row).contains(number)
                && !this.getColumn(column).contains(number)
                && !this.getBox(row, column).contains(number);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", board)
                .add("solver", solver).toString();
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(board);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        SudokuBoard other = (SudokuBoard) obj;
        return com.google.common.base.Objects.equal(board, other.board);
    }

    @Override
    public SudokuBoard clone() throws CloneException {
        try {
            SudokuBoard clone = (SudokuBoard) super.clone();

            List<List<SudokuField>> boardClone = new ArrayList<>(BOARD_SIZE);
            for (int i = 0; i < BOARD_SIZE; i++) {
                boardClone.add(new ArrayList<>(BOARD_SIZE));
            }

            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    boardClone.get(i).add(this.board.get(i).get(j).clone());
                }
            }
            clone.board = boardClone;
            clone.solver = new BacktrackingSudokuSolver();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new CloneException(SudokuBoard.class.getSimpleName() + e.getMessage());
        }
    }
}
