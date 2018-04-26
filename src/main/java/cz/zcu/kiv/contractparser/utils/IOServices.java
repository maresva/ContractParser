package cz.zcu.kiv.contractparser.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strobel.decompiler.Decompiler;
import com.strobel.decompiler.PlainTextOutput;
import cz.zcu.kiv.contractparser.model.FileType;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class provides methods for IO operations such as getting file extension, exporting to JSON etc.
 *
 * @author Vaclav Mares
 */
public final class IOServices {

    /** Log4j logger for this class */
    private final static Logger logger = Logger.getLogger(String.valueOf(IOServices.class));

    /** Private constructor to prevent its use */
    private IOServices() {}

    /**
     * This method gets all files from given folder
     *
     * @param folder    input folder
     * @param files     list of files used for recursion
     * @return          complete list of files
     */
    public static List<File> getFilesFromFolder(File folder, List<File> files) {

        if(files == null){
            files = new ArrayList<>();
        }

        if(!checkFolder(folder)){
            return files;
        }

        try{
            for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {

                if(fileEntry != null) {
                    if (fileEntry.isDirectory()) {
                        files = getFilesFromFolder(fileEntry, files);
                    } else {

                        // get name and extension of the file
                        String[] nameAndExtension = getFileNameAndExtension(fileEntry);

                        // if file has extension and it is supported one - add file to list
                        if (nameAndExtension != null && nameAndExtension.length == 2) {
                            for (FileType fileType : FileType.values()) {
                                if (nameAndExtension[1].equals(fileType.toString().toLowerCase())) {
                                    files.add(fileEntry);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (NullPointerException e){
            logger.warn(ResourceHandler.getMessage("errorFilesNotRetrieved", folder));
            return files;
        }

        return files;
    }


    /**
     * From given file separates file name and its extension. Then returns String array of size two
     * like [name, extension]
     *
     * @param file  Input file
     * @return      String array with name and extension
     */
    public static String[] getFileNameAndExtension(File file){

        String filename = file.getName();

        String extensionAndName[] = new String[2];

        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extensionAndName[0] = filename.substring(0,i);
            extensionAndName[1] = filename.substring(i+1);
            extensionAndName[1] = extensionAndName[1].toLowerCase();
        }

        if(extensionAndName[0] == null || extensionAndName[1] == null){
            return null;
        }

        return extensionAndName;
    }


    /**
     * Decompiles given java class file to extract its source code.
     *
     * @param filename  Name of a file that should be decompiled
     * @return  boolean if the decompilation was successful
     */
    public static boolean decompileClassFile(String filename){

        // Temporary file where is the decompilation stored
        String tempFile = ResourceHandler.getProperties().getString("decompileTempFile");

        try {
            final FileOutputStream stream = new FileOutputStream(tempFile);
            final PrintWriter writer = new PrintWriter(stream);

            Decompiler.decompile(filename, new PlainTextOutput(writer));
            writer.flush();

            // Decompiler library writes fixed error message as a first line of the file.
            // Thus there has to be this kind of error check.
            BufferedReader bufferedReader = new BufferedReader(new FileReader(tempFile));
            String firstLine = bufferedReader.readLine();

            if(firstLine.compareTo(ResourceHandler.getMessage("decompilerError", filename)) == 0){
                throw new Exception();
            }
        }
        catch (Exception e){
            logger.error(ResourceHandler.getMessage("errorDecompileFail", filename));
            if(e.getMessage() != null) {
                logger.error(e.getMessage());
            }
            
            return false;
        }

        logger.info(ResourceHandler.getMessage("infoDecompiled", filename));
        return true;
    }


    /**
     * Saves given object to file in JSON format.
     *
     * @param object            Object to be saved to file as a JSON
     * @param filename          Name of the file where should be JSON saved
     * @param outputFolder      Folder where should be the file saved
     * @param prettyPrint       Whether JSON should be in human readable form or not
     * @return                  True if operation was successful
     */
    public static boolean saveObjectAsJson(Object object, String filename, File outputFolder, boolean prettyPrint) {

        String json = createJsonString(object, prettyPrint);

        return json != null && saveJsonToFile(json, filename, outputFolder);
    }


    /**
     * Creates JSON string from given object. If the object is not support by Gson library exception is thrown.
     * If prettyPrint flag is enabled output JSON is formatted to be human readable otherwise it is min JSON.
     *
     * @param object        Input object to be transformed to JSON
     * @param prettyPrint   Whether JSON should be in human readable form or not
     * @return              String with JSON or null in case of error
     */
    private static String createJsonString(Object object, boolean prettyPrint){

        if(object == null){
            logger.error(ResourceHandler.getMessage("errorJsonObjectNull"));
            return null;
        }

        Gson gson;
        String json = null;

        // initialize Gson with pretty print if requested
        if(prettyPrint){
            gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        }
        else{
            gson = new GsonBuilder().disableHtmlEscaping().create();
        }

        try{
            json = gson.toJson(object);
        }
        catch(Exception e){
            logger.error(ResourceHandler.getMessage("errorJsonObjectUnsupportedClass", object.getClass()));
            logger.error(e.getMessage());
        }

        return json;
    }


    /**
     *
     * @param json          String containing object as a JSON
     * @param filename      Name of the file where should be JSON saved
     * @param outputFolder  Folder where should be the file saved
     * @return              True if operation was successful
     */
    private static boolean saveJsonToFile(String json, String filename, File outputFolder) {

        if(checkFolder(outputFolder)) {

            // prepare absolute path of given file
            String path = getAbsolutePath(outputFolder) + File.separator + filename
                    + ResourceHandler.getProperties().getString("jsonExtension");

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(path));
                writer.write(json);
            } catch (IOException e) {
                logger.error(e.getMessage());
                return false;
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    logger.warn(e.getMessage());
                }
            }

            logger.info(ResourceHandler.getMessage("infoJsonExported", path));
        }
        else{
            return false;
        }

        return true;
    }


    /**
     * Checks whether given File is a folder. If it doesn't exists it tries to created it.
     *
     * @param folder    File folder
     * @return          True if folder is ready to use, false otherwise
     */
    public static boolean checkFolder(File folder){

        if(folder == null){
            logger.error(ResourceHandler.getMessage("errorFolderCheckNull"));
            return false;
        }

        if(folder.isFile()){
            logger.error(ResourceHandler.getMessage("errorFolderCheckFile", folder));
            return false;
        }

        if (!folder.exists()){
            boolean success = folder.mkdirs();

            if(!success){
                logger.error(ResourceHandler.getMessage("errorFolderCheckCannotCreate", folder));
                return false;
            }
        }

        if(!folder.exists()){
            logger.error(ResourceHandler.getMessage("errorFolderCheckCannotCreate", folder));
            return false;
        }

        return true;
    }


    /**
     * Replaces characters in given path to allow use it as a file name. Characters are replaced with other
     * based on properties.
     *
     * @param path      String with path to be escaped
     * @return          String with escaped path
     */
    public static String escapeFilePath(String path) {

        String replacedFilePath = path.replace(File.separator, ResourceHandler.getProperties()
                .getString("charReplacementSlash"));
        replacedFilePath = replacedFilePath.replace(":", ResourceHandler.getProperties()
                .getString("charReplacementColon"));

        return replacedFilePath;
    }


    /**
     * Retrieves absolute path of given file.
     *
     * @param file  Input file
     * @return      Absolute path
     */
    public static String getAbsolutePath(File file){

        String path;

        try {
            path = file.getCanonicalPath();
        } catch (IOException e) {
            path = file.getPath();
            logger.warn(ResourceHandler.getMessage("errorFileAbsolutePathNotRetrieved", path));
        }

        return path;
    }
}
