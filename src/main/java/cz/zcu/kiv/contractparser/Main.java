package cz.zcu.kiv.contractparser;

import cz.zcu.kiv.contractparser.model.JavaFile;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        File fileA = new File("../../../../../test/input");
        File fileB = new File("../../OUTTEST");

        ContractExtractorApi.retrieveContractsFromFolderExportToJson(fileA, fileB, true, true);

        List<JavaFile> javaFileList = ContractExtractorApi.retrieveContractsFromFolder(fileA, true);
        System.out.println(javaFileList);
    }
}
