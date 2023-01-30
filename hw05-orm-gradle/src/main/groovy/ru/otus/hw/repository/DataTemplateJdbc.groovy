package ru.otus.hw.repository

import groovy.transform.Canonical
import ru.otus.hw.mapper.EntityClassMetaData
import ru.otus.hw.mapper.EntitySQLMetaData
import ru.otus.hw.mapper.EntitySQLMetaDataImpl

@Canonical
class DataTemplateJdbc implements DataTemplate {
    private DbExecutor dbExecutor
    private EntitySQLMetaData entitySQLMetaData
    private EntityClassMetaData entityClassMetaData

    DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor
        this.entitySQLMetaData = entitySQLMetaData
        this.entityClassMetaData = entityClassMetaData
    }

    @Override
    def findById( connection, long id) {
        return connection.execute(entitySQLMetaData.getSelectByIdSql())
    }

    @Override
    List findAll( connection) {
        return connection.execute(entitySQLMetaData.getSelectAllSql())
    }

    @Override
    long insert( connection,  object) {
        Map map = object.properties
        map.remove(entityClassMetaData.idField.name)
        connection.execute(entitySQLMetaData.getInsertSql(map))
        return 0
    }

    @Override
    void update( connection,  object) {
        Map map = object.properties
        map.remove(entityClassMetaData.idField.name)
        Long id = object.properties.get(entityClassMetaData.idField.name) as long
        connection.execute(entitySQLMetaData.getUpdateSql(map, id))
    }
}
