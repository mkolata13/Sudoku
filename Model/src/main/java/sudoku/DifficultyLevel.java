package sudoku;

import java.util.Collections;
import java.util.Vector;

public enum DifficultyLevel {
    EASY(20),
    MEDIUM(35),
    HARD(55);

    private final int fieldsToRemove;

    DifficultyLevel(int fieldsToRemove) {
        this.fieldsToRemove = fieldsToRemove;
    }

    public int getFieldsToRemove() {
        return fieldsToRemove;
    }

    public void difficultyLevelHandler(SudokuBoard board) {
        for (int i = 0; i < this.getFieldsToRemove(); i++) {
            Vector<Integer> numbers = new Vector<>(2);
            for (int j = 0; j < 2; j++) {
                numbers.add((int) (Math.random() * 9));
            }
            Collections.shuffle(numbers);
            board.setField(numbers.get(0), numbers.get(1), 0);
        }
    }
}
