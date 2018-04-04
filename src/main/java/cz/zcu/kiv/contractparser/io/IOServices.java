package cz.zcu.kiv.contractparser.io;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strobel.decompiler.Decompiler;
import cz.zcu.kiv.contractparser.comparator.JavaFileCompareReport;
import cz.zcu.kiv.contractparser.comparator.JavaFolderCompareReport;
import cz.zcu.kiv.contractparser.model.*;
import com.strobel.decompiler.PlainTextOutput;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for IO operations such as get file extension or exporting to JSON.
 *
 * @author Vaclav Mares
 */
public class IOServices {

    final static Logger logger = Logger.getLogger(String.valueOf(IOServices.class));
    

    public static File getFile(String filename){

        File file = new File(filename);

        return file;
    }


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

        if(folder == null || !folder.exists() || folder.isFile()){
            logger.error("Given folder doesn't exist or it is a file. (" + folder + ")");
            return files;
        }

        try{
            for (final File fileEntry : folder.listFiles()) {

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
            logger.warn("Unable to get files from folder: " + folder);
            return files;
        }

        return files;
    }


    /**
     * From given file separates file name and its extension. Then returns String array of size two
     * like [name, extension]
     *
     * @param file  Input file
     * @return      String array
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


    public static boolean decompileClassFile(String filename, String tempFile){

        // Error message that is set as a string in the decompiler library
        String decompilerError = "!!! ERROR: Failed to load class " + filename + ".";

        try {
            final FileOutputStream stream = new FileOutputStream(tempFile);
            final PrintWriter writer = new PrintWriter(stream);

            Decompiler.decompile(filename, new PlainTextOutput(writer));
            writer.flush();

            // Decompiler library writes error message as a first line of the file.
            // Thus there has to be this kind of error check.
            BufferedReader bufferedReader = new BufferedReader(new FileReader(tempFile));
            String firstLine = bufferedReader.readLine();

            if(firstLine.compareTo(decompilerError) == 0){
                throw new Exception();
            }
        }
        catch (Exception e){
            logger.error("File " + filename + " could not be decompiled.");
            if(e.getMessage() != null) {
                logger.error(e.getMessage());
            }
            
            return false;
        }

        return true;
    }


    /**
     * This method exports given parsed java file to JSON
     *
     * @param javaFile  Input java file
     * @param outputFolder  Output folder
     */
    public static boolean exportJavaFileToJson(JavaFile javaFile, File outputFolder){

        if(javaFile != null) {

            String json = createJsonString(javaFile, true);
            return saveJsonToFile(json, outputFolder, escapeFilePath(javaFile.getPath()));
        }
        else {
            return false;
        }
    }


    public static void exportJavaFilesToJson(List<JavaFile> javaFiles, File outputFolder) {

        JavaFileStatistics globalJavaFileStatistics = new JavaFileStatistics();

        boolean success = false;
        if(checkFolder(outputFolder)) {
            for (JavaFile javaFile : javaFiles) {
                globalJavaFileStatistics.mergeStatistics(javaFile.getJavaFileStatistics());
                if(exportJavaFileToJson(javaFile, outputFolder)){
                    success = true;
                }
            }

            if(success) {
                exportStatistics(globalJavaFileStatistics, outputFolder);
            }
        }
    }


    public static boolean exportJavaFileCompareReportToJson(JavaFileCompareReport javaFileCompareReport, File outputFolder) {

        if(javaFileCompareReport != null) {

            String json = createJsonString(javaFileCompareReport, true);
            return saveJsonToFile(json, outputFolder, escapeFilePath(javaFileCompareReport.getThisFilePath()));
        }
        else {
            return false;
        }
    }


    public static void exportJavaFolderCompareRoportsToJson(JavaFolderCompareReport javaFolderCompareReport, File outputFolder) {

        //JavaFileStatistics globalJavaFileStatistics = new JavaFileStatistics();

        boolean success = false;
        if(checkFolder(outputFolder)) {
            for (JavaFileCompareReport javaFileCompareReport : javaFolderCompareReport.getJavaFileCompareReports()) {
                if(exportJavaFileCompareReportToJson(javaFileCompareReport, outputFolder)){
                    success = true;
                }
            }

            if(success) {
                exportStatistics(javaFolderCompareReport, outputFolder);
            }
        }
    }




    public static void exportStatistics(Object statisticsObject, File outputFolder){
        
        String json = createJsonString(statisticsObject, true);
        saveJsonToFile(json, outputFolder, "_global_statistics");
    }


    public static String createJsonString(Object object, boolean prettyPrint){

        Gson gson;

        if(prettyPrint){
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        else{
            gson = new Gson();
        }

        return gson.toJson(object);
    }


    public static boolean saveJsonToFile(String json, File outputFolder, String filename) {

        if(checkFolder(outputFolder)) {

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(outputFolder.toString() + "/" + filename + ".json"));
                writer.write(json);
            } catch (IOException e) {
                logger.error("Export to JSON could not be realised.");
                logger.error(e.getMessage());
                return false;
            } finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                } catch (IOException e) {
                    logger.warn("JSON writer could not be closed.");
                    logger.warn(e.getMessage());
                }
            }
        }
        else{
            return false;
        }

        return true;
    }


    public static boolean checkFolder(File folder){

        if(folder == null){
            logger.error("Given folder " + folder + " doesn't exist.");
            return false;
        }

        if (!folder.exists()){
            boolean success = folder.mkdirs();

            if(!success){
                logger.error("Given folder " + folder + " couldn't be created.");
                return false;
            }
        }

        if(!folder.exists() || folder.isFile()){
            logger.error("Given folder " + folder + " couldn't be created or it is a file.");
            return false;
        }

        return true;
    }


    public static String escapeFilePath(String path) {

        String replacedFilePath = path.replace("\\", "!");
        replacedFilePath = replacedFilePath.replace(":", "&");

        return replacedFilePath;
    }
}
