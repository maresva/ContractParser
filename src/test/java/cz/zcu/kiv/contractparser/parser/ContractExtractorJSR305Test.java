package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.api.ApiFactory;
import cz.zcu.kiv.contractparser.api.ContractExtractorApi;
import cz.zcu.kiv.contractparser.api.DefaultContractExtractorApi;
import cz.zcu.kiv.contractparser.model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This test class contains set of tests used to check correctness of JSR305 parser.
 */
class ContractExtractorJSR305Test {

    /** Prefix of a file path for all test files */
    private String pathStart;

    /** Class loader for resource gathering */
    private static ClassLoader classLoader;

    /** Instance of ContractExtractorApi */
    private ContractExtractorApi contractExtractorApi;


    /**
     * Prepare method which sets up some variables
     */
    private void setUp(){

        ApiFactory apiFactory = new ApiFactory();
        contractExtractorApi = apiFactory.getContractExtractorApi();
        
        pathStart = "testFiles/extractor/JSR305/";
        classLoader = getClass().getClassLoader();
    }


    @Test
    void testJSR305Java(){
        testJSR305(FileType.JAVA);
    }

    @Test
    void testJSR305Class(){
        testJSR305(FileType.CLASS);
    }

    /**
     * File should contain one JSR305 contract that is pre-condition.
     */
    private void testJSR305(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        String finalWord = (fileType == FileType.JAVA) ? "" : "final ";
        basicCheck(contract, ConditionType.PRE, "@Nonnull " + finalWord + "String x", "Nonnull",
                "String x");
    }


    @Test
    void testJSR305PostConditionJava(){
        testJSR305PostCondition(FileType.JAVA);
    }

    @Test
    void testJSR305PostConditionClass(){
        testJSR305PostCondition(FileType.CLASS);
    }

    /**
     * File should contain one JSR305 contract that is post-condition.
     */
    private void testJSR305PostCondition(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305PostCondition." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, ConditionType.POST, "@Nonnull", "Nonnull",
                "");
    }


    @Test
    void testJSR305InvariantJava(){
        testJSR305Invariant(FileType.JAVA);
    }

    @Test
    void testJSR305InvariantClass(){
        testJSR305Invariant(FileType.CLASS);
    }

    /**
     * File should contain one JSR305 contract that is class invariant.
     */
    private void testJSR305Invariant(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "testJSR305Invariant." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, ConditionType.INVARIANT, "@Nonnull", "Nonnull",
                "");
    }


    @Test
    void testJSR305DiffFunctionJava(){
        testJSR305DiffFunction(FileType.JAVA);
    }

    @Test
    void testJSR305DiffFunctionClass(){
        testJSR305DiffFunction(FileType.CLASS);
    }

    /**
     * File should contain one JSR305 contract with function CheckForNull.
     */
    private void testJSR305DiffFunction(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305DiffFunction." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        String finalWord = (fileType == FileType.JAVA) ? "" : "final ";
        basicCheck(contract, ConditionType.PRE, "@CheckForNull " + finalWord + "String x",
                "CheckForNull", "String x");
    }


    /**
     * File should contain invalid JSR305 function.
     */
    @Test
    void testJSR305BadFunction(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305BadFunction.java");
        assertEquals(0, javaFile.getContracts().size());
    }


    @Test
    void testJSR305ManyFunctionsJava(){
        testJSR305ManyFunctions(FileType.JAVA);
    }

    @Test
    void testJSR305ManyFunctionsClass(){
        testJSR305ManyFunctions(FileType.CLASS);
    }


    /**
     * File should contain 6 different JSR305 contract with various function.
     */
    private void testJSR305ManyFunctions(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJSR305ManyFunctions." + getExtension(fileType));
        assertEquals(6, javaFile.getContracts().size());
    }


    @Test
    void testJSR3053ContractsJava(){

        testJSR305Get3Contracts("TestJSR3053Contracts", FileType.JAVA);
    }

    @Test
    void testJSR3053ContractsClass(){

        testJSR305Get3Contracts("TestJSR3053Contracts", FileType.CLASS);
    }


    @Test
    void testJSR3052MethodsJava(){

        testJSR305Get3Contracts("TestJSR3052Methods", FileType.JAVA);
    }

    @Test
    void testJSR3052MethodsClass(){

        testJSR305Get3Contracts("TestJSR3052Methods", FileType.CLASS);
    }

    @Test
    void testJSR3052ClassesJava(){

        testJSR305Get3Contracts("TestJSR3052Classes", FileType.JAVA);
    }

    @Test
    void testJSR3052ClassesClass(){

        testJSR305Get3Contracts("TestJSR3052Classes", FileType.CLASS);
    }

    /**
     * File should contain 3 contracts distributed between methods and classes. Behavior is defined by input file
     * and file type.
     */
    private void testJSR305Get3Contracts(String filename, FileType fileType){

        setUp();

        boolean multipleClasses = false;
        int expectedContracts = 3;
        if("TestJSR3052Classes".compareTo(filename) == 0 && fileType == FileType.CLASS){
            multipleClasses = true;
            expectedContracts = 1;
        }

        JavaFile javaFile = getTestJavaFile(pathStart + filename + "." + getExtension(fileType));
        assertEquals(expectedContracts, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, ConditionType.INVARIANT, "@CheckReturnValue", "CheckReturnValue",
                "");

        if(!multipleClasses) {
            contract = javaFile.getContracts().get(1);
            basicCheck(contract, ConditionType.POST, "@Untainted", "Untainted",
                    "");

            contract = javaFile.getContracts().get(2);
            String finalWord = (fileType == FileType.JAVA) ? "" : "final ";
            basicCheck(contract, ConditionType.PRE, "@Nonnull " + finalWord + "String x", "Nonnull",
                    "String x");
        }
    }


    /**
     * This methods provides basic assertions done in every test
     *
     * @param contract              Actual contract
     * @param conditionType         Expected condition type
     * @param completeExpression    Expected complete expression
     * @param function              Expected contract function
     * @param expression            Expected contract expression
     */
    private void basicCheck(Contract contract, ConditionType conditionType, String completeExpression, String function, String expression){

        assertEquals(ContractType.JSR305, contract.getContractType());
        assertEquals(conditionType, contract.getConditionType());
        assertEquals(completeExpression, contract.getCompleteExpression());
        assertEquals(function, contract.getFunction());
        assertEquals(expression, contract.getExpression());
    }


    /**
     * Method to get JavaFile on given path
     *
     * @param path  Path with JavaFile
     * @return      Retrieved JavaFile
     */
    private JavaFile getTestJavaFile(String path) {
        File fileJavaFile = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        return contractExtractorApi.retrieveContracts(fileJavaFile, false);
    }


    /**
     * Get extension base on given file type.
     *
     * @param fileType  FileType (CLASS or JAVA)
     * @return          File extension for given type
     */
    private String getExtension(FileType fileType){
        return fileType.toString().toLowerCase();
    }
}