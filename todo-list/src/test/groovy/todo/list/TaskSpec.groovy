package todo.list

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import groovy.sql.Sql

class TaskSpec extends Specification implements DomainUnitTest<Task> {

    def sql = Sql.newInstance("jdbc:h2:mem:devDb", "org.h2.Driver")

    def setup() {
        sql.execute("DROP TABLE IF EXISTS TASK")
        sql.execute("CREATE TABLE TASK (id BIGINT, name VARCHAR(255) NOT NULL, date DATE NOT NULL, start TIMESTAMP NOT NULL, finish TIMESTAMP NOT NULL, duration BIGINT NOT NULL)")
    }

    def cleanup() {
        sql.execute("DROP TABLE IF EXISTS TASK")
    }



    void "test if exists"() {

        def query = sql.execute("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'TASK'")

        expect:
        query
    }
}
