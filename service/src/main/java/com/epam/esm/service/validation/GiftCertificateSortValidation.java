package com.epam.esm.service.validation;

import static com.epam.esm.dao.SqlRequestGiftCertificate.LIKE;
import static com.epam.esm.dao.SqlRequestGiftCertificate.OR;
import static com.epam.esm.dao.SqlRequestGiftCertificate.ORDER_BY;
import static com.epam.esm.dao.SqlRequestGiftCertificate.WHERE;

import java.util.HashMap;
import java.util.Map;

public class GiftCertificateSortValidation {

    public static final String PARAMETER_SORT = "sort";
    public static final String PARAMETER_NAME = "name";
    public static final String PARAMETER_DESCRIPTION = "description";
    public static final String COLUMN_ID = " id ";
    public static final String COLUMN_NAME = " name ";
    public static final String COLUMN_DESCRIPTION = " description ";

    public static final String DESC = " desc ";
    public static final String ASC = " asc ";

    private GiftCertificateSortValidation() {
    }

    public static Map<String, String> sortValidate(Map<String, String> parameters) {
        if (parameters == null) {
            return new HashMap<>();
        }

        if ()
    }

    private String getFullSqlWithParameters(Map<String, String> parameters, String request) {
        if (parameters == null) {
            return request;
        }

        boolean whereUse = false;
        String fullFind = request;

        if (parameters.containsKey("name")) {
            fullFind = fullFind + WHERE + COLUMN_NAME + LIKE + "'%" + parameters.get("name") + "%'";
            whereUse = true;
        }

        if (parameters.containsKey("description")) {
            if (!whereUse) {
                fullFind += WHERE;
            } else {
                fullFind = fullFind + OR + COLUMN_DESCRIPTION + LIKE + "'%" + parameters.get("description") + "%'";
            }
        }

        if (parameters.containsKey("sort")) {
            fullFind += ORDER_BY;
            Map<String, String> tokensMap = splitSortLineOnTokens(parameters.get("sort"));

            for (Map.Entry<String, String> entryToken : tokensMap.entrySet()) {
                fullFind = fullFind + entryToken.getKey() + " " + entryToken.getValue() + ",";
            }
            fullFind = fullFind.substring(0, fullFind.length() - 1);
        }
        return fullFind;
    }
