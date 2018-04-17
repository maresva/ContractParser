package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.model.ConditionType;
import cz.zcu.kiv.contractparser.model.Contract;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ContractExtractorJSR305Test {

    private String pathStart;
    private ClassLoader classLoader;

    private void setUp(){

        pathStart = "testFiles/extractor/JSR305/";
        classLoader = getClass().getClassLoader();
    }


    @Test
    void testJSR305(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, ConditionType.PRE, "@Nonnull String x", "Nonnull",
                "String x");
    }

    @Test
    void testJSR305PostCondition(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305PostCondition.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, ConditionType.POST, "@Nonnull", "Nonnull",
                "");
    }

    @Test
    void testJSR305Invariant(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "testJSR305Invariant.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, ConditionType.INVARIANT, "@Nonnull", "Nonnull",
                "");
    }


    @Test
    void testJSR305DiffFunction(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305DiffFunction.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, ConditionType.PRE, "@CheckForNull String x", "CheckForNull",
                "String x");
    }


    @Test
    void testJSR305BadFunction(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305BadFunction.java");
        assertEquals(0, javaFile.getContracts().size());
    }

    @Test
    void testJSR305ManyFunctions(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305ManyFunctions.java");
        assertEquals(6, javaFile.getContracts().size());
    }


    @Test
    void testJSR3053Contracts(){

        testJSR305Get3Contracts("TestJSR3053Contracts.java");
    }


    @Test
    void testJSR3052Methods(){

        testJSR305Get3Contracts("TestJSR3052Methods.java");
    }


    @Test
    void testJSR3052Classes(){

        testJSR305Get3Contracts("TestJSR3052Classes.java");
    }


    private void testJSR305Get3Contracts(String filename){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + filename);
        assertEquals(3, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, ConditionType.INVARIANT, "@CheckReturnValue", "CheckReturnValue",
                "");

        contract = javaFile.getContracts().get(1);
        basicCheck(contract, ConditionType.POST, "@Untainted", "Untainted",
                "");

        contract = javaFile.getContracts().get(2);
        basicCheck(contract, ConditionType.PRE, "@Nonnull String x", "Nonnull",
                "String x");
    }


    private void basicCheck(Contract contract, ConditionType conditionType, String completeExpression, String function, String expression){

        assertEquals(ContractType.JSR305, contract.getContractType());
        assertEquals(conditionType, contract.getConditionType());
        assertEquals(completeExpression, contract.getCompleteExpression());
        assertEquals(function, contract.getFunction());
        assertEquals(expression, contract.getExpression());
    }


    private JavaFile getTestJavaFile(String path) {
        File fileJavaFile = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        return ContractExtractorApi.retrieveContracts(fileJavaFile, false);
    }
}