package todo.list

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BootStrap {

    def init = { servletContext ->
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')
        DateTimeFormatter formatterD = DateTimeFormatter.ofPattern('yyyy-MM-dd')
        Action one = new Action(activity: "first", start: LocalDateTime.parse("2023-12-02 10:00", formatter), finish: LocalDateTime.parse("2023-12-02 10:30", formatter))
        Action two = new Action(activity: "sec", start: LocalDateTime.parse("2023-12-02 10:30", formatter), finish: LocalDateTime.parse("2023-12-02 11:00", formatter))
        Event onee = new Event(startTime: one.start).save()
        Event twoo = new Event(startTime: two.start).save()
        Task task = new Task(name: "task", date: LocalDate.parse("2023-12-02", formatterD), start: LocalDateTime.parse("2023-12-02 10:00", formatter), finish: LocalDateTime.parse("2023-12-02 11:00", formatter)).addToActions(one).addToActions(two)
        task.save()
    }
    def destroy = {
    }
}
