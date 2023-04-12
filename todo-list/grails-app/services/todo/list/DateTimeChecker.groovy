package todo.list

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeChecker {
    static def checkTaskDate(inputDate) {
        if (inputDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern('yyyy-MM-dd')
            LocalDate ldt = LocalDate.parse(inputDate, formatter)
            if (ldt.isAfter(LocalDate.now())) {
                return ldt
            }
        }
        return null
    }

    static def checkStartFinishTaskDate(inputDate, inputStart, inputFinish) {
        if ((inputStart) && (inputFinish) && (inputDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')
            LocalDateTime start = LocalDateTime.parse("${inputDate} ${inputStart}", formatter)
            LocalDateTime finish = LocalDateTime.parse("${inputDate} ${inputFinish}", formatter)
            if (start.toLocalTime().isBefore(finish.toLocalTime())) {
                return [start, finish]
            }
        }
        return [null, null]
    }

    static def checkTaskStartFinishTaskDate(date, taskStart, taskFinish, inputStart, inputFinish) {
        def startFinish = checkStartFinishTaskDate(date, inputStart, inputFinish)
        if (startFinish != [null, null]) {
            if ((taskStart.toLocalTime() <= startFinish[0].toLocalTime()) && (taskFinish.toLocalTime() >= startFinish[1].toLocalTime())) {
                return startFinish
            }
        }
        return [null, null]
    }
}