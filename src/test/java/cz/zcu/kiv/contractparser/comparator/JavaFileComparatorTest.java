package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Objects;

class JavaFileComparatorTest {

    private JavaFile javaFileX;
    private ClassLoader classLoader;

    private void setUp(){

        classLoader = getClass().getClassLoader();
        File fileJavaFileX = new File(Objects.requireNonNull(classLoader.getResource("testFiles/testJavaFileX.java")).getFile());

        javaFileX =  ContractExtractorApi.retrieveContracts(fileJavaFileX, false);
    }


    @Test
    void compareEqual(){

        setUp();

        JavaFile javaFileY = getTestJavaFile("testFiles/testJavaFileYEqual.java");
        JavaFileCompareReport javaFileCompareReport = javaFileX.compareJavaFileTo(javaFileY, true, false);
        //assertEquals(true, javaFileCompareReport.getJavaFileComparison());
    }


    @Test
    void compareDifferentContracts() {

        setUp();

        JavaFile javaFileY = getTestJavaFile("testFiles/testJavaFileYDifferentContracts.java");
        JavaFileCompareReport javaFileCompareReport = javaFileX.compareJavaFileTo(javaFileY, true, false);
        //assertEquals(JavaFileComparison.CONTRACT_DIFFERENT, javaFileCompareReport.getJavaFileComparison());
    }


    @Test
    void compareDifferentApi() {

        setUp();

        JavaFile javaFileY = getTestJavaFile("testFiles/testJavaFileYDifferentApiSignature.java");
        JavaFileCompareReport javaFileCompareReport = javaFileX.compareJavaFileTo(javaFileY, true, false);
        //assertEquals(JavaFileComparison.API_DIFFERENT, javaFileCompareReport.getJavaFileComparison());
    }


    private JavaFile getTestJavaFile(String path) {
        File fileJavaFileY = new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
        return ContractExtractorApi.retrieveContracts(fileJavaFileY, false);
    }

}