package cz.zcu.kiv.contractparser;

import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static cz.zcu.kiv.contractparser.ContractExtractorApi.retrieveContracts;
import static org.junit.jupiter.api.Assertions.*;

class ContractManagerApiTest {

    @Test
    void testNumberOfContracts() {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("testFiles/testCase.java").getFile());

        HashMap<ContractType, Boolean> contractTypes = new HashMap<>();
        contractTypes.put(ContractType.GUAVA, true);
        contractTypes.put(ContractType.JSR305, true);

        JavaFile javaFile = retrieveContracts(file, false, contractTypes);

        assertEquals(4, javaFile.getJavaFileStatistics().getTotalNumberOfContracts());
    }
}