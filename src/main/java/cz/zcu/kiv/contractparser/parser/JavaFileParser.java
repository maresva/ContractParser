package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.google.common.base.Preconditions;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import static cz.zcu.kiv.contractparser.io.IOServices.decompileClassFile;

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
     * @return        ExtendedJavaFile with structured data
     */
    public static ExtendedJavaFile parseFile(File file) {

        ExtendedJavaFile extendedJavaFile = prepareFile(file);

        if(extendedJavaFile == null){
            return null;
        }

        // if the original file was a *.class file its decompiled version is saved fileInputStream tempFile
        File tempFile = null;
        if(extendedJavaFile.getFileType() == FileType.CLASS) {

            tempFile = new File("tempFile");
            decompileClassFile(file.getPath(), "tempFile");
        }

        // prepare input stream
        FileInputStream fileInputStream;
        try {
            if(tempFile != null){
                fileInputStream = new FileInputStream(tempFile);
            }
            else {
                fileInputStream = new FileInputStream(file);
            }
        } catch (FileNotFoundException e) {
            logger.error("Error while reading file.");
            logger.error(e.getMessage());
            return null;
        }

        
        // parse the file
        CompilationUnit cu = com.github.javaparser.JavaParser.parse(fileInputStream);

        // get all the classes present fileInputStream the java file
        for(NodeList<?> nodeList : cu.getNodeLists()){

            for(Object node : nodeList) {

                // if the node is class or interface declaration
                if(node.getClass() == ClassOrInterfaceDeclaration.class){

                    // save the class/IF as extendedJavaClass
                    ClassOrInterfaceDeclaration classDeclaration = (ClassOrInterfaceDeclaration) node;
                    ExtendedJavaClass extendedJavaClass = new ExtendedJavaClass(classDeclaration.getNameAsString());

                    // save class constructors
                    for(ConstructorDeclaration constructor : classDeclaration.getConstructors()){

                        ExtendedJavaMethod extendedJavaMethod = prepareContract(constructor);
                        if(extendedJavaMethod!= null){
                            extendedJavaClass.addExtendedJavaMethod(extendedJavaMethod);
                        }
                    }

                    // save annotations of the class
                    for(AnnotationExpr annotationExpr : classDeclaration.getAnnotations()) {
                        extendedJavaClass.addAnnotation(annotationExpr.toString());
                    }

                    extendedJavaFile.addExtendedJavaClass(extendedJavaClass);
                    extendedJavaFile.increaseNumberOfClasses(1);
                }
            }
        }

        // go through each method fileInputStream given file using the Method visitor
        MethodVisitor visitor = new MethodVisitor();
        visitor.visit(cu, extendedJavaFile);

        try {
            fileInputStream.close();
        } catch (IOException e) {
            logger.warn("Input stream for Java file parser couldn't be closed.");
            logger.warn(e.getMessage());
        }

        if(tempFile != null && tempFile.exists()){
            if(!tempFile.delete()){
                logger.warn("Temporary file could not be deleted.");
            }
        }

        return extendedJavaFile;
    }


    private static ExtendedJavaMethod prepareContract(ConstructorDeclaration constructor){

        // initialize ExtendedJavaMethod object and save all relevant information
        ExtendedJavaMethod extendedJavaMethod = new ExtendedJavaMethod(constructor.getDeclarationAsString(), true);

        // save annotations
        for (AnnotationExpr annotationExpr : constructor.getAnnotations()) {
            extendedJavaMethod.addAnnotation(annotationExpr.toString());
        }

        // save parameters
        for(Parameter parameter : constructor.getParameters()){
            extendedJavaMethod.addParameter(parameter);
        }

        // save method body as individual nodes (can be converted to statements)
        try {
            for (Node node : constructor.getBody().getChildNodes()) {
                extendedJavaMethod.addBodyNode(node);
            }
        }
        catch (NoSuchElementException e){
            logger.warn("Could not retrieve method body nodes.");
            logger.warn(e.getMessage());
            return null;
        }

        return extendedJavaMethod;
    }


    /**
     * This method prepares file to be parsed and it creates empty javaFile which is later filled with data.
     * It can process *.java or *.class files.
     */
    public static ExtendedJavaFile prepareFile(File file) {

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

        ExtendedJavaFile extendedJavaFile = new ExtendedJavaFile();

        // save extension to the contractFile if valid or throw error otherwise
        if(extension != null) {
            switch (extension) {
                case "java":
                    extendedJavaFile.setFileType(FileType.JAVA);
                    break;
                case "class":
                    extendedJavaFile.setFileType(FileType.CLASS);
                    break;
                default:
                    logger.warn("File " + file.getName()  + " not parsed because of unsupported file extension. ("
                            + extension + ")");
                    return null;
            }
        }

        extendedJavaFile.setFileName(fileName);
        extendedJavaFile.setPath(file.getPath());

        return extendedJavaFile;
    }
}
