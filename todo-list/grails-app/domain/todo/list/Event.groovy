package todo.list

import java.time.LocalDateTime

class Event {
    private LocalDateTime startTime

    static constraints = {

        startTime(nullable: false)
    }

    static mapping = {
        version(false)
    }
}
