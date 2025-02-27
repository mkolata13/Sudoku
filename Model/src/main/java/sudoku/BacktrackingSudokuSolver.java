package sudoku;

import java.util.Collections;
import java.util.Vector;


public class BacktrackingSudokuSolver implements SudokuSolver {

    public BacktrackingSudokuSolver() {

    }

    @Override
    public void solve(SudokuBoard board) {
        solveBoard(board);
    }

    private boolean solveBoard(SudokuBoard board) {
        for (int row = 0; row < SudokuBoard.BOARD_SIZE; row++) {
            for (int column = 0; column < SudokuBoard.BOARD_SIZE; column++) {
                if (board.getField(row, column) == 0) {
                    Vector<Integer> numbers = new Vector<>();
                    for (int k = 1; k <= SudokuBoard.BOARD_SIZE; k++) {
                        numbers.add(k);
                    }
                    Collections.shuffle(numbers);
                    for (Integer number : numbers) {
                        if (board.isValidMove(number, row, column)) {
                            board.setField(row, column, number);
                            if (solveBoard(board)) {
                                return true;
                            } else {
                                board.setField(row, column, 0);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}

