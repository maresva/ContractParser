package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import cz.zcu.kiv.contractparser.model.ExtendedJavaClass;
import cz.zcu.kiv.contractparser.model.ExtendedJavaMethod;

import java.util.NoSuchElementException;

/**
 * @author Vaclav Mares
 */
public class MethodVisitor extends VoidVisitorAdapter {

    /**
     * This method goes through method and saves all important elements such as signature or the body. Statements
     * in body are analyzed and only those that can contain Contract are preserved.
     *
     * @param n     MethodDeclaration containing all method information
     * @param arg   generic Object arguments - here it is ExtendedJavaClass to where method belongs
     */
    public void visit(MethodDeclaration n, Object arg) {

        ExtendedJavaClass extendedJavaClass = (ExtendedJavaClass) arg;

        if(n != null && extendedJavaClass != null) {

            ExtendedJavaMethod extendedJavaMethod = new ExtendedJavaMethod();
            extendedJavaMethod.setSignature(n.getDeclarationAsString());

            // save annotations
            for (AnnotationExpr annotationExpr : n.getAnnotations()) {
                extendedJavaMethod.addAnnotation(annotationExpr.toString());
            }

            for(Parameter parameter : n.getParameters()){
                extendedJavaMethod.addParameter(parameter);
            }

            // save method body as individual nodes (can be converted to statements)
            try {

                for (Node node : n.getBody().get().getChildNodes()) {

                    if(isNodePerspective(node)) {
                        extendedJavaMethod.addBodyNode(node);
                    }
                }
            }
            catch (NoSuchElementException e){
                // TODO
            }

            // add method to class and increase method counter
            extendedJavaClass.addExtendedJavaMethod(extendedJavaMethod);
            extendedJavaClass.increaseNumberOfMethods(1);
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
