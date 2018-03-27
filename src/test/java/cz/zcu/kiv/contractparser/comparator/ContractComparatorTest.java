package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.ContractManagerApi;
import cz.zcu.kiv.contractparser.model.Contract;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ContractComparatorTest {

    private Contract contractX;
    private ClassLoader classLoader;

    private void setUp(){

        classLoader = getClass().getClassLoader();
        contractX = getTestContract("testFiles/testContractX.java");
    }


    @Test
    void testCompareEqualContracts() {

        setUp();

        Contract contractY = getTestContract("testFiles/testContractX.java");
        contractX.compareTo(contractY);
        assertEquals(ContractComparison.EQUAL, contractX.compareTo(contractY));
    }

    @Test
    void testCompareDifferentContracts() {

        setUp();

        Contract contractY = getTestContract("testFiles/testContractYDifferentContract.java");
        contractX.compareTo(contractY);
        assertEquals(ContractComparison.DIFFERENT, contractX.compareTo(contractY));
    }

    @Test
    void testCompareChangeContractMessage() {

        setUp();

        Contract contractY = getTestContract("testFiles/testContractYChangeContractMessage.java");
        contractX.compareTo(contractY);

        System.out.println(contractX);
        System.out.println(contractY);

        assertEquals(ContractComparison.MESSAGE_CHANGE, contractX.compareTo(contractY));
    }




    private Contract getTestContract(String path) {
        File file = new File(classLoader.getResource(path).getFile());
        JavaFile javaFile = ContractManagerApi.retrieveContracts(file, null);

        return javaFile.getContracts().get(0);
    }
}