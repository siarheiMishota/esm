package com.epam.esm.service.validation;

import static com.epam.esm.dao.StringParameters.COLUMN_DATE;
import static com.epam.esm.dao.StringParameters.COLUMN_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.COLUMN_DURATION;
import static com.epam.esm.dao.StringParameters.COLUMN_LAST_UPDATE_DATE;
import static com.epam.esm.dao.StringParameters.COLUMN_NAME;
import static com.epam.esm.dao.StringParameters.COLUMN_PRICE;
import static com.epam.esm.dao.StringParameters.EMPTY;
import static com.epam.esm.dao.StringParameters.NEGATIVE;
import static com.epam.esm.dao.StringParameters.PATTERN_DATE;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_NAME;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_SORT;
import static com.epam.esm.dao.StringParameters.PATTERN_SORT_ON_COLUMN;
import static com.epam.esm.dao.StringParameters.PATTERN_SORT_ON_PRIORITY;

import com.epam.esm.entity.GiftCertificate;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateValidation {

    public Map<String, String> validateCreating(GiftCertificate giftCertificate) {
        Map<String, String> result = new HashMap<>();

        if (giftCertificate.getName() == null) {
            result.put(COLUMN_NAME, EMPTY);
        }

        if (giftCertificate.getDescription() == null) {
            result.put(COLUMN_DESCRIPTION, EMPTY);
        }

        if (giftCertificate.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            result.put(COLUMN_PRICE, NEGATIVE);
        }

        if (giftCertificate.getDuration() < 0) {
            result.put(COLUMN_DURATION, NEGATIVE);
        }
        return result;
    }

    public boolean validateDate(String date) {
        if (date == null) {
            return false;
        }

        return date.matches(PATTERN_DATE);
    }

    public Map<String, String> validateRequestLine(Map<String, String> parameters) {
        if (parameters == null) {
            return new HashMap<>();
        }

        Map<String, String> validatedMap = new HashMap<>();
        if (parameters.containsKey(PATTERN_KEY_NAME)) {
            validateNameLine(parameters, validatedMap);
        }

        if (parameters.containsKey(PATTERN_KEY_DESCRIPTION)) {
            validateDescriptionLine(parameters, validatedMap);
        }

        if (parameters.containsKey(PATTERN_KEY_SORT)) {
            validateSortLine(parameters, validatedMap);
        }
        return validatedMap;
    }

    private void validateNameLine(Map<String, String> parameters, Map<String, String> validatedMap) {
        if (parameters.get(PATTERN_KEY_NAME) == null) {
            return;
        }
        validatedMap.put(PATTERN_KEY_NAME, parameters.get(PATTERN_KEY_NAME));
    }

    private void validateDescriptionLine(Map<String, String> parameters, Map<String, String> validatedMap) {
        if (parameters.get(PATTERN_KEY_DESCRIPTION) == null) {
            return;
        }
        validatedMap.put(PATTERN_KEY_DESCRIPTION, parameters.get(PATTERN_KEY_DESCRIPTION));
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
                validatedMap.get(PATTERN_KEY_SORT).replace(COLUMN_DATE, COLUMN_LAST_UPDATE_DATE));
        }
    }
}