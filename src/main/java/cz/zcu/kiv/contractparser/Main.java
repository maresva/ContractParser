package cz.zcu.kiv.contractparser;

import cz.zcu.kiv.contractparser.model.JavaFile;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*
        List<JavaFile> list = ContractExtractorApi.retrieveContractsFromFolder(new File("D:\\My\\_ZCU\\dp\\Test Data (anot-20170926T)"), false);

        for(JavaFile javaFile : list) {
            if(javaFile.getJavaFileStatistics().getTotalNumberOfContracts() >= 6 && javaFile.getJavaFileStatistics().getTotalNumberOfContracts() <= 10) {
                System.out.println(javaFile.getFullPath() + " : " + javaFile.getJavaFileStatistics().getNumberOfContracts());
            }
        } */

        JavaFile javaFile = ContractExtractorApi.retrieveContracts(new File("D:/test/guavaProblem.java"), false);
        System.out.println(javaFile);
    }
}
