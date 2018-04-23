package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This test class contains set of tests used to check correctness of Guava parser.
 */
class ContractExtractorGuavaTest {

    /** Prefix of a file path for all test files */
    private String pathStart;

    /** Class loader for resource gathering */
    private static ClassLoader classLoader;


    /**
     * Prepare method which sets up some variables
     */
    private void setUp(){

        pathStart = "testFiles/extractor/Guava/";
        classLoader = getClass().getClassLoader();
    }


    @Test
    void testGuavaJava() {
        testGuava(FileType.JAVA);
    }

    @Test
    void testGuavaClass() {
        testGuava(FileType.CLASS);
    }


    /**
     * File should contain one basic Guava contract.
     */
    private void testGuava(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuava." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        String expression, completeExpression;

        if(fileType == FileType.JAVA) {
            expression = "x";
            completeExpression = "Preconditions.checkNotNull(" + expression + ");";

        }
        else{
            expression = "(Object) x";
            completeExpression = "Preconditions.checkNotNull(" + expression + ");";
        }

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, completeExpression, "checkNotNull", expression, 0);
    }


    @Test
    void testGuava2argsJava() {
        testGuava2args(FileType.JAVA);
    }

    @Test
    void testGuava2argsClass() {
        testGuava2args(FileType.CLASS);
    }

    /**
     * File should contain one Guava contract with two arguments.
     */
    private void testGuava2args(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuava2args." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);

        String expression, completeExpression, message;

        if(fileType == FileType.JAVA) {
            expression = "x";
            message = "\"x must not be null.\"";
            completeExpression = "Preconditions.checkNotNull(" + expression + ", " + message + ");";

        }
        else{
            expression = "(Object) x";
            message = "(Object) \"x must not be null.\"";
            completeExpression = "Preconditions.checkNotNull(" + expression + ", " + message + ");";
        }

        basicCheck(contract, completeExpression, "checkNotNull", expression, 1);

        assertEquals(message, contract.getArguments().get(0));
    }


    @Test
    void testGuavaDiffFunctionJava(){
        testGuavaDiffFunction(FileType.JAVA);
    }

    @Test
    void testGuavaDiffFunctionClass(){
        testGuavaDiffFunction(FileType.CLASS);
    }


    /**
     * File should contain one Guava contract with different function.
     */
    private void testGuavaDiffFunction(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaDiffFunction." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, "Preconditions.checkArgument(x.length() > 0);", "checkArgument",
                "x.length() > 0", 0);
    }


    @Test
    void testGuavaBadFunctionJava(){
        testGuavaBadFunction(FileType.JAVA);
    }

    @Test
    void testGuavaBadFunctionClass(){
        testGuavaBadFunction(FileType.CLASS);
    }


    /**
     * File should contain one invalid Guava contract with invalid function.
     */
    private void testGuavaBadFunction(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaBadFunction." + getExtension(fileType));
        assertEquals(0, javaFile.getContracts().size());
    }


    @Test
    void testGuavaNoParamsJava(){
        testGuavaNoParams(FileType.JAVA);
    }

    @Test
    void testGuavaNoParamsClass(){
        testGuavaNoParams(FileType.CLASS);
    }


    /**
     * File should contain one invalid Guava contract with no parameters.
     */
    private void testGuavaNoParams(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaNoParams." + getExtension(fileType));
        assertEquals(0, javaFile.getContracts().size());
    }


    @Test
    void testGuavaFullScopeJava(){
        testGuavaFullScope(FileType.JAVA);
    }

    @Test
    void testGuavaFullScopeClass(){
        testGuavaFullScope(FileType.CLASS);
    }


    /**
     * File should contain one basic Guava contract written with full method scope.
     */
    private void testGuavaFullScope(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaFullScope." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);

        String expression, completeExpression;

        if(fileType == FileType.JAVA) {
            expression = "x";
            completeExpression = "com.google.common.base.Preconditions.checkNotNull(" + expression + ");";

        }
        else{
            expression = "(Object) x";
            completeExpression = "Preconditions.checkNotNull(" + expression + ");";
        }

        basicCheck(contract, completeExpression, "checkNotNull", expression, 0);
    }


    @Test
    void testGuavaNoScopeJava(){
        testGuavaNoScope(FileType.JAVA);
    }

    @Test
    void testGuavaNoScopeClass(){
        testGuavaNoScope(FileType.CLASS);
    }

    /**
     * File should contain one basic Guava contract written without a method scope.
     */
    private void testGuavaNoScope(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaNoScope." + getExtension(fileType));
        assertEquals(1, javaFile.getContracts().size());

        Contract contract = javaFile.getContracts().get(0);

        String expression, completeExpression;

        if(fileType == FileType.JAVA) {
            expression = "x";
            completeExpression = "checkNotNull(" + expression + ");";

        }
        else{
            expression = "(Object) x";
            completeExpression = "Preconditions.checkNotNull(" + expression + ");";
        }

        basicCheck(contract, completeExpression, "checkNotNull", expression, 0);
    }


    @Test
    void testGuavaBadScopeJava(){
        testGuavaBadScope(FileType.JAVA);
    }

    @Test
    void testGuavaBadScopeClass(){
        testGuavaBadScope(FileType.CLASS);
    }

    /**
     * File should contain one invalid Guava contract written with wrong method scope.
     */
    private void testGuavaBadScope(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "testGuavaBadScope." + getExtension(fileType));
        assertEquals(0, javaFile.getContracts().size());
    }


    @Test
    void testGuavaManyFunctionsJava(){
        testGuavaManyFunctions(FileType.JAVA);
    }

    @Test
    void testGuavaManyFunctionsClass(){
        testGuavaManyFunctions(FileType.CLASS);
    }


    /**
     * File should contain 7 Guava contracts using various functions.
     */
    private void testGuavaManyFunctions(FileType fileType){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestGuavaManyFunctions." + getExtension(fileType));
        assertEquals(7, javaFile.getContracts().size());
    }


    @Test
    void testGuava2ContractsJava(){

        testGuavaGet2Contracts("TestGuava2Contracts", FileType.JAVA, false);
    }

    @Test
    void testGuava2ContractsClass(){

        testGuavaGet2Contracts("TestGuava2Contracts", FileType.CLASS, false);
    }


    @Test
    void testGuava2MethodsJava(){

        testGuavaGet2Contracts("TestGuava2Methods", FileType.JAVA, false);
    }

    @Test
    void testGuava2MethodsClass(){

        testGuavaGet2Contracts("TestGuava2Methods", FileType.CLASS, false);
    }

    @Test
    void testGuava2ClassesJava(){

        testGuavaGet2Contracts("TestGuava2Classes", FileType.JAVA, false);
    }

    @Test
    void testGuava2ClassesClass(){

        testGuavaGet2Contracts("TestGuava2Classes", FileType.CLASS, true);
    }


    /**
     * File should contain two Guava contracts spread between methods and classes. Check is different depending
     * on given file type.
     */
    private void testGuavaGet2Contracts(String filename, FileType fileType, boolean manyClasses){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + filename + "." + getExtension(fileType));

        String expression, completeExpression;
        int size = 2;

        if(fileType == FileType.JAVA) {
            assertEquals(size, javaFile.getContracts().size());
            expression = "x";
            completeExpression = "Preconditions.checkNotNull(" + expression + ");";
        }
        else{
            if(manyClasses){
                size = 1;
            }

            assertEquals(size, javaFile.getContracts().size());
            expression = "(Object) x";
            completeExpression = "Preconditions.checkNotNull(" + expression + ");";
        }
        
        Contract contract = javaFile.getContracts().get(0);
        basicCheck(contract, completeExpression, "checkNotNull", expression, 0);

        if(!manyClasses) {
            contract = javaFile.getContracts().get(1);

            if (fileType == FileType.JAVA) {
                expression = "x.length() > 0";
                completeExpression = "Preconditions.checkArgument(" + expression + ");";

            } else {
                expression = "x.length() > 0";
                completeExpression = "Preconditions.checkArgument(" + expression + ");";
            }

            basicCheck(contract, completeExpression, "checkArgument", expression, 0);
        }
    }


    /**
     * This methods provides basic assertions done in every test
     *
     * @param contract              Actual contract
     * @param completeExpression    Expected complete expression
     * @param function              Expected contract function
     * @param expression            Expected contract expression
     * @param argsLength            Expected number of arguments
     */
    private void basicCheck(Contract contract, String completeExpression, String function, String expression, int argsLength){

        assertEquals(ContractType.GUAVA, contract.getContractType());
        assertEquals(ConditionType.PRE, contract.getConditionType());
        assertEquals(completeExpression, contract.getCompleteExpression());
        assertEquals(function, contract.getFunction());
        assertEquals(expression, contract.getExpression());
        assertEquals(argsLength, contract.getArguments().size());
    }


    /**
     * Method to get JavaFile on given path
     *
     * @param path  Path with JavaFile
     * @return      Retrieved JavaFile
     */
    private JavaFile getTestJavaFile(String path) {
        File fileJavaFile = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        return ContractExtractorApi.retrieveContracts(fileJavaFile, false);
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