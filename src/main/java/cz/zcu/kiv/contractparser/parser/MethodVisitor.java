package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import cz.zcu.kiv.contractparser.model.JavaClass;
import cz.zcu.kiv.contractparser.model.JavaMethod;

import java.util.List;

/**
 * @author Vaclav Mares
 */
public class MethodVisitor extends VoidVisitorAdapter {

    public void visit(MethodDeclaration n, Object arg) {

        JavaClass javaClass = (JavaClass) arg;

        JavaMethod javaMethod = new JavaMethod();
        javaMethod.setSignature(n.getDeclarationAsString());

        // save annotations
        for(AnnotationExpr annotationExpr : n.getAnnotations()){
            javaMethod.addAnnotation(annotationExpr.toString());
        }

        // save method body as individual nodes (can be converted to statements)
        for(Node node : n.getBody().get().getChildNodes()) {
            javaMethod.addBodyNode(node);
        }

        javaClass.addMethod(javaMethod);
    }
}
