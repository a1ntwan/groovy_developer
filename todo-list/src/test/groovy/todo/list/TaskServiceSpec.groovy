package todo.list

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import grails.web.servlet.mvc.GrailsParameterMap
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Integration
@Rollback
class TaskServiceSpec extends Specification implements ServiceUnitTest<TaskService>{

    @Autowired TaskService service


    def setup() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')
        DateTimeFormatter formatterD = DateTimeFormatter.ofPattern('yyyy-MM-dd')

        Action one = new Action(activity: "first", start: LocalDateTime.parse("2023-12-02 10:00", formatter), finish: LocalDateTime.parse("2023-12-02 10:30", formatter), duration: ChronoUnit.SECONDS.between(LocalDateTime.parse("2023-12-02 10:00", formatter), LocalDateTime.parse("2023-12-02 10:30", formatter)))
        Action two = new Action(activity: "sec", start: LocalDateTime.parse("2023-12-02 10:30", formatter), finish: LocalDateTime.parse("2023-12-02 11:00", formatter), duration: ChronoUnit.SECONDS.between(LocalDateTime.parse("2023-12-02 10:30", formatter), LocalDateTime.parse("2023-12-02 11:00", formatter)))
        Event onee = new Event(startTime: one.start).save()
        Event twoo = new Event(startTime: two.start).save()
        Task task = new Task(name: "task", date: LocalDate.parse("2023-12-02", formatterD), start: LocalDateTime.parse("2023-12-02 10:00", formatter), finish: LocalDateTime.parse("2023-12-02 11:00", formatter), duration: ChronoUnit.SECONDS.between(LocalDateTime.parse("2023-12-02 10:00", formatter), LocalDateTime.parse("2023-12-02 11:00", formatter))).addToActions(one).addToActions(two)

        return task
    }

    def cleanup() {
        def task = setup()
        task.delete()
    }

    void "create task"() {
        given:
        setup()
        GrailsParameterMap map = new GrailsParameterMap(["task":"3","name":"test","date":"2024-12-02","start":"10:00","finish":"11:00"], null)
        GrailsParameterMap map1 = new GrailsParameterMap(["task":"4","name":"test1","date":"2024-12-02","start":"11:00","finish":"10:00"], null)
        GrailsParameterMap map2 = new GrailsParameterMap(["task":"5","name":"test2","date":"2020-12-02","start":"10:00","finish":"11:00"], null)
        def response = service.save(map)
        def response1 = service.save(map1)
        def response2 = service.save(map2)

        expect:
        response.isSuccess == true
        response1.isSuccess == false
        response2.isSuccess == false

        and:
        response.task.name == "test"
    }

    void "update task"() {

        given:
        def task = setup()
        GrailsParameterMap map = new GrailsParameterMap(["task":"1","name":"updated","date":"2023-12-02","start":"10:00","finish":"11:00"], null)
        service.update(task, map)

        expect:
        task.name == "updated"
    }

    void "delete task"() {

        given:
        def task = setup()
        def result = service.delete(task)

        expect:
        result == true
    }

    void "get by id"() {

        given:
        setup()
        def task = Task.find {name == 'task' }
        def id = task.id

        expect:
        service.getById(id).name == 'task'
    }

}
