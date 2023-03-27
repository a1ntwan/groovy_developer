package todo.list

import java.time.LocalDateTime

class Action {
    String activity
    int duration
    long taskId


    static constraints = {
        activity(nullable: false)
        duration(nullable: false)
        taskId(nullable: false)
    }
}
