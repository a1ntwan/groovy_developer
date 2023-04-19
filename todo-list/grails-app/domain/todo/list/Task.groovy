package todo.list

import java.time.LocalDateTime
import java.time.LocalDate

class Task {
    String name
    LocalDate date
    LocalDateTime start
    LocalDateTime finish
    long duration

    static hasMany = [actions: Action]

    static constraints = {
        name(nullable: false, blank: true)
        date(nullable: false, blank: true)
        date validator: { val, obj, errors ->
            if (!val.isAfter(LocalDate.now())) errors.rejectValue('date', 'noMatch')
        }
        start(nullable: false, blank: true, unique: true)
        finish(nullable: false, blank: true, unique: true)
        finish validator: { val, obj, errors ->
            if (obj.start && obj.finish && obj.date) {
                if (obj.start.toLocalTime() >= obj.finish.toLocalTime()) errors.rejectValue('finish', 'noMatch')
            }
        }
        duration(nullable: false, blank: true)
    }

    static mapping = {
        version(false)
        Action(cascade: 'all-delete-orphan', lazy: false)
    }
}
