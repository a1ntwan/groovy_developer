package ru.otus.hw.mapper

import groovy.transform.Canonical
import ru.otus.hw.exception.EntityMetaDataException
import ru.otus.hw.mapper.EntityClassMetaData
import ru.otus.hw.mapper.EntitySQLMetaData

import java.lang.reflect.Field

@Canonical
class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaData

    private final selectByIdQuery;

    EntitySQLMetaDataImpl(entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData
        selectByIdQuery = "SELECT * FROM ${entityClassMetaData.getName()} WHERE ${entityClassMetaData.getIdField()} = ?"
    }

    @Override
    String getSelectAllSql() {
        String selectAll = "SELECT * FROM ${entityClassMetaData.getName()}"
        return selectAll
    }

    @Override
    String getSelectByIdSql() {
        return selectByIdQuery
    }

    @Override
    String getInsertSql(Map<String, String> values) {
        List<Field> fields = entityClassMetaData.fieldsWithoutId
        String statementFields = ""
        ArrayList statementValues = []
        statementFields = statementFields.join(", ", fields.collect {it.name})
        values.each {k, v -> statementValues << v}
        println(statementValues)
        String insert = "INSERT INTO ${entityClassMetaData.getName()} (${statementFields}) VALUES (${statementValues})"
        return insert
    }

    @Override
    String getUpdateSql(Map<String, String> fields, long id) {
        String set = ""
        set = set.join(", ", fields.collect {it.key = it.value})
        String update = "UPDATE ${entityClassMetaData.getName()} SET ${set} WHERE id=${id}"
        return update
    }
}
