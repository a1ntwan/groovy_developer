package ru.otus.hw.repository

/**
 * CRUD operations with database (like JdbcTemplate)
 */
interface DataTemplate<T> {
    def findById(connection, long id);

    List findAll(connection);

    long insert(connection, object);

    void update(connection, object);
}
