package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.model.Contract;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This test class tests comparison of contracts.
 */
class ContractComparatorTest {

    /** Prefix of a file path for all test files */
    private String pathStart;

    /** Class loader for resource gathering */
    private static ClassLoader classLoader;

    /** Basic contract to which others are compared to */
    private static Contract contractX;


    /**
     * Prepare method which sets up some variables
     */
    private void setUp(){

        pathStart = "testFiles/comparator/";
        classLoader = getClass().getClassLoader();
        contractX = getTestContract(pathStart + "testContractX.java");
    }


    /**
     * Compares two contracts which should be equal.
     */
    @Test
    void testCompareEqualContracts() {

        setUp();

        Contract contractY = getTestContract(pathStart + "testContractX.java");
        contractX.compareTo(contractY);
        assertEquals(ContractComparison.EQUAL, contractX.compareTo(contractY));
    }

    /**
     * Compares two contracts which should be different.
     */
    @Test
    void testCompareDifferentContracts() {

        setUp();

        Contract contractY = getTestContract(pathStart + "testContractYDifferentContract.java");
        contractX.compareTo(contractY);
        assertEquals(ContractComparison.DIFFERENT, contractX.compareTo(contractY));
    }

    /**
     * Compares two contracts which should have different message.
     */
    @Test
    void testCompareChangeContractMessage() {

        setUp();

        Contract contractY = getTestContract(pathStart + "testContractYChangeContractMessage.java");
        contractX.compareTo(contractY);

        assertEquals(ContractComparison.MINOR_CHANGE, contractX.compareTo(contractY));
    }


    /**
     * Method for easier contract retrieve.
     *
     * @param path  Path with input JavaFile
     * @return      First found contract
     */
    private Contract getTestContract(String path) {
        File file = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        JavaFile javaFile = ContractExtractorApi.retrieveContracts(file, false);

        return javaFile.getContracts().get(0);
    }
}