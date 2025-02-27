package sudoku;


public class SudokuBox extends SudokuCheck {

    public SudokuBox(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuBox clone() throws CloneException {
        try {
            SudokuBox clone = (SudokuBox) super.clone();
            SudokuField[] clonedFields = new SudokuField[fields.length];
            System.arraycopy(fields, 0, clonedFields, 0, fields.length);
            clone.fields = clonedFields;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new CloneException(SudokuBox.class.getSimpleName());
        }
    }
}
