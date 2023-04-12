package todo.list

import grails.rest.Resource

import java.time.LocalDateTime

@Resource(uri='/events', formats=['json', 'xml'])
class Event {

    LocalDateTime startTime

    static constraints = {
    }

    static mapping = {
        version(false)
    }
}
