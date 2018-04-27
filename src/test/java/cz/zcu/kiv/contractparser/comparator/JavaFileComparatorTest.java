package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.api.ApiFactory;
import cz.zcu.kiv.contractparser.api.ContractExtractorApi;
import cz.zcu.kiv.contractparser.comparator.comparatormodel.JavaFileCompareReport;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This test class tests comparison of java files.
 */
class JavaFileComparatorTest {

    /** Prefix of a file path for all test files */
    private String pathStart;

    /** Class loader for resource gathering */
    private static ClassLoader classLoader;

    /** Basic JavaFile to which others are compared to */
    private JavaFile javaFileX;

    /** Instance of ContractExtractorApi */
    private ContractExtractorApi contractExtractorApi;


    /**
     * Prepare method which sets up some variables
     */
    private void setUp(){

        ApiFactory apiFactory = new ApiFactory();
        contractExtractorApi = apiFactory.getContractExtractorApi();

        pathStart = "testFiles/comparator/";
        classLoader = getClass().getClassLoader();
        File fileJavaFileX = new File(Objects.requireNonNull(classLoader.getResource(pathStart + "testJavaFileX.java")).getFile());

        javaFileX =  contractExtractorApi.retrieveContracts(fileJavaFileX, false);
    }


    /**
     * Compares two JavaFiles which should be equal.
     */
    @Test
    void compareEqual(){

        setUp();

        JavaFile javaFileY = getTestJavaFile(pathStart + "testJavaFileYEqual.java");

        JavaFileCompareReport javaFileCompareReport = javaFileX.compareJavaFileTo(javaFileY, true, false);
        assertEquals(true, javaFileCompareReport.isContractEqual());
        assertEquals(true, javaFileCompareReport.isApiEqual());
    }


    /**
     * Compares two JavaFiles which should have equal API but different contracts.
     */
    @Test
    void compareDifferentContracts() {

        setUp();

        JavaFile javaFileY = getTestJavaFile(pathStart + "testJavaFileYDifferentContracts.java");
        JavaFileCompareReport javaFileCompareReport = javaFileX.compareJavaFileTo(javaFileY, true, false);
        System.out.println(javaFileCompareReport);
        assertEquals(false, javaFileCompareReport.isContractEqual());
        assertEquals(true, javaFileCompareReport.isApiEqual());
    }


    /**
     * Compares two JavaFiles which should have different API but equal contracts.
     */
    @Test
    void compareDifferentApi() {

        setUp();

        JavaFile javaFileY = getTestJavaFile(pathStart + "testJavaFileYDifferentApiSignature.java");
        JavaFileCompareReport javaFileCompareReport = javaFileX.compareJavaFileTo(javaFileY, true, false);
        assertEquals(true, javaFileCompareReport.isContractEqual());
        assertEquals(false, javaFileCompareReport.isApiEqual());
    }


    /**
     * Method to get an JavaFile on given path.
     *
     * @param path  Path with JavaFile
     * @return      Retrieved JavaFile
     */
    private JavaFile getTestJavaFile(String path) {
        File fileJavaFileY = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        return contractExtractorApi.retrieveContracts(fileJavaFileY, false);
    }
}