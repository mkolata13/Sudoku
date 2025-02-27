package sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public abstract class SudokuCheck implements Cloneable {
    SudokuField[] fields;

    public SudokuCheck(SudokuField[] fields) {
        this.fields = new SudokuField[fields.length];
        System.arraycopy(fields, 0, this.fields, 0, fields.length);
    }

    public boolean verify() {
        boolean[] valuesChecked = new boolean[10];
        for (SudokuField field : fields) {
            int value = field.getFieldValue();
            if (value != 0) {
                if (valuesChecked[value]) {
                    return false;
                }
                valuesChecked[value] = true;
            }
        }
        return true;
    }

    boolean contains(int number) {
        for (SudokuField field : fields) {
            if (field.getFieldValue() == number) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fields", fields).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode((Object[]) fields);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        SudokuCheck other = (SudokuCheck) obj;
        return Objects.equal(fields, other.fields);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}