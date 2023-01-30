package lesson07.hw.repository

import groovy.sql.DataSet

//language=SQL
class DataTemplateJDBC implements DataTemplate {

    @Override
    Optional<T> findById(connection, long id){
        String pst = "SELECT * FROM employees WHERE id==(?)"
        List params = [id]
        DbExecutorImpl executor = new DbExecutorImpl()
        executor.executeSelect(connection, pst, params)
    }

    @Override
    List<T> findAll(connection) {
        String pst = "SELECT * FROM employees"
        DbExecutorImpl executor = new DbExecutorImpl()
        executor.executeSelect(connection, pst, [])
    }

    @Override
    long insert(connection, object) {
        String pst = "INSERT INTO employees (first_name, second_name) VALUES (?, ?)"
        List params = [['1', '2']]
        DbExecutorImpl executor = new DbExecutorImpl()
        executor.executeStatement(connection, pst, params)
    }

    @Override
    void update(connection, object) {
        String pst = "UPDATE employees SET (first_name, second_name) VALUES (?, ?)"
        List params = [['1', '2']]
        DbExecutorImpl executor = new DbExecutorImpl()
        executor.executeStatement(connection, pst, params)
    }
}
