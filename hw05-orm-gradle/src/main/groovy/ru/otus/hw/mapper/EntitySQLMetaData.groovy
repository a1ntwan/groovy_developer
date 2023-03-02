package ru.otus.hw.mapper;

/**
 * Создает SQL - запросы
 */
interface EntitySQLMetaData {
    String getSelectAllSql()

    String getSelectByIdSql(long id) // SELECT * FROM <CLient>  WHERE <Client.id> = ?

    String getInsertSql(Map<String, String> values)

    String getUpdateSql(Map<String, String> fields, String primaryName, long id)
}
