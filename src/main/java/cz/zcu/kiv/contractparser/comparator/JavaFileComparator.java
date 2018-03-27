package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.model.Contract;
import cz.zcu.kiv.contractparser.model.JavaClass;
import cz.zcu.kiv.contractparser.model.JavaFile;

import java.util.ArrayList;
import java.util.List;

public class JavaFileComparator {

    public JavaFileCompareReport compareJavaFiles(JavaFile javaFileX, JavaFile javaFileY, boolean reportEqual){

        List<Contract> contractsX = javaFileX.getContracts();
        List<Contract> contractsY = javaFileY.getContracts();
        List<ContractCompareReport> contractReports =  new ArrayList<>();

        JavaFileComparison javaFileComparison = JavaFileComparison.EQUAL;

        for(int i = 0 ; i < contractsX.size() ; i++){

            if(contractsY.size() <= i){
                javaFileComparison = JavaFileComparison.CONTRACT_DIFFERENT;
                break;
            }

            if(contractsX == null){
                if(contractsY == null){
                    javaFileComparison = JavaFileComparison.CONTRACT_DIFFERENT;
                }
                continue;
            }

            // create contract compare report and add it to the list
            ContractComparison contractComparison = contractsX.get(i).compareTo(contractsY.get(i));
            ContractCompareReport contractCompareReport = new ContractCompareReport(contractComparison,
                    contractsX.get(i).getClassName(), contractsX.get(i).getMethodName(),
                    contractsX.get(i).getCompleteExpression(), contractsY.get(i).getCompleteExpression());
            contractReports.add(contractCompareReport);

            if(contractCompareReport.getContractComparison() == ContractComparison.MESSAGE_CHANGE ||
                    contractCompareReport.getContractComparison() == ContractComparison.DIFFERENT) {

                javaFileComparison = JavaFileComparison.CONTRACT_DIFFERENT;
            }
        }

        return new JavaFileCompareReport(javaFileComparison, contractReports);
    }


    public List<ContractCompareReport>  getContractsReports(JavaFile javaFile){

        List<ContractCompareReport> contractReports = new ArrayList<>();

        for(JavaClass javaClass : javaFile.getClasses()){

        }

        return contractReports;
    }


    public void exploreJavaClass(JavaClass javaClass, List<ContractCompareReport> contractReports){


    }
}
