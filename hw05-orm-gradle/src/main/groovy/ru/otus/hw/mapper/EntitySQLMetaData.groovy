package ru.otus.hw.mapper;

/**
 * Создает SQL - запросы
 */
interface EntitySQLMetaData {
    String getSelectAllSql()

    String getSelectByIdSql() // SELECT * FROM <CLient>  WHERE <Client.id> = ?

    String getInsertSql(Map<String, String> values)

    String getUpdateSql(Map<String, String> fields, long id)
}
