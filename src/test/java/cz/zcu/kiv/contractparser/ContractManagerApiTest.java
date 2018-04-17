package cz.zcu.kiv.contractparser;

import cz.zcu.kiv.contractparser.model.ConditionType;
import cz.zcu.kiv.contractparser.model.Contract;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContractManagerApiTest {

    private String pathStart;
    private ClassLoader classLoader;

    private void setUp(){

        pathStart = "testFiles/extractor/";
        classLoader = getClass().getClassLoader();
    }


    @Test
    void testComplexGuava19Suppliers() {

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestComplexGuava19UnsignedInteger.java");
        assertEquals(4, javaFile.getJavaFileStatistics().getNumberOfContracts().get(ContractType.GUAVA).intValue());
        assertEquals(2, javaFile.getJavaFileStatistics().getNumberOfContracts().get(ContractType.JSR305).intValue());

        List<String> arguments;
        Contract contract;

        contract = javaFile.getContracts().get(0);
        basicCheck(contract, ContractType.JSR305, ConditionType.INVARIANT, "@CheckReturnValue",
                "CheckReturnValue", "", new ArrayList<>());

        contract = javaFile.getContracts().get(1);
        arguments = new ArrayList<>();
        arguments.add("\"value (%s) is outside the range for an unsigned integer value\"");
        arguments.add("value");
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "checkArgument((value & INT_MASK) == value, \"value (%s) is outside the range for an unsigned integer value\", value);",
                "checkArgument",
                "(value & INT_MASK) == value", arguments);

        contract = javaFile.getContracts().get(2);
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "checkNotNull(value);","checkNotNull",
                "value", new ArrayList<>());

        contract = javaFile.getContracts().get(3);
        arguments = new ArrayList<>();
        arguments.add("\"value (%s) is outside the range for an unsigned integer value\"");
        arguments.add("value");
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "checkArgument(value.signum() >= 0 && value.bitLength() <= Integer.SIZE, \"value (%s) is outside the range for an unsigned integer value\", value);",
                "checkArgument",
                "value.signum() >= 0 && value.bitLength() <= Integer.SIZE", arguments);
        /*
        contract = javaFile.getContracts().get(2);
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "Preconditions.checkNotNull(supplier);","checkNotNull",
                "supplier", new ArrayList<>());

        contract = javaFile.getContracts().get(3);
        basicCheck(contract, ContractType.JSR305, ConditionType.PRE, "@Nullable Object obj",
                "Nullable", "Object obj", new ArrayList<>());

        contract = javaFile.getContracts().get(4);
        basicCheck(contract, ContractType.JSR305, ConditionType.PRE, "@Nullable T instance",
                "Nullable", "T instance", new ArrayList<>());

        contract = javaFile.getContracts().get(5);
        basicCheck(contract, ContractType.JSR305, ConditionType.PRE, "@Nullable Object obj",
                "Nullable", "Object obj", new ArrayList<>());

        contract = javaFile.getContracts().get(6);
        System.out.println(contract);


        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "Preconditions.checkNotNull(delegate)","checkNotNull",
                "delegate", new ArrayList<>());            */


    }


    @Test
    void testComplexGuava19HashCode(){

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestComplexGuava19HashCode.java");
        assertEquals(6, javaFile.getJavaFileStatistics().getNumberOfContracts().get(ContractType.GUAVA).intValue());
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
        arguments = new ArrayList<>();
        arguments.add("\"A HashCode must contain at least 1 byte.\"");
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "checkArgument(bytes.length >= 1, \"A HashCode must contain at least 1 byte.\");",
                "checkArgument", "bytes.length >= 1", arguments);

        testCheckReturnValue(9, javaFile);

        contract = javaFile.getContracts().get(10);
        arguments = new ArrayList<>();
        arguments.add("\"HashCode#asInt() requires >= 4 bytes (it only has %s bytes).\"");
        arguments.add("bytes.length");
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "checkState(bytes.length >= 4, \"HashCode#asInt() requires >= 4 bytes (it only has %s bytes).\", bytes.length);",
                "checkState", "bytes.length >= 4", arguments);

        contract = javaFile.getContracts().get(11);
        arguments = new ArrayList<>();
        arguments.add("\"HashCode#asLong() requires >= 8 bytes (it only has %s bytes).\"");
        arguments.add("bytes.length");
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "checkState(bytes.length >= 8, \"HashCode#asLong() requires >= 8 bytes (it only has %s bytes).\", bytes.length);",
                "checkState", "bytes.length >= 8", arguments);


        contract = javaFile.getContracts().get(12);
        arguments = new ArrayList<>();
        arguments.add("\"input string (%s) must have at least 2 characters\"");
        arguments.add("string");
        basicCheck(contract, ContractType.GUAVA, ConditionType.PRE,
                "checkArgument(string.length() >= 2, \"input string (%s) must have at least 2 characters\", string);",
                "checkArgument", "string.length() >= 2", arguments);
    }


    private void testCheckReturnValue(int index, JavaFile javaFile) {
        
        Contract contract = javaFile.getContracts().get(index);
        basicCheck(contract, ContractType.JSR305, ConditionType.POST, "@CheckReturnValue",
                "CheckReturnValue", "", new ArrayList<>());
    }


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


    private JavaFile getTestJavaFile(String path) {
        File fileJavaFile = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        return ContractExtractorApi.retrieveContracts(fileJavaFile, false);
    }


    // TestComplexGuava19HashCode.java
}
