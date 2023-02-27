package ru.otus.hw.repository

import java.sql.ResultSet

/**
 * CRUD operations with database (like JdbcTemplate)
 */
interface DataTemplate<T> {
    def findById(connection, long id);

    ResultSet findAll(connection);

    long insert(connection, object);

    void update(connection, object);
}
