package todo.list

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import groovy.sql.Sql


class ActionSpec extends Specification implements DomainUnitTest<Action> {

    def sql = Sql.newInstance("jdbc:h2:mem:devDb", "org.h2.Driver")

    def setup() {
        sql.execute("DROP TABLE IF EXISTS ACTION")
        sql.execute("CREATE TABLE ACTION (id BIGINT, activity VARCHAR(255) NOT NULL, start TIMESTAMP NOT NULL, finish TIMESTAMP NOT NULL, duration BIGINT NOT NULL, task_id BIGINT NOT NULL)")
    }

    def cleanup() {
        sql.execute("DROP TABLE IF EXISTS ACTION")
    }

    void "test if exists"() {

        def query = sql.execute("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'ACTION'")

        expect:
        query
    }
}
