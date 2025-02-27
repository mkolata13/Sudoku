package sudoku;

public interface Dao<T> extends AutoCloseable {
    T read() throws Exceptions;

    void write(T obj) throws Exceptions;
}
