package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import cz.zcu.kiv.contractparser.model.ExtendedJavaFile;
import cz.zcu.kiv.contractparser.model.ExtendedJavaMethod;
import org.apache.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author Vaclav Mares
 */
public class MethodVisitor extends VoidVisitorAdapter {

    final static Logger logger = Logger.getLogger(String.valueOf(JavaFileParser.class));

    /**
     * This method goes through method and saves all important elements such as signature or the body. Statements
     * in body are analyzed and only those that can contain Contract are preserved.
     *
     * @param methodDeclaration     MethodDeclaration containing all method information
     * @param arg   generic Object arguments - here it is ExtendedJavaClass to where method belongs
     */
    public void visit(MethodDeclaration methodDeclaration, Object arg) {

        super.visit(methodDeclaration, arg);

        System.out.println(methodDeclaration.getName());

        // retrieve argument which is parent ExtendedJavaFile
        ExtendedJavaFile extendedJavaFile = (ExtendedJavaFile) arg;
        if(extendedJavaFile == null){
            logger.warn("ExtendedJavaFile was null.");
            return;
        }

        // get parent node of method
        Optional parent = methodDeclaration.getParentNode();
        Node nodeParent = null;
        if(parent.isPresent()){
            nodeParent = (Node) parent.get();
        }

        // go through parents until the root parent is found (parent class/interface)
        while(nodeParent != null) {

            if(nodeParent.getParentNode().isPresent() &&
                    nodeParent.getParentNode().get().getClass() != CompilationUnit.class){
                nodeParent = nodeParent.getParentNode().get();
            }
            else{
                break;
            }
        }

        ClassOrInterfaceDeclaration classDeclaration;
        try{
            classDeclaration = (ClassOrInterfaceDeclaration) nodeParent;
        }
        catch (ClassCastException e){
            logger.warn("Could not retrieve parent class.");
            logger.warn(e.getMessage());
            return;
        }

        if(classDeclaration != null) {

            // initialize ExtendedJavaMethod object and save all relevant information
            ExtendedJavaMethod extendedJavaMethod = new ExtendedJavaMethod(methodDeclaration.getDeclarationAsString(),
                    false);

            // save annotations
            for (AnnotationExpr annotationExpr : methodDeclaration.getAnnotations()) {
                extendedJavaMethod.addAnnotation(annotationExpr.toString());
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
                            extendedJavaMethod.addBodyNode(node);
                        }
                    }
                }
            }
            catch (NoSuchElementException e){
                logger.warn("Could not retrieve method body nodes.");
                logger.warn(e.getMessage());
                return;
            }

            // find parent class and save extendedJavaMethod
            for (int i = 0; i < extendedJavaFile.getExtendedJavaClasses().size(); i++) {

                if (extendedJavaFile.getExtendedJavaClasses().get(i) != null) {
                    if (extendedJavaFile.getExtendedJavaClasses().get(i).getName()
                            .compareTo(classDeclaration.getNameAsString()) == 0) {

                        extendedJavaFile.getExtendedJavaClasses().get(i).addExtendedJavaMethod(extendedJavaMethod);
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
    public boolean isNodePerspective(Node node){

        // if the node is comment - return false
        if(node instanceof LineComment || node instanceof BlockComment)  {
            return false;
        }
        else{
            String nodeString = node.toString();

            // library sometimes falsely returns commented code as a expression this work around stops it
            if(nodeString.length() >= 2 && nodeString.charAt(0) == '/' && nodeString.charAt(1) == '/'){
                return false;
            }

            return true;
        }
    }
}