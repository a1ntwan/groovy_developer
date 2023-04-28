package todo.list

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import groovy.sql.Sql


class EventSpec extends Specification implements DomainUnitTest<Event> {

    def sql = Sql.newInstance("jdbc:h2:mem:devDb", "org.h2.Driver")

    def setup() {
        sql.execute("DROP TABLE IF EXISTS EVENT")
        sql.execute("CREATE TABLE EVENT (id BIGINT, start_time TIMESTAMP NOT NULL)")
    }

    def cleanup() {
        sql.execute("DROP TABLE IF EXISTS EVENT")
    }

    void "test if exists"() {

        def query = sql.execute("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'EVENT'")

        expect:
        query
    }
}
