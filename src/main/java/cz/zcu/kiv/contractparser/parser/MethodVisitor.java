package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import cz.zcu.kiv.contractparser.model.JavaClass;
import cz.zcu.kiv.contractparser.model.JavaMethod;

/**
 * @Author Václav Mareš
 */
public class MethodVisitor extends VoidVisitorAdapter {


    // TODO v soucasnosti se nepouziva
    /*
    public void visit(MethodDeclaration n, Object arg) {

        JavaClass javaClass = (JavaClass) arg;

        JavaMethod javaMethod = new JavaMethod();
        javaMethod.signature = n.getDeclarationAsString();
        javaMethod.body = n.getBody().toString();
        //javaMethod.comments = n.getJavadoc().toString();

        javaClass.methods.add(javaMethod);
    }            */
}
