package com.epam.esm.util;

import static com.epam.esm.dao.StringParameters.AND;
import static com.epam.esm.dao.StringParameters.ASC;
import static com.epam.esm.dao.StringParameters.COLUMN_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.COLUMN_NAME;
import static com.epam.esm.dao.StringParameters.COLUMN_NAME_FOR_TAG;
import static com.epam.esm.dao.StringParameters.LIKE;
import static com.epam.esm.dao.StringParameters.ORDER_BY;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_NAME;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_SORT;
import static com.epam.esm.dao.StringParameters.PATTERN_KEY_TAG;
import static com.epam.esm.dao.StringParameters.PREFIX_GC;
import static com.epam.esm.dao.StringParameters.WHERE;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.JOIN_TAG;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GiftCertificateParameter {

    public void buildSort(Map<String, String> parameters, StringBuilder fullFindBuilder) {
        if (parameters.containsKey(PATTERN_KEY_SORT)) {
            String sort = parameters.get(PATTERN_KEY_SORT);
            if (!sort.isEmpty()) {
                fullFindBuilder.append(ORDER_BY);
                Map<String, String> tokensMap = splitSortLineOnTokens(sort);

                tokensMap.forEach((key, value) -> fullFindBuilder.append(PREFIX_GC).append(key)
                    .append(" ")
                    .append(value)
                    .append(","));
                fullFindBuilder.deleteCharAt(fullFindBuilder.lastIndexOf(","));
            }
        }
    }

    public void buildDescription(Map<String, String> parameters,
                                 boolean isWhereUsed,
                                 StringBuilder fullFindBuilder) {
        if (parameters.containsKey(PATTERN_KEY_DESCRIPTION)) {
            if (!isWhereUsed) {
                fullFindBuilder.append(WHERE);
            } else {
                fullFindBuilder.append(AND);
            }

            String description = parameters.get(PATTERN_KEY_DESCRIPTION);
            fullFindBuilder.append(" ")
                .append(COLUMN_DESCRIPTION)
                .append(" ")
                .append(LIKE)
                .append("'%")
                .append(description)
                .append("%'")
                .append(AND)
                .append(" ");
            fullFindBuilder.delete(fullFindBuilder.lastIndexOf(AND), fullFindBuilder.length());
        }
    }

    public boolean buildName(Map<String, String> parameters,
                             boolean isWhereUsed,
                             StringBuilder fullFindBuilder) {
        if (parameters.containsKey(PATTERN_KEY_NAME)) {
            if (!isWhereUsed) {
                fullFindBuilder.append(WHERE).append(" ");
            } else {
                fullFindBuilder.append(AND).append(" ");
            }

            String name = parameters.get(PATTERN_KEY_NAME);
            fullFindBuilder.append(COLUMN_NAME)
                .append(LIKE)
                .append("'%")
                .append(name)
                .append("%'")
                .append(AND)
                .append(" ");
            fullFindBuilder.delete(fullFindBuilder.lastIndexOf(AND), fullFindBuilder.length());

            isWhereUsed = true;
        }
        return isWhereUsed;
    }

    public boolean buildTag(Map<String, String> parameters,
                            boolean isWhereUsed,
                            StringBuilder fullFindBuilder) {
        if (parameters.containsKey(PATTERN_KEY_TAG)) {
            String tag = parameters.get(PATTERN_KEY_TAG);
            fullFindBuilder.append(" ").append(JOIN_TAG)
                .append(" ")
                .append(WHERE)
                .append(" ")
                .append(COLUMN_NAME_FOR_TAG)
                .append(" = '")
                .append(tag)
                .append("' ")
                .append(AND)
                .append(" ");
            fullFindBuilder.delete(fullFindBuilder.lastIndexOf(AND), fullFindBuilder.length());
            isWhereUsed = true;

        }
        return isWhereUsed;
    }

    private Map<String, String> splitSortLineOnTokens(String line) {
        Map<String, String> sortMap = new HashMap<>();
        Arrays.stream(line.split(","))
            .forEach(sortLine -> {
                int indexOfColon = sortLine.indexOf(":");
                if (indexOfColon == -1) {
                    sortMap.put(sortLine, ASC);
                } else {
                    sortMap.put(sortLine.substring(0, indexOfColon), sortLine.substring(indexOfColon + 1));
                }
            });
        return sortMap;
    }
}
