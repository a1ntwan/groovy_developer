package todo.list

import java.time.LocalDateTime
import java.time.LocalDate

class Task {
    String name
    LocalDate date
    LocalDateTime start
    LocalDateTime finish

    static hasMany = [actions: Action]

    static constraints = {
        name(nullable: false, blank: true)
        date(nullable: false, blank: true)
        start(nullable: false, blank: true)
        finish(nullable: false, blank: true)
    }

    static mapping = {
        version(false)
        Action(cascade: 'all-delete-orphan', lazy: false)
    }
}
