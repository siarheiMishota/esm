package com.epam.esm.util;

import static com.epam.esm.dao.StringParameters.AND;
import static com.epam.esm.dao.StringParameters.ASC;
import static com.epam.esm.dao.StringParameters.COLUMN_DESCRIPTION;
import static com.epam.esm.dao.StringParameters.COLUMN_NAME;
import static com.epam.esm.dao.StringParameters.COLUMN_NAME_FOR_TAG;
import static com.epam.esm.dao.StringParameters.IN;
import static com.epam.esm.dao.StringParameters.LIKE;
import static com.epam.esm.dao.StringParameters.ORDER_BY;
import static com.epam.esm.dao.StringParameters.PREFIX_GC;
import static com.epam.esm.dao.StringParameters.WHERE;
import static com.epam.esm.dao.sqlRequest.SqlRequestGiftCertificate.JOIN_TAG;

import com.epam.esm.entity.GiftCertificateParameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class GiftCertificateSqlBuilder {

    public void buildSort(GiftCertificateParameter giftCertificateParameter, StringBuilder fullFindBuilder) {

        if (giftCertificateParameter.getSort() == null) {
            return;
        }

        String sort = replaceDateOnLastUpdateDateInLine(giftCertificateParameter.getSort());
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

    public void buildDescription(GiftCertificateParameter giftCertificateParameter, boolean isWhereUsed,
                                 StringBuilder fullFindBuilder) {
        if (giftCertificateParameter.getDescription() == null) {
            return;
        }

        if (!isWhereUsed) {
            fullFindBuilder.append(WHERE);
        } else {
            fullFindBuilder.append(AND);
        }

        String description = giftCertificateParameter.getDescription();
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

    public boolean buildName(GiftCertificateParameter giftCertificateParameter, boolean isWhereUsed,
                             StringBuilder fullFindBuilder) {
        if (giftCertificateParameter.getName() == null) {
            return isWhereUsed;
        }

        if (!isWhereUsed) {
            fullFindBuilder.append(WHERE).append(" ");
        } else {
            fullFindBuilder.append(AND).append(" ");
        }

        String name = giftCertificateParameter.getName();
        fullFindBuilder.append(COLUMN_NAME)
            .append(LIKE)
            .append("'%")
            .append(name)
            .append("%'")
            .append(AND)
            .append(" ");
        fullFindBuilder.delete(fullFindBuilder.lastIndexOf(AND), fullFindBuilder.length());

        isWhereUsed = true;

        return isWhereUsed;
    }

    public boolean buildTag(GiftCertificateParameter giftCertificateParameter, boolean isWhereUsed,
                            StringBuilder fullFindBuilder) {
        if (giftCertificateParameter.getTags().isEmpty()) {
            return isWhereUsed;
        }

        String condition = giftCertificateParameter.getTags().stream()
            .collect(Collectors.joining("','", "('", "')"));

        fullFindBuilder.append(" ").append(JOIN_TAG)
            .append(" ")
            .append(WHERE)
            .append(" ")
            .append(COLUMN_NAME_FOR_TAG)
            .append(IN)
            .append(" ")
            .append(condition);

        isWhereUsed = true;
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

    private String replaceDateOnLastUpdateDateInLine(String line) {
        return line.replaceAll("date", "lastUpdateDate");
    }

}
