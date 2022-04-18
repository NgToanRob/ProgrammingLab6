package server.utility;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import data.Organization;
import server.App;
import utility.Outputer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Operates the file for saving/loading collection.
 */
public class CollectionFileManager {

    private Gson gson = new Gson();
    private String envVariable;

    public CollectionFileManager(String envVariable) {
        this.envVariable = envVariable;
    }

    /**
     * Writes collection to a file.
     *
     * @param collection Collection to write.
     */
    public void writeCollection(Collection<?> collection){
        String filePath = System.getenv().get(envVariable);
        if (filePath != null) {
            try (FileWriter collectionFileWriter = new FileWriter(filePath)) {
                collectionFileWriter.write((gson.toJson(collection)));
                ResponseOutputer.appendln("Collection successfully saved to file.");
            } catch (IOException exception) {
                ResponseOutputer.appendError("The download file is a directory/cannot be opened!");
            }
        } else ResponseOutputer.appendError("System variable with boot file not found!");
    }

    /**
     * Reads collection from a file.
     *
     * @return Readed collection.
     */
    public ArrayList<Organization> readCollection(){
        String filePath = System.getenv().get(envVariable);
        if (filePath != null){
            try (Scanner fileScanner = new Scanner(new File(filePath))) {
                ArrayList<Organization> collection;
                Type collectionType = new TypeToken<ArrayList<Organization>>() {}.getType();
                collection = gson.fromJson(fileScanner.nextLine().trim(), collectionType);
                Outputer.println("Collection loaded successfully.");
                App.logger.info("Collection loaded successfully.");
                return collection;

            } catch (FileNotFoundException exception){
                Outputer.printError("Boot file not found!");
                App.logger.warn("Boot file not found!");
            } catch (JsonParseException | NullPointerException exception) {
                Outputer.printError("The correct collection was not found in the boot file!");
                App.logger.error("The correct collection was not found in the boot file!");
            }
            catch (IllegalStateException exception) {
                Outputer.printError("Unexpected error!");
                App.logger.fatal("Unexpected error!");
                System.exit(0);
            } catch (NoSuchElementException exception){
                ResponseOutputer.appendln("The collection is empty");
                App.logger.warn("The collection is empty");
            }

        } else {
            Outputer.printError("Boot file system variable not found!");
            App.logger.error("Boot file system variable not found!");
        }
        return new ArrayList<>();
    }

}
