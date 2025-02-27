package sudoku;


public class SudokuColumn extends SudokuCheck {

    public SudokuColumn(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuColumn clone() throws CloneException {
        try {
            SudokuColumn clone = (SudokuColumn) super.clone();
            SudokuField[] clonedFields = new SudokuField[fields.length];
            System.arraycopy(fields, 0, clonedFields, 0, fields.length);
            clone.fields = clonedFields;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new CloneException(SudokuColumn.class.getSimpleName());
        }
    }
}
