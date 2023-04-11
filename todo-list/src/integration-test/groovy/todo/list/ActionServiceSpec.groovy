package todo.list

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class ActionServiceSpec extends Specification {

    ActionService actionService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Action(...).save(flush: true, failOnError: true)
        //new Action(...).save(flush: true, failOnError: true)
        //Action action = new Action(...).save(flush: true, failOnError: true)
        //new Action(...).save(flush: true, failOnError: true)
        //new Action(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //action.id
    }

    void "test get"() {
        setupData()

        expect:
        actionService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Action> actionList = actionService.list(max: 2, offset: 2)

        then:
        actionList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        actionService.count() == 5
    }

    void "test delete"() {
        Long actionId = setupData()

        expect:
        actionService.count() == 5

        when:
        actionService.delete(actionId)
        sessionFactory.currentSession.flush()

        then:
        actionService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Action action = new Action()
        actionService.save(action)

        then:
        action.id != null
    }
}
