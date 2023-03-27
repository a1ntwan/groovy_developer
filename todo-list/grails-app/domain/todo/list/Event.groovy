package todo.list

import java.time.LocalDateTime

class Event {
    long id
    private LocalDateTime startTime

    static constraints = {

        startTime(nullable: false)
    }
}
