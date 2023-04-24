package todo.list

import java.time.LocalDateTime

class Action {
    String activity
    Task task
    LocalDateTime start
    LocalDateTime finish
    long duration

    static belongsTo = [task: Task]

    static constraints = {
        activity(nullable: false, blank: true)
        task(nullable: false, blank: true)
        start(nullable: false, blank: true)
        start validator: { val, obj, errors ->
            if (val == null) errors.rejectValue('start', 'noMatch')
            else if (val.isBefore(obj.task.start)) errors.rejectValue('start', 'noMatch')
        }
        finish(nullable: false, blank: true)
        finish validator: { val, obj, errors ->
            if (val == null || val.isEqual(obj.start)) errors.rejectValue('finish', 'noEquals')
            else if (val.isAfter(obj.task.finish)) errors.rejectValue('finish', 'noMatch')
        }
        duration(nullable: false, blank: true)
    }

    static mapping = {
        version(false)
    }
}
