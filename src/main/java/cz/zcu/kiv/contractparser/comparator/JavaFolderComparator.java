package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.JavaFile;

import java.io.File;
import java.util.List;

/**
 * Provides means to compare two folders containing Java files. It compares individual files and determines what changes
 * there were made on API and contracts.
 *
 */
public class JavaFolderComparator {

    /**
     * This method compares two folders containing Java files. It compares individual files and determines what changes
     * there were made on API and contracts (based on input parameters). Output is a report object which contains
     * information such as what files were added/removed, individual file compare reports and summary statistics.
     *
     * @param thisFolderName            String - path of the first folder with Java files
     * @param otherFolderName           String - path of the second folder with Java files
     * @param reportEqual               boolean - whether should equal files be reported or not
     * @param reportNonContractChanges  boolean - whether should be reported changes that doesn't affect contracts
     * @return                          JavaFolderCompareReport - containing all relevant compare information
     */
    public JavaFolderCompareReport compareJavaFolders(String thisFolderName, String otherFolderName, boolean reportEqual,
                                      boolean reportNonContractChanges) {

        JavaFolderCompareReport javaFolderCompareReport = new JavaFolderCompareReport(thisFolderName, otherFolderName);

        int contractsAdded = 0;
        int contractsRemoved = 0;

        File thisFolder = new File(thisFolderName);
        File otherFolder = new File(otherFolderName);

        // check if folders exist and that they are folders
        if(!IOServices.checkFolder(thisFolder) || !IOServices.checkFolder(otherFolder)){
            return javaFolderCompareReport;
        }

        String thisFolderPath = thisFolder.getAbsolutePath();
        String otherFolderPath = otherFolder.getAbsolutePath();

        // retrieve contracts from both folders
        List<JavaFile> thisJavaFiles = ContractExtractorApi.retrieveContractsFromFolder(thisFolderName, false);
        List<JavaFile> otherJavaFiles = ContractExtractorApi.retrieveContractsFromFolder(otherFolderName, false);

        for(JavaFile thisJavaFile : thisJavaFiles) {

            // if non contract changes should not be reported and if there are not any contracts in file - skip it
            if(!reportNonContractChanges && thisJavaFile.getJavaFileStatistics().getTotalNumberOfContracts() == 0){
                continue;
            }

            // create path difference to find files in the other folder
            String thisJavaFilePath = thisJavaFile.getPath().substring(thisFolderPath.length() + 1);

            // try to find pair file int the other folder by comparing relative paths
            int otherJavaFileId = 0;
            boolean fileFound = false;
            while(otherJavaFileId < otherJavaFiles.size()){

                String otherJavaFilePath = otherJavaFiles.get(otherJavaFileId).getPath()
                        .substring(otherFolderPath.length() + 1);

                if(thisJavaFilePath.compareTo(otherJavaFilePath) == 0){
                    fileFound = true;
                    break;
                }

                otherJavaFileId++;
            }

            // if pair file was found compare those files and add java file report to the folder report
            if(fileFound){

                JavaFileCompareReport javaFileCompareReport = thisJavaFile.compareJavaFileTo(
                        otherJavaFiles.get(otherJavaFileId), reportEqual, reportNonContractChanges);

                if(javaFileCompareReport != null && 
                        (reportNonContractChanges || !javaFileCompareReport.isContractEqual())) {
                    javaFolderCompareReport.addJavaFileCompareReport(javaFileCompareReport);
                }

                otherJavaFiles.remove(otherJavaFileId);
            }
            else{
                // if pair file was not found - add it to list of removed files
                javaFolderCompareReport.addFileRemoved(thisJavaFilePath);
                contractsRemoved += thisJavaFile.getJavaFileStatistics().getTotalNumberOfContracts();
            }
        }

        // go through all remaining files in the other folder
        for(JavaFile otherJavaFile : otherJavaFiles) {

            // if non contract changes should not be reported and if there are not any contracts in file - skip it
            if(!reportNonContractChanges && otherJavaFile.getJavaFileStatistics().getTotalNumberOfContracts() == 0){
                continue;
            }

            // add it to the list with added files
            String otherJavaFilePath = otherJavaFile.getPath().substring(otherFolderPath.length() + 1);
            javaFolderCompareReport.addFileAdded(otherJavaFilePath);
            contractsAdded += otherJavaFile.getJavaFileStatistics().getTotalNumberOfContracts();
        }

        // create folder statistics
        int filesAdded = javaFolderCompareReport.getFilesAdded().size();
        int filesRemoved = javaFolderCompareReport.getFilesRemoved().size();
        javaFolderCompareReport.setJavaFolderCompareStatistics(new JavaFolderCompareStatistics(filesAdded, filesRemoved,
                contractsAdded, contractsRemoved));

        
        return javaFolderCompareReport;
    }
}
