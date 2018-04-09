package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.google.common.base.Preconditions;
import cz.zcu.kiv.contractparser.ResourceHandler;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.*;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.NoSuchElementException;

import static cz.zcu.kiv.contractparser.io.IOServices.decompileClassFile;

/**
 * This class provides methods to parse file with Java source code to later extract contracts from it.
 *
 * @author Vaclav Mares
 */
public class JavaFileParser {

    /** Log4j logger for this class */
    private final static Logger logger = Logger.getLogger(String.valueOf(JavaFileParser.class));


    /**
     * Parses *.java or *.class file to create JavaFile structure which contains all crucial information
     * to retrieve design by contracts. It goes through the file and saves all the classes, methods and their
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

            tempFile = new File(ResourceHandler.getProperties().getString("decompileTempFile"));

            if(!decompileClassFile(file.getPath())){
                return null;
            }
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
            logger.error(e.getMessage());
            return null;
        }
        
        // parse the file (log any exception thrown by the parser
        CompilationUnit cu;
        try {
            cu = com.github.javaparser.JavaParser.parse(fileInputStream);
        } catch (Exception e){
            logger.warn(ResourceHandler.getMessage("errorJavaParserNotParsed", extendedJavaFile.getFullPath()));
            logger.warn(e.getMessage());
            return null;
        }

        // get all the classes present fileInputStream the java file
        for(NodeList<?> nodeList : cu.getNodeLists()){

            for(Object node : nodeList) {

                // if the node is class, interface or enum declaration
                if(node.getClass() == ClassOrInterfaceDeclaration.class || node.getClass() == EnumDeclaration.class) {

                    // if the node is class or interface declaration
                    if (node.getClass() == ClassOrInterfaceDeclaration.class) {

                        // save the class/IF as extendedJavaClass
                        ClassOrInterfaceDeclaration classDeclaration = (ClassOrInterfaceDeclaration) node;

                        ExtendedJavaClass extendedJavaClass = new ExtendedJavaClass(classDeclaration.getNameAsString(),
                                prepareClassSignature(classDeclaration));

                        // save class constructors
                        for (ConstructorDeclaration constructor : classDeclaration.getConstructors()) {

                            ExtendedJavaMethod extendedJavaMethod = prepareContract(constructor);
                            if (extendedJavaMethod != null) {
                                extendedJavaClass.addExtendedJavaMethod(extendedJavaMethod);
                                extendedJavaFile.getJavaFileStatistics().increaseNumberOfMethods(1);
                            }
                        }

                        // save annotations of the class
                        for (AnnotationExpr annotationExpr : classDeclaration.getAnnotations()) {
                            extendedJavaClass.addAnnotation(annotationExpr);
                        }

                        extendedJavaFile.addExtendedJavaClass(extendedJavaClass);
                        extendedJavaFile.getJavaFileStatistics().increaseNumberOfClasses(1);
                    }
                    // if the node is enum declaration
                    else if (node.getClass() == EnumDeclaration.class) {

                        EnumDeclaration enumDeclaration = (EnumDeclaration) node;

                        ExtendedJavaClass extendedJavaClass = new ExtendedJavaClass(enumDeclaration.getNameAsString(),
                                prepareEnumSignature(enumDeclaration));

                        // save annotations of the class
                        for (AnnotationExpr annotationExpr : enumDeclaration.getAnnotations()) {
                            extendedJavaClass.addAnnotation(annotationExpr);
                        }

                        extendedJavaFile.addExtendedJavaClass(extendedJavaClass);
                        extendedJavaFile.getJavaFileStatistics().increaseNumberOfClasses(1);
                    }
                }
            }
        }

        // go through each method fileInputStream given file using the Method visitor
        MethodVisitor visitor = new MethodVisitor();
        visitor.visit(cu, extendedJavaFile);

        try {
            fileInputStream.close();
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        
        if(tempFile != null && tempFile.exists()){
            tempFile.delete();
        }

        return extendedJavaFile;
    }


    /**
     * Prepares ExtendedJavaMethod object for contract extraction.
     *
     * @param constructor   Constructor declaration
     * @return              Prepared ExtendedJavaMethod
     */
    private static ExtendedJavaMethod prepareContract(ConstructorDeclaration constructor){

        // initialize ExtendedJavaMethod object and save all relevant information
        ExtendedJavaMethod extendedJavaMethod = new ExtendedJavaMethod(constructor.getDeclarationAsString(), true);

        // save annotations
        for (AnnotationExpr annotationExpr : constructor.getAnnotations()) {
            extendedJavaMethod.addAnnotation(annotationExpr);
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
     *
     * @param file  File to be prepared
     * @return      ExtendedJavaFile prepared based on information gathered from given file
     */
    private static ExtendedJavaFile prepareFile(File file) {

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
        extendedJavaFile.setFullPath(file.getAbsolutePath());
        extendedJavaFile.setShortPath(file.getPath());

        return extendedJavaFile;
    }


    /**
     * Prepares signature of class or interface from complex object to simple String.
     *
     * @param classOrInterfaceDeclaration   ClassOrInterfaceDeclaration
     * @return                              String with signature
     */
    private static String prepareClassSignature(ClassOrInterfaceDeclaration classOrInterfaceDeclaration){

        StringBuilder signature = new StringBuilder();
        for(Modifier modifier : classOrInterfaceDeclaration.getModifiers()) {

            signature.append(modifier.toString().toLowerCase()).append(" ");
        }

        if(classOrInterfaceDeclaration.isInterface()){
            signature.append("interface");
        }
        else{
            signature.append("class");
        }

        signature.append(" ");
        signature.append(classOrInterfaceDeclaration.getNameAsString());

        return signature.toString();
    }


    /**
     * Prepares signature of enum from complex object to simple String.
     *
     * @param enumDeclaration   EnumDeclaration
     * @return                  String with signature
     */
    private static String prepareEnumSignature(EnumDeclaration enumDeclaration){

        StringBuilder signature = new StringBuilder();
        for(Modifier modifier : enumDeclaration.getModifiers()) {

            signature.append(modifier.toString().toLowerCase()).append(" ");
        }

        signature.append("enum");
        signature.append(" ");
        signature.append(enumDeclaration.getNameAsString());

        return signature.toString();
    }
}
