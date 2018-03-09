package cz.zcu.kiv.contractparser.io;


import com.google.gson.Gson;
import com.strobel.decompiler.Decompiler;
import cz.zcu.kiv.contractparser.model.*;
import com.strobel.decompiler.PlainTextOutput;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides methods for IO operations such as get file extension or exporting to JSON.
 *
 * @author Vaclav Mares
 */
public class IOServices {

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

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                files = getFilesFromFolder(fileEntry, files);
            } else {

                // get name and extension of the file
                String[] nameAndExtension = getFileNameAndExtension(fileEntry);

                // if file has extension and it is supported one - add file to list
                if(nameAndExtension != null && nameAndExtension.length == 2){
                    for(FileType fileType : FileType.values()){
                        if(nameAndExtension[1].equals(fileType.toString().toLowerCase())){
                            files.add(fileEntry);
                            break;
                        }
                    }
                }
            }
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
            extensionAndName[1].toLowerCase();
        }

        if(extensionAndName[0] == null || extensionAndName[1] == null){
            return null;
        }

        return extensionAndName;
    }


    // TODO používá se ?
    public static void decompileClassFile(String filename, String tempFile){

        //System.out.println("Decompiling class file...");

        try {
            final FileOutputStream stream = new FileOutputStream(tempFile);
            final PrintWriter writer = new PrintWriter(stream);
            Decompiler.decompile(filename, new PlainTextOutput(writer));
            writer.flush();
        }
        catch (Exception e){

        }

        //System.out.println("Class file decompile completed.");
    }


    /**
     * This method exports given parsed java file to JSON
     *
     * @param javaFile  Input java file
     * @param outputFolder  Output folder
     */
    public static void exportToJson(JavaFile javaFile, File outputFolder){
                
        Gson gson = new Gson();
        String json = gson.toJson(javaFile);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter( new FileWriter(outputFolder.toString() + "/" + javaFile.getFileName()
                    + javaFile.getFileType() + ".json"));
            writer.write(json);
        }
        catch ( IOException e) {

        }
        finally {
            try {
                if ( writer != null) {
                    writer.close();
                }
            }
            catch ( IOException e) {

            }
        }
    }


    public static void exportManyToJson(List<JavaFile> javaFiles, File outputFolder) {

        if (!outputFolder.exists()){
            boolean success = outputFolder.mkdirs();

            if(!success){
                // TODO Handle error
                System.out.println("ERROR: Output folder could not be created");
                return;
            }
        }

        if(outputFolder == null || !outputFolder.exists() || outputFolder.isFile()){
            // TODO Handle error
            System.out.println("ERROR: Output folder doesn't exist");
            return;
        }

        for(JavaFile javaFile : javaFiles){
            exportToJson(javaFile, outputFolder);
        }
    }
}
