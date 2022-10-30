package com.arakviel.persistence.util;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

//
public abstract class FilterSelectHelper {
    protected final List<String> whereSQL = new ArrayList<>();

    public String getSql(final String findAllSql) {
        System.out.println();
        initParams();
        String where = whereSQL.stream().collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));
        return whereSQL.isEmpty() ? findAllSql + " LIMIT ? OFFSET ?" : findAllSql + where;
    }

    protected abstract void initParams();
}
