package ru.otus.hw.mapper

import groovy.transform.Canonical
import java.lang.reflect.Field

@Canonical
class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaData


    EntitySQLMetaDataImpl(entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData
    }

    @Override
    String getSelectAllSql() {
        String selectAll = "SELECT * FROM ${entityClassMetaData.getName().split( "\\.")[-1]}"
        return selectAll
    }

    @Override
    String getSelectByIdSql(long id) {
        String selectByIdQuery = "SELECT * FROM ${entityClassMetaData.getName().split("\\.")[-1]} WHERE ${entityClassMetaData.getIdField().name}=\'${id}\'"
        return selectByIdQuery
    }

    @Override
    String getInsertSql(Map<String, String> values) {
        List<Field> fields = entityClassMetaData.fieldsWithoutId
        String statementFields = ""
        statementFields = statementFields.join(", ", fields.collect {it.name})

        ArrayList statementValues = []
        values.each {k, v -> statementValues << v}
        statementValues -= values['class']

        String finalValues = ""
        statementValues.eachWithIndex{ it, ind ->
            finalValues += "\'${it}\'"
            if (ind < fields.size() - 1) {
                finalValues += ', '
            }
        }

        String insert = "INSERT INTO ${entityClassMetaData.getName().split("\\.")[-1]} (${statementFields}) VALUES (${finalValues})"
        return insert
    }

    @Override
    String getUpdateSql(Map<String, String> fields, String primaryName, long id) {
        String set = ""
        fields.eachWithIndex { it, ind ->
            if (it.key != 'class') {
                set += "${it.key}=\'${it.value}\'"
                if (ind < fields.size() - 1) {
                    set += ', '
                }
            }
        }
        String update = "UPDATE ${entityClassMetaData.getName().split("\\.")[-1]} SET ${set} WHERE ${primaryName}=${id}"
        return update
    }
}
