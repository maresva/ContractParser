package cz.zcu.kiv.contractparser;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        File fileA = new File("D:/test/input");
        File fileB = new File("../../OUTTEST");

        ContractExtractorApi.retrieveContractsFromFolderExportToJson(fileA, fileB, true, true);
    }
}
