package sudoku;


public class SudokuRow extends SudokuCheck {

    public SudokuRow(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuRow clone() throws CloneException {
        try {
            SudokuRow clone = (SudokuRow) super.clone();
            SudokuField[] clonedFields = new SudokuField[fields.length];
            System.arraycopy(fields, 0, clonedFields, 0, fields.length);
            clone.fields = clonedFields;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new CloneException(SudokuRow.class.getSimpleName());
        }
    }
}

