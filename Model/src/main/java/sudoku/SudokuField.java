package sudoku;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private int value;

    public SudokuField() {
        
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value < 0 || value > 9) {
            throw new IllegalArgumentException("Podana wartosc musi byc z przedzialu 0-9");
        }
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SudokuField other = (SudokuField) obj;
        return Objects.equal(value, other.value);
    }

    @Override
    public int compareTo(SudokuField other) {
        try {
            return Integer.compare(this.value, other.value);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Override
    public SudokuField clone() throws CloneException {
        try {
            return (SudokuField) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CloneException(SudokuField.class.getSimpleName());
        }
    }
}
