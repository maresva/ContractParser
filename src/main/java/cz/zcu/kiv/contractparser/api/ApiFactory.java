package cz.zcu.kiv.contractparser.api;

/**
 * Provides instances of APIs primary for external use.
 *
 * @author Vaclav Mares
 */
public class ApiFactory {

    /**
     * Provides instance of ContractExtractorApi. As for now only available API is DefaultContractExtractorApi.
     *
     * @return  Instance of ContractExtractorApi
     */
    public ContractExtractorApi getContractExtractorApi(){

        return new DefaultContractExtractorApi();
    }


    /**
     * Provides instance of BatchContractExtractorApi. As for now only available API is DefaultBatchContractExtractorApi.
     *
     * @return  Instance of BatchContractExtractorApi
     */
    public BatchContractExtractorApi getBatchContractExtractorApi(){

        return new DefaultBatchContractExtractorApi();
    }


    /**
     * Provides instance of ContractComparatorApi. As for now only available API is DefaultContractComparatorApi.
     *
     * @return  Instance of ContractComparatorApi
     */
    public ContractComparatorApi getContractComparatorApi(){

        return new DefaultContractComparatorApi();
    }


    /**
     * Provides instance of BatchContractComparatorApi. As for now only available API is DefaultBatchContractComparatorApi.
     *
     * @return  Instance of BatchContractComparatorApi
     */
    public BatchContractComparatorApi getBatchContractComparatorApi(){

        return new DefaultBatchContractComparatorApi();
    }
}
