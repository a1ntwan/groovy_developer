package todo.list

import java.time.LocalDateTime

class Action {
    String activity
    Task task
    LocalDateTime start
    LocalDateTime finish

    static belongsTo = [task: Task]

    static constraints = {
        activity(nullable: false, blank: true)
        task(nullable: false, blank: true)
        start(nullable: false, blank: true)
        finish(nullable: false, blank: true)
    }

    static mapping = {
        version(false)
    }
}
