package cz.zcu.kiv.contractparser;

import cz.zcu.kiv.contractparser.comparator.JavaFolderComparator;
import cz.zcu.kiv.contractparser.comparator.JavaFolderCompareReport;
import cz.zcu.kiv.contractparser.io.IOServices;

import java.io.File;

public class ContractComparatorApi {


    public static JavaFolderCompareReport compareJavaFolders(String thisFolderName, String otherFolderName, boolean reportEqual,
                                                             boolean reportNonContractChanges) {

        JavaFolderComparator javaFolderComparator = new JavaFolderComparator();
        return javaFolderComparator.compareJavaFolders(thisFolderName, otherFolderName, reportEqual,
                reportNonContractChanges);
    }

    public static void exportJavaFolderCompareReportToJson(JavaFolderCompareReport javaFolderCompareReport, File outputFolder){

        IOServices.exportJavaFolderCompareRoportsToJson(javaFolderCompareReport, outputFolder);
    }
}
