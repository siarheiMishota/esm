package com.epam.esm.dao.sqlRequest;

public final class SqlRequestTag {

    private static final String BASE_QUERY = "select t from Tag t ";
    public static final String FIND_ALL = BASE_QUERY;
    public static final String FIND_BY_NAME = BASE_QUERY + "  where t.name= :name";
    public static final String FIND_MOST_USED_BY_USER_HIGHEST_COST =
        " select t.id, t.name\n"
            + "from tag t\n"
            + "         join tag_gift_certificate tgc on t.id = tgc.tag_id\n"
            + "where tgc.gift_certificate_id in (\n"
            + "    select gc.id\n"
            + "    from gift_certificate gc\n"
            + "             join orders o on gc.id = o.id_gift_certificate\n"
            + "    where o.id_user = (\n"
            + "        select id_user\n"
            + "        from orders\n"
            + "        group by id_user\n"
            + "        order by sum(cost) desc\n"
            + "        limit 1\n"
            + "    ))\n"
            + "group by t.id\n"
            + "order by count(t.id) desc\n"
            + "limit 1\n";

    private SqlRequestTag() {
    }
}
