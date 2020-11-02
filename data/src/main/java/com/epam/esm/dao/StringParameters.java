package com.epam.esm.dao;

public class StringParameters {

    public static final String ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LAST_UPDATE_DATE = "last_update_date";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DURATION = "duration";
    public static final String ORDER_BY = " order by ";
    public static final String DESC = " desc ";
    public static final String ASC = " asc ";
    public static final String WHERE = " where ";
    public static final String LIKE = " like ";
    public static final String AND = " and ";
    public static final String PATTERN_KEY_SORT = "sort";
    public static final String PATTERN_KEY_NAME = "name";
    public static final String PATTERN_KEY_DESCRIPTION = "description";
    public static final String PATTERN_SORT_ON_COLUMN = "(data|name)";
    public static final String PATTERN_SORT_ON_PRIORITY = "(asc|desc)";
    public static final String PATTERN_DATE = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}";
    public static final String EMPTY = "empty";
    public static final String NEGATIVE = "negative";

    private StringParameters() {
    }
}
