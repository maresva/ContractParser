package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import cz.zcu.kiv.contractparser.ResourceHandler;
import cz.zcu.kiv.contractparser.model.ExtendedJavaFile;
import cz.zcu.kiv.contractparser.model.ExtendedJavaMethod;
import org.apache.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This method extends visitor of JavaParser library and it provides means to extract methods from given Java file.
 * It goes through all the methods of given file. From given information ExtendedJavaMethod is created and is saved
 * to its parent class and file respectively.
 *
 * @author Vaclav Mares
 */
public class MethodVisitor extends VoidVisitorAdapter {

    private final static Logger logger = Logger.getLogger(String.valueOf(JavaFileParser.class));

    /**
     * This method goes through method and saves all important elements such as signature or the body. Statements
     * in body are analyzed and only those that can contain Contract are preserved.
     *
     * @param methodDeclaration     MethodDeclaration containing all method information
     * @param arg   generic Object arguments - here it is ExtendedJavaClass to where method belongs
     */
    public void visit(MethodDeclaration methodDeclaration, Object arg) {
        //System.out.println(methodDeclaration.getBody().get().getStatements().get(0));
        super.visit(methodDeclaration, arg);

        // retrieve argument which is parent ExtendedJavaFile
        ExtendedJavaFile extendedJavaFile = (ExtendedJavaFile) arg;
        if(extendedJavaFile == null){
            logger.warn(ResourceHandler.getMessage("errorFileNotParsedNull"));
            return;
        }

        // get parent node of method
        Optional parent = methodDeclaration.getParentNode();
        Node nodeParent = null;
        if(parent.isPresent()){
            nodeParent = (Node) parent.get();
        }

        Node topParent = null;

        if(nodeParent == null){
            logger.warn(ResourceHandler.getMessage("errorFileNotParsedParent", extendedJavaFile.getFullPath()));
            return;
        }

        // if the node is class declaration 
        if(nodeParent.getClass() == ClassOrInterfaceDeclaration.class || nodeParent.getClass() == EnumDeclaration.class){
            topParent = nodeParent;
        }

        // go through parents until the root parent is found (parent class/interface)
        while(nodeParent.getParentNode().isPresent() && nodeParent.getParentNode().get().getClass() != CompilationUnit.class) {

            nodeParent = nodeParent.getParentNode().get();

            if(nodeParent.getClass() == ClassOrInterfaceDeclaration.class || nodeParent.getClass() == EnumDeclaration.class){
                topParent = nodeParent;
            }
        }

        if(topParent == null){
            logger.warn(ResourceHandler.getMessage("errorFileNotParsedParent", extendedJavaFile.getFullPath()));
        }
        else {

            // initialize ExtendedJavaMethod object and save all relevant information
            ExtendedJavaMethod extendedJavaMethod = new ExtendedJavaMethod(methodDeclaration.getDeclarationAsString(),
                    false);

            // save annotations
            for (AnnotationExpr annotationExpr : methodDeclaration.getAnnotations()) {
                extendedJavaMethod.addAnnotation(annotationExpr);
            }

            // save parameters
            for(Parameter parameter : methodDeclaration.getParameters()){
                extendedJavaMethod.addParameter(parameter);
            }

            // save method body as individual nodes (can be converted to statements)
            try {
                if(methodDeclaration.getBody().isPresent()) {
                    for (Node node : methodDeclaration.getBody().get().getChildNodes()) {
                        if (isNodePerspective(node)) {

                            // remove comments from node and save it
                            node.removeComment();
                            extendedJavaMethod.addBodyNode(node);
                        }
                    }
                }
            }
            catch (NoSuchElementException e){
                logger.warn(ResourceHandler.getMessage("errorParserBodyNotRetrieved",
                        methodDeclaration.getDeclarationAsString()));
                logger.warn(e.getMessage());
                return;
            }

            // find parent class and save extendedJavaMethod
            for (int i = 0; i < extendedJavaFile.getExtendedJavaClasses().size(); i++) {

                if (extendedJavaFile.getExtendedJavaClasses().get(i) != null) {

                    String topParentName = "";

                    if (topParent.getClass() == ClassOrInterfaceDeclaration.class) {

                        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = (ClassOrInterfaceDeclaration) topParent;
                        topParentName = classOrInterfaceDeclaration.getNameAsString();
                    } else if (topParent.getClass() == EnumDeclaration.class) {

                        EnumDeclaration enumDeclaration = (EnumDeclaration) topParent;
                        topParentName = enumDeclaration.getNameAsString();
                    }

                    if (extendedJavaFile.getExtendedJavaClasses().get(i).getName().compareTo(topParentName) == 0) {

                        extendedJavaFile.getExtendedJavaClasses().get(i).addExtendedJavaMethod(extendedJavaMethod);
                        extendedJavaFile.getJavaFileStatistics().increaseNumberOfMethods(1);
                    }
                }
            }
        }
    }


    /**
     * This method determines whether the given node is worth keeping for farther analysis. If the node is for
     * example a comment false is returned.
     *
     * @param node  Node to be analyzed
     * @return  true if node is perspective
     */
    private boolean isNodePerspective(Node node){

        // if the node is comment - return false
        return !(node instanceof LineComment) && !(node instanceof BlockComment);
    }
}