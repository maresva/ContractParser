package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test class contains set of tests used to check correctness of Java parser.
 */
class JavaParserTest {

    /** Prefix of a file path for all test files */
    private String pathStart;

    /** Class loader for resource gathering */
    private static ClassLoader classLoader;


    /**
     * Prepare method which sets up some variables
     */
    private void setUp(){

        pathStart = "testFiles/parser/";
        classLoader = getClass().getClassLoader();
    }


    @Test
    void testJavaParserError1() {

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJavaParserError1.java");
        assertEquals(null, javaFile);
    }

    @Test
    void testJavaParserError2() {

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJavaParserError2.java");
        assertEquals(null, javaFile);
    }

    @Test
    void testJavaParserError3() {

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJavaParserError3.java");
        assertEquals(null, javaFile);
    }

    @Test
    void testJavaParserError4() {

        setUp();

        JavaFile javaFile = getTestJavaFile(pathStart + "TestJavaParserError4.java");
        assertEquals(null, javaFile);
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

        for(ContractType contractType : ContractType.values()){
            contractTypes.put(contractType, false);
        }

        return ContractExtractorApi.retrieveContracts(fileJavaFile, false, contractTypes);
    }
}
