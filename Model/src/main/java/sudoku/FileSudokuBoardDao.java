package sudoku;

import java.io.*;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    private final String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName + ".txt";
    }

    @Override
    public SudokuBoard read() throws DaoException {
        SudokuBoard obj;
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            obj = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException exception) {
            String msg = FileSudokuBoardDao.class.getSimpleName();
            throw new DaoException(exception,msg);
        }
        return obj;
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(obj);
        } catch (IOException exception) {
            String msg = FileSudokuBoardDao.class.getSimpleName();
            throw new DaoException(exception,msg);
        }
    }

    @Override
    public void close() throws Exception {
        System.out.println("Plik zostal zamkniety");
    }

    static FileSudokuBoardDao create(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

}