package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.model.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test class contains some more complex tests for contract extractor.
 */
class ContractExtractorApiTest {

    /** Prefix of a file path for all test files */
    private String pathStart;

    /** Class loader for resource gathering */
    private static ClassLoader classLoader;


    /**
     * Prepare method which sets up some variables
     */
    private void setUp(){

        pathStart = "testFiles/extractor/";
        classLoader = getClass().getClassLoader();
    }


    @Test
    void testComplexGuava19UnsignedIntegerJava() {
        testComplexGuava19UnsignedInteger(FileType.JAVA);
    }

    @Test
    void testComplexGuava19UnsignedIntegerClass() {
        testComplexGuava19UnsignedInteger(FileType.CLASS);
    }


    /**
     * File should contain multiple JSR305 and Guava contracts. Each of them is tested.
     */
    private void testComplexGuava19UnsignedInteger(FileType fileType) {

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestComplexGuava19UnsignedInteger." + fileType.toString().toLowerCase());
        assertEquals(9, javaFile.getJavaFileStatistics().getNumberOfContracts().get(ContractType.GUAVA).intValue());
        assertEquals(2, javaFile.getJavaFileStatistics().getNumberOfContracts().get(ContractType.JSR305).intValue());

        List<String> arguments;
        Contract contract;
        String expression, completeExpression, value;


        // Contract #0
        contract = javaFile.getContracts().get(0);
        basicCheck(contract, ContractType.JSR305, ConditionType.INVARIANT, "@CheckReturnValue",
                "CheckReturnValue", "", new ArrayList<>());


        // Contract #1
        contract = javaFile.getContracts().get(1);
        arguments = new ArrayList<>();
        arguments.add("\"value (%s) is outside the range for an unsigned integer value\"");

        if(fileType == FileType.JAVA) {
            expression = "(value & INT_MASK) == value";
            value = "value";
            completeExpression = "checkArgument(" + expression + ", \"value (%s) is outside the range for an " +
                    "unsigned integer value\", " + value + ");";

        }
        else{
            expression = "(value & 0xFFFFFFFFL) == value";
            value = "new Object[] { value }";
            completeExpression = "Preconditions.checkArgument(" + expression + ", \"value (%s) is outside the " +
                    "range for an unsigned integer value\", " + value + ");";
        }

        arguments.add(value);
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression,"checkArgument",
                expression, arguments);


        // Contract #2
        contract = javaFile.getContracts().get(2);

        if(fileType == FileType.JAVA) {
            expression = "value";
            completeExpression = "checkNotNull(" + expression + ");";

        }
        else{
            expression = "(Object) value";
            completeExpression = "Preconditions.checkNotNull(" + expression + ");";
        }

        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression,"checkNotNull",
                expression, new ArrayList<>());


        // Contract #3
        contract = javaFile.getContracts().get(3);
        arguments = new ArrayList<>();
        arguments.add("\"value (%s) is outside the range for an unsigned integer value\"");

        if(fileType == FileType.JAVA) {
            expression = "value.signum() >= 0 && value.bitLength() <= Integer.SIZE";
            value = "value";
            completeExpression = "checkArgument(" + expression + ", \"value (%s) is outside the range for an " +
                    "unsigned integer value\", " + value + ");";

        }
        else{
            expression = "value.signum() >= 0 && value.bitLength() <= 32";
            value = "new Object[] { value }";
            completeExpression = "Preconditions.checkArgument(" + expression + ", \"value (%s) is outside the range " +
                    "for an unsigned integer value\", " + value + ");";
        }

        arguments.add(value);
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression, "checkArgument",
                expression, arguments);


        // Contract #4
        contract = javaFile.getContracts().get(4);

        if(fileType == FileType.JAVA) {
            expression = "val";
            completeExpression = "return fromIntBits(this.value + checkNotNull(" + expression + ").value);";

        }
        else{
            expression = "(Object) val";
            completeExpression = "return fromIntBits(this.value + ((UnsignedInteger) Preconditions.checkNotNull("
                    + expression + ")).value);";
        }

        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression,"checkNotNull",
                expression, new ArrayList<>());


        // Contract #5
        contract = javaFile.getContracts().get(5);

        if(fileType == FileType.JAVA) {
            expression = "val";
            completeExpression = "return fromIntBits(value - checkNotNull(" + expression + ").value);";

        }
        else{
            expression = "(Object) val";
            completeExpression = "return fromIntBits(this.value - ((UnsignedInteger) Preconditions.checkNotNull("
                    + expression + ")).value);";
        }

        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression,"checkNotNull",
                expression, new ArrayList<>());


        // Contract #6
        contract = javaFile.getContracts().get(6);

        if(fileType == FileType.JAVA) {
            expression = "val";
            completeExpression = "return fromIntBits(value * checkNotNull(" + expression + ").value);";

        }
        else{
            expression = "(Object) val";
            completeExpression = "return fromIntBits(this.value * ((UnsignedInteger) Preconditions.checkNotNull("
                    + expression + ")).value);";
        }

        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression,"checkNotNull",
                expression, new ArrayList<>());


        // Contract #7
        contract = javaFile.getContracts().get(7);

        if(fileType == FileType.JAVA) {
            expression = "val";
            completeExpression = "return fromIntBits(UnsignedInts.divide(value, checkNotNull(" + expression + ")" +
                    ".value));";

        }
        else{
            expression = "(Object) val";
            completeExpression = "return fromIntBits(UnsignedInts.divide(this.value, ((UnsignedInteger) " +
                    "Preconditions.checkNotNull(" + expression + ")).value));";
        }

        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression, "checkNotNull",
                expression, new ArrayList<>());


        // Contract #8
        contract = javaFile.getContracts().get(8);

        if(fileType == FileType.JAVA) {
            expression = "val";
            completeExpression = "return fromIntBits(UnsignedInts.remainder(value, checkNotNull(" + expression + ")" +
                    ".value));";

        }
        else{
            expression = "(Object) val";
            completeExpression = "return fromIntBits(UnsignedInts.remainder(this.value, ((UnsignedInteger) " +
                    "Preconditions.checkNotNull(" + expression + ")).value));";
        }

        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression, "checkNotNull",
                expression, new ArrayList<>());


        // Contract #9
        contract = javaFile.getContracts().get(9);

        if(fileType == FileType.JAVA) {
            expression = "other";
            completeExpression = "checkNotNull(" + expression + ");";

        }
        else{
            expression = "(Object) other";
            completeExpression = "Preconditions.checkNotNull(" + expression + ");";
        }

        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression, "checkNotNull",
                expression, new ArrayList<>());


        // Contract #10

        if(fileType == FileType.JAVA) {
            completeExpression = "@Nullable Object obj";

        }
        else{
            completeExpression = "@Nullable final Object obj";
        }

        contract = javaFile.getContracts().get(10);
        basicCheck(contract, ContractType.JSR305, ConditionType.PRE, completeExpression,  "Nullable",
                "Object obj", new ArrayList<>());
    }


    @Test
    void testComplexGuava19HashCodeJava(){
        testComplexGuava19HashCode(FileType.JAVA);
    }

    @Test
    void testComplexGuava19HashCodeClass(){
        testComplexGuava19HashCode(FileType.CLASS);
    }

    /**
     * File should contain multiple JSR305 and Guava contracts. Each of them is tested.
     */
    private void testComplexGuava19HashCode(FileType fileType){

        setUp();

        int expectedContractsGuava = (fileType == FileType.JAVA) ? 6 : 4;

        JavaFile javaFile = getTestJavaFile(pathStart + "TestComplexGuava19HashCode." + getExtension(fileType));
        assertEquals(expectedContractsGuava, javaFile.getJavaFileStatistics().getNumberOfContracts().get(ContractType.GUAVA).intValue());
        assertEquals(10, javaFile.getJavaFileStatistics().getNumberOfContracts().get(ContractType.JSR305).intValue());

        List<String> arguments;
        Contract contract;

        for(int i = 0 ; i <= 4 ; i++) {

            testCheckReturnValue(i, javaFile);
        }
        
        contract = javaFile.getContracts().get(5);
        arguments = new ArrayList<>();
        arguments.add("offset + maxLength");
        arguments.add("dest.length");
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "Preconditions.checkPositionIndexes(offset, offset + maxLength, dest.length);",
                "checkPositionIndexes", "offset", arguments);

        testCheckReturnValue(6, javaFile);
        testCheckReturnValue(7, javaFile);

        contract = javaFile.getContracts().get(8);

        String completeExpression, argument;

        if(fileType == FileType.JAVA) {
            completeExpression = "checkArgument(bytes.length >= 1, \"A HashCode must contain at least 1 byte.\");";
            argument = "\"A HashCode must contain at least 1 byte.\"";
        }
        else{
            completeExpression = "Preconditions.checkArgument(bytes.length >= 1, (Object) \"A HashCode must contain at least 1 byte.\");";
            argument = "(Object) \"A HashCode must contain at least 1 byte.\"";
        }

        arguments = new ArrayList<>();
        arguments.add(argument);
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression,  "checkArgument",
                "bytes.length >= 1", arguments);


        testCheckReturnValue(9, javaFile);


        contract = javaFile.getContracts().get(10);
        
        if(expectedContractsGuava == 6) {
            arguments = new ArrayList<>();
            arguments.add("\"HashCode#asInt() requires >= 4 bytes (it only has %s bytes).\"");
            arguments.add("bytes.length");
            basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, "checkState(bytes.length >= 4, \"HashCode#asInt() requires >= 4 bytes (it only has %s bytes).\", bytes.length);",
                    "checkState", "bytes.length >= 4", arguments);

            contract = javaFile.getContracts().get(11);

            arguments = new ArrayList<>();
            arguments.add("\"HashCode#asLong() requires >= 8 bytes (it only has %s bytes).\"");
            arguments.add("bytes.length");
            basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                    "checkState(bytes.length >= 8, \"HashCode#asLong() requires >= 8 bytes (it only has %s bytes).\", bytes.length);",
                    "checkState", "bytes.length >= 8", arguments);

            contract = javaFile.getContracts().get(12);
        }

        if(fileType == FileType.JAVA) {
            completeExpression = "checkArgument(string.length() >= 2, \"input string (%s) must have at least 2 characters\", string);";
            argument = "string";
        }
        else{
            completeExpression = "Preconditions.checkArgument(string.length() >= 2, \"input string (%s) must have at least 2 characters\", new Object[] { string });";
            argument = "new Object[] { string }";
        }


        arguments = new ArrayList<>();
        arguments.add("\"input string (%s) must have at least 2 characters\"");
        arguments.add(argument);
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE, completeExpression,
                "checkArgument", "string.length() >= 2", arguments);
    }


    private void testCheckReturnValue(int index, JavaFile javaFile) {
        
        Contract contract = javaFile.getContracts().get(index);
        basicCheck(contract, ContractType.JSR305, ConditionType.POST, "@CheckReturnValue",
                "CheckReturnValue", "", new ArrayList<>());
    }

    /**
     * This methods provides basic assertions done in every test
     *
     * @param contract              Actual contract
     * @param contractType          Expected type of contract
     * @param conditionType         Expected condition type
     * @param completeExpression    Expected complete expression
     * @param function              Expected contract function
     * @param expression            Expected contract expression
     * @param arguments             Expected list of arguments
     */
    private void basicCheck(Contract contract, ContractType contractType, ConditionType conditionType,
                            String completeExpression, String function, String expression, List<String> arguments){

        assertEquals(contractType, contract.getContractType());
        assertEquals(conditionType, contract.getConditionType());
        assertEquals(completeExpression, contract.getCompleteExpression());
        assertEquals(function, contract.getFunction());
        assertEquals(expression, contract.getExpression());
        assertEquals(arguments.size(), contract.getArguments().size());

        for(int i = 0 ; i < contract.getArguments().size(); i++){
            assertEquals(arguments.get(i), contract.getArguments().get(i));
        }
    }


    /**
     * Method to get JavaFile on given path
     *
     * @param path  Path with JavaFile
     * @return      Retrieved JavaFile
     */
    private JavaFile getTestJavaFile(String path) {
        File fileJavaFile = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());

        HashMap<ContractType, Boolean> contractTypes = new HashMap<>();



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
