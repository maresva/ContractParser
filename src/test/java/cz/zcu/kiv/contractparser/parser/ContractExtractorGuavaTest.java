package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.model.ConditionType;
import cz.zcu.kiv.contractparser.model.Contract;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


class ContractExtractorGuavaTest {

    private String pathStart;
    private ClassLoader classLoader;

    private void setUp(){

        pathStart = "testFiles/extractor/Guava/";
        classLoader = getClass().getClassLoader();
    }


    @Test
    void testGuava(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuava.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, "Preconditions.checkNotNull(x);", "checkNotNull",
                "x", 0);
    }


    @Test
    void testGuava2args(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuava2args.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, "Preconditions.checkNotNull(x, \"x must not be null.\");",
                "checkNotNull", "x", 1);

        assertEquals("\"x must not be null.\"", contract.getArguments().get(0));
    }

    
    @Test
    void testGuavaDiffFunction(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaDiffFunction.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, "Preconditions.checkArgument(x.length > 0);", "checkArgument",
                "x.length > 0", 0);
    }


    @Test
    void testGuavaBadFunction(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaBadFunction.java");
        assertEquals(0, javaFile.getContracts().size());
    }


    @Test
    void testGuavaNoParams(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaNoParams.java");
        assertEquals(0, javaFile.getContracts().size());
    }


    @Test
    void testGuavaFullScope(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaFullScope.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, "com.google.common.base.Preconditions.checkNotNull(x);",
                "checkNotNull", "x", 0);
    }


    @Test
    void testGuavaNoScope(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "testGuavaNoScope.java");
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, "checkNotNull(x);", "checkNotNull", "x", 0);
    }


    @Test
    void testGuavaBadScope(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "testGuavaBadScope.java");
        assertEquals(0, javaFile.getContracts().size());
    }


    @Test
    void testGuavaManyFunctions(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaManyFunctions.java");
        assertEquals(10, javaFile.getContracts().size());
    }


    @Test
    void testGuava2Contracts(){

        testGuavaGet2Contracts("TestGuava2Contracts.java");
    }


    @Test
    void testGuava2Methods(){

        testGuavaGet2Contracts("TestGuava2Methods.java");
    }


    @Test
    void testGuava2Classes(){

        testGuavaGet2Contracts("TestGuava2Classes.java");
    }


    private void testGuavaGet2Contracts(String filename){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + filename);
        assertEquals(2, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, "Preconditions.checkNotNull(x);", "checkNotNull",
                "x", 0);

        contract = javaFile.getContracts().get(1);
        basicCheck(contract, "Preconditions.checkArgument(x.length > 0);", "checkArgument",
                "x.length > 0", 0);
    }


    private void basicCheck(Contract contract, String completeExpression, String function, String expression, int argsLength){

        assertEquals(ContractType.GUAVA, contract.getContractType());
        assertEquals(ConditionType.PRE, contract.getConditionType());
        assertEquals(completeExpression, contract.getCompleteExpression());
        assertEquals(function, contract.getFunction());
        assertEquals(expression, contract.getExpression());
        assertEquals(argsLength, contract.getArguments().size());
    }


    private JavaFile getTestJavaFile(String path) {
        File fileJavaFile = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        return ContractExtractorApi.retrieveContracts(fileJavaFile, false);
    }
}