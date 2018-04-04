package cz.zcu.kiv.contractparser;

import com.google.common.base.Preconditions;
import cz.zcu.kiv.contractparser.comparator.JavaFolderCompareReport;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.apache.log4j.Logger;

import javax.annotation.*;
import java.io.File;
import java.util.List;


/**
 * Main class - FOR DEBUGGING PURPOSES ONLY
 *
 * @author Vaclav Mares
 */
@WillClose
public class Main {

    final Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) {


        //List<JavaFile> javaFilesX = ContractExtractorApi.retrieveContractsFromFolder("D:\\My\\_ZCU\\dp\\Test Data (anot-20170926T)\\guava\\guava-10.0", contractTypes, false);

        //JavaFile javaFileX = ContractExtractorApi.retrieveContracts("D:\\test\\guava-10.0\\com\\google\\common\\base\\CharMatcher.java", false);
        //JavaFile javaFileY = ContractExtractorApi.retrieveContracts("D:\\test\\guava-13.0\\com\\google\\common\\base\\CharMatcher.java", false);

        //JavaFile javaFileX = ContractExtractorApi.retrieveContracts("D:\\test\\testCaseX.java", false);
        //JavaFile javaFileY = ContractExtractorApi.retrieveContracts("D:\\test\\testCaseY.java", false);


        JavaFolderCompareReport javaFolderCompareReport = ContractComparatorApi.compareJavaFolders("D:\\test\\guava-10.0",
                "D:\\test\\guava-14.0", false, false);

        ContractComparatorApi.exportJavaFolderCompareReportToJson(javaFolderCompareReport, new File("D:\\test\\compareJson"));
        
        System.out.println(javaFolderCompareReport);

        //JavaFileCompareReport javaFileCompareReport = javaFileX.compareJavaFileTo(javaFileY, true, false);
        //exportJavaFileCompareReportToJson(javaFileCompareReport, new File("D:\\test\\compareJson"));


        //IOServices.exportToJson(javaFile, new File("D:/test/mainParserJson"));
        //IOServices.exportManyToJson(javaFiles, new File("D:/test/JSON_export"));
    }

    private static void printOutJavaFiles(){

        List<JavaFile> javaFilesX = ContractExtractorApi.retrieveContractsFromFolder("D:\\test\\guava-10.0", null, false);
        List<JavaFile> javaFilesY = ContractExtractorApi.retrieveContractsFromFolder("D:\\test\\\\guava-19.0", null, false);

        for(JavaFile javaFile : javaFilesX) {

            if(javaFile.getJavaFileStatistics().getTotalNumberOfContracts() > 0){
                System.out.println(javaFile.getPath() + " ::: " + javaFile.getJavaFileStatistics().getTotalNumberOfContracts());
            }
        }

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------------");

        for(JavaFile javaFile : javaFilesY) {

            if(javaFile.getJavaFileStatistics().getTotalNumberOfContracts() > 0){
                System.out.println(javaFile.getPath() + " ::: " + javaFile.getJavaFileStatistics().getTotalNumberOfContracts());
            }
        }
    }


    private static void meassureTime(){

        //long tStart = System.nanoTime();
        //long tEnd = System.nanoTime();
        //long tRes = tEnd - tStart;

        //System.out.println("Total time: " + (tRes/1000000) + "ms (" + javaFiles.size() + " files)");
    }



    @PropertyKey
    @WillClose
    public static String guavaTest(@WillClose String x){

        Preconditions.checkNotNull(x, "x must not be null.");

        //Preconditions.checkArgument(x.length() == 0, "Illegal Argument passed: Negative value %s.", x);

        String y = "ystring";

        return y;
    }
}
