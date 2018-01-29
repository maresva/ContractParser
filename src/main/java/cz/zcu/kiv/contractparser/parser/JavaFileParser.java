package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.google.common.base.Preconditions;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.FileType;
import cz.zcu.kiv.contractparser.model.JavaClass;
import cz.zcu.kiv.contractparser.model.JavaFile;
import cz.zcu.kiv.contractparser.model.JavaMethod;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This class provides methods to parse file with Java source code to later extract contracts from it.
 *
 * @author Vaclav Mares
 */
public class JavaFileParser {

    final static Logger logger = Logger.getLogger(String.valueOf(JavaFileParser.class));


    /**
     * Parses *.java or *.class file to create javaFile structure which contains all crucial information
     * to retrieve design by contracts . It goes through the file and saves all the classes, methods and their
     * parameters.
     *
     * @param file    Input file to be parsed
     * @return        JavaFile with structured data
     */
    public static JavaFile parseFile(File file) {

        JavaFile javaFile = prepareFile(file);

        if(javaFile == null){
            return null;
        }

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // parse the file
        CompilationUnit cu = com.github.javaparser.JavaParser.parse(in);


        for(NodeList<?> x : cu.getNodeLists()){

            for(Object y : x){

                if(y.getClass().getName() == "com.github.javaparser.ast.ImportDeclaration"){
                    ImportDeclaration id = (ImportDeclaration) y;
                    javaFile.addImport(id.getNameAsString());
                }

                if(y.getClass().getName() == "com.github.javaparser.ast.body.ClassOrInterfaceDeclaration") {

                    ClassOrInterfaceDeclaration classOrIn = (ClassOrInterfaceDeclaration) y;

                    JavaClass javaClass = new JavaClass();
                    javaClass.setName(classOrIn.getNameAsString());

                    // save individual methods
                    MethodVisitor visitor = new MethodVisitor();
                    visitor.visit(cu, javaClass);

                    javaFile.addClass(javaClass);
                }
            }
        }

        return javaFile;
    }


    /**
     * This method prepares file to be parsed and it creates empty javaFile which is later filled with data.
     * It can process *.java or *.class files.
     */
    public static JavaFile prepareFile(File file) {

        logger.info("Parsing file " + file);

        // check whether file exists and is not directory
        Preconditions.checkArgument(file.exists(), "Given file doesn't exist.");
        Preconditions.checkArgument(file.isFile(), "Expected file not directory");

        // get file name and its extension
        String[] extensionAndName = IOServices.getFileNameAndExtension(file);

        String fileName = "";
        String extension = "";

        if(extensionAndName != null && extensionAndName.length == 2) {
            fileName = extensionAndName[0];
            extension = extensionAndName[1];
        }

        JavaFile javaFile = new JavaFile();

        // save extension to the contractFile if valid or throw error otherwise
        if(extension != null) {
            switch (extension) {
                case "java":
                    javaFile.setFileType(FileType.JAVA);
                    break;
                case "class":
                    javaFile.setFileType(FileType.CLASS);
                    break;
                default:
                    logger.warn("File " + file.getName()  + " not parsed because of unsupported file extension. ("
                            + extension + ")");
                    return null;
            }
        }

        javaFile.setFileName(fileName);

        return javaFile;
    }
}
