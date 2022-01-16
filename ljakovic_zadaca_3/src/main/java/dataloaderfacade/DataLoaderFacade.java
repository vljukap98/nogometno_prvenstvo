package dataloaderfacade;

import java.util.EnumMap;

/**
 *
 * @author Luka Jaković
 */
public class DataLoaderFacade {

    private static DataLoaderFacade instance;

    private final EnumMap<FileOrderEnum, String> fileMap = new EnumMap<>(FileOrderEnum.class);

    private DataLoaderFacade() {
    }

    public static DataLoaderFacade getInstance() {
        if (instance == null) {
            instance = new DataLoaderFacade();
        }

        return instance;
    }

    public void loadInitialFiles(String[] args) {
        checkFlagValidity(args);
        mapDataToObjects(args);
    }

    public void loadAdditionalFile(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Krivi argumenti");
        }

        CsvReaderFactory factory = new CsvReaderFactory();
        CsvReader reader = factory.getReader(args[0]);
        reader.loadData(args[1]);
    }

    private void mapDataToObjects(String[] args) {
        CsvReaderFactory factory = new CsvReaderFactory();

        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-i")) {
                fileMap.put(FileOrderEnum.i, args[i + 1]);
            }
            if (args[i].equals("-k")) {
                fileMap.put(FileOrderEnum.k, args[i + 1]);
            }
            if (args[i].equals("-u")) {
                fileMap.put(FileOrderEnum.u, args[i + 1]);
            }
            if (args[i].equals("-d")) {
                fileMap.put(FileOrderEnum.d, args[i + 1]);
            }
            if (args[i].equals("-s")) {
                fileMap.put(FileOrderEnum.s, args[i + 1]);
            }
        }

        fileMap.entrySet().forEach(entry -> {
            FileOrderEnum key = entry.getKey();
            String value = entry.getValue();

            CsvReader reader = factory.getReader(key.toString());
            reader.loadData(value);
        });
    }

    private void checkFlagValidity(String[] args) {
        if (args.length < 4 || args.length > 10) {
            throw new IllegalArgumentException("Wrong arguments!");
        }
    }
}
