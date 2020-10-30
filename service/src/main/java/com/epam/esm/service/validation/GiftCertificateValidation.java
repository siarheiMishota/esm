package com.epam.esm.service.validation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateValidation {

    public static final String PATTERN_KEY_SORT = "sort";
    public static final String PATTERN_NAME = "name";
    public static final String PATTERN_DESCRIPTION = "description";
    public static final String PATTERN_SORT_ON_COLUMN = "(data|name)";
    public static final String PATTERN_SORT_ON_PRIORITY = "(asc|desc)";
    public static final String DATE = "date";
    public static final String COLUMN_LAST_UPDATE_DATE = "last_update_date";

    public GiftCertificateValidation() {
    }

    public Map<String, String> validate(Map<String, String> parameters) {
        if (parameters == null) {
            return new HashMap<>();
        }

        Map<String, String> validatedMap = new HashMap<>();
        if (parameters.containsKey(PATTERN_NAME)) {
            validateNameLine(parameters, validatedMap);
        }

        if (parameters.containsKey(PATTERN_DESCRIPTION)) {
            validateDescriptionLine(parameters, validatedMap);
        }

        if (parameters.containsKey(PATTERN_KEY_SORT)) {
            validateSortLine(parameters, validatedMap);
        }
        return validatedMap;
    }

    private void validateNameLine(Map<String, String> parameters, Map<String, String> validatedMap) {
        if (parameters.get(PATTERN_NAME) == null) {
            return;
        }
        validatedMap.put(PATTERN_NAME, parameters.get(PATTERN_NAME));
    }

    private void validateDescriptionLine(Map<String, String> parameters, Map<String, String> validatedMap) {
        if (parameters.get(PATTERN_DESCRIPTION) == null) {
            return;
        }
        validatedMap.put(PATTERN_DESCRIPTION, parameters.get(PATTERN_DESCRIPTION));
    }

    private void validateSortLine(Map<String, String> parameters, Map<String, String> validatedMap) {
        if (parameters.get(PATTERN_KEY_SORT) == null) {
            return;
        }

        String sortLine = parameters.get(PATTERN_KEY_SORT);

        Arrays.stream(sortLine.split(","))
            .forEach(part -> {
                int indexOfColon = sortLine.indexOf(":");
                if (indexOfColon == -1) {
                    if (part.matches(PATTERN_SORT_ON_COLUMN)) {
                        validatedMap.put(PATTERN_KEY_SORT, part);
                    }
                } else if (part.substring(0, indexOfColon).matches(PATTERN_SORT_ON_COLUMN) &&
                    part.substring(indexOfColon + 1).matches(PATTERN_SORT_ON_PRIORITY)) {
                    validatedMap.put(PATTERN_KEY_SORT, part);
                }
            });
        if (validatedMap.containsKey(PATTERN_KEY_SORT)) {
            validatedMap.put(PATTERN_KEY_SORT,
                validatedMap.get(PATTERN_KEY_SORT).replace(DATE, COLUMN_LAST_UPDATE_DATE));
        }
    }
}