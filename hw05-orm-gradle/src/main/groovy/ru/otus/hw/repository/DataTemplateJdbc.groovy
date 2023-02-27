package ru.otus.hw.repository

import groovy.transform.Canonical
import ru.otus.hw.mapper.EntityClassMetaData
import ru.otus.hw.mapper.EntitySQLMetaData
import java.sql.PreparedStatement
import java.sql.ResultSet

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
    ResultSet findById( connection, long id) {
        PreparedStatement pst = connection.prepareStatement(entitySQLMetaData.getSelectByIdSql(id))
        ResultSet rs = pst.executeQuery()
        return rs
    }

    @Override
    ResultSet findAll( connection) {
        PreparedStatement pst = connection.prepareStatement(entitySQLMetaData.getSelectAllSql())
        ResultSet rs = pst.executeQuery()
        return rs
    }

    @Override
    long insert( connection,  object) {
        Map map = object.properties
        map.remove(entityClassMetaData.idField.name)
        PreparedStatement pst = connection.prepareStatement(entitySQLMetaData.getInsertSql(map))
        pst.executeUpdate()
        return 1
    }

    @Override
    void update( connection,  object) {
        Map map = object.properties
        map.remove(entityClassMetaData.idField.name)
        Long id = object.properties.get(entityClassMetaData.idField.name) as long
        PreparedStatement pst = connection.prepareStatement(entitySQLMetaData.getUpdateSql(map, entityClassMetaData.idField.name, id))
        pst.executeUpdate()
    }
}
