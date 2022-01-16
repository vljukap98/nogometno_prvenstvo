package dataloaderfacade;

import java.io.BufferedReader;

/**
 *
 * @author Luka Jaković
 */
abstract class CsvReader {

    protected final String DELIMITER = ";";
    protected Integer currentRow = 0;
    protected Integer columnCount = 0;
    protected MyParser parser = new MyParser();
    protected String row = "";
    protected Boolean firstRow = true;
    protected BufferedReader reader;

    protected abstract void loadData(String path);

    abstract Boolean isRowValid(String[] row);

    protected abstract void calculateColumnCount();

}
