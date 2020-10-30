package com.epam.esm.service.validation;

import java.util.Map;

import static com.epam.esm.dao.SqlRequestGiftCertificate.*;
import static com.epam.esm.dao.SqlRequestGiftCertificate.ORDER_BY;

public class GiftCertificateSortValidation {

    public static final String PARAMETER_SORT="sort";
    public static final String PARAMETER_DESCRIPTION="description";
    public static final String COLUMN_NAME="name";

    private GiftCertificateSortValidation() {
    }

    public static boolean sortValidate(String sortString){
        if (sortString==null){
            return true;
        }
//todo
        return true;
    }

    private String getFullSqlWithParameters(Map<String, String> parameters, String request) {
        if (parameters==null){
            return request;
        }

        boolean whereUse = false;
        String fullFind = request;

        if (parameters.containsKey("name")) {
            fullFind = fullFind + WHERE + COLUMN_NAME + LIKE +"'%"+ parameters.get("name")+"%'";
            whereUse = true;
        }

        if (parameters.containsKey("description")) {
            if (!whereUse) {
                fullFind += WHERE;
            } else {
                fullFind = fullFind + OR + COLUMN_DESCRIPTION + LIKE +"'%"+ parameters.get("description")+"%'";
            }
        }

        if (parameters.containsKey("sort")) {
            fullFind += ORDER_BY;
            Map<String, String> tokensMap = splitSortLineOnTokens(parameters.get("sort"));

            for (Map.Entry<String, String> entryToken: tokensMap.entrySet()) {
                fullFind = fullFind + entryToken.getKey() + " " + entryToken.getValue() + ",";
            }
            fullFind=fullFind.substring(0,fullFind.length()-1);
        }
        return fullFind;
}
