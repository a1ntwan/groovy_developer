import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Event {
    private LocalDateTime currentDT

    Event() {
        this.currentDT = LocalDateTime.now()
    }

    private TimerTask setTimer(Task task, Action action) {
        Timer timer = new Timer()
        long millis = ChronoUnit.MILLIS.between(this.currentDT, action.start)
        if (millis > 2147483647) {
            println("You can plan only up to 24 days !")
            throw new UnsupportedOperationException()
        }
        TimerTask eventTimer = timer.runAfter(millis as int) {
            println("NOTIFICATION: It's time for task: ${task.name} action: ${action.activity} !!!")
        }
        println "Current date is ${new Date()}. Task ${task.name} with action ${action.activity} is planned at ${new Date(eventTimer.scheduledExecutionTime())}."
        return eventTimer
    }

    ArrayList<Map> createEvent(Task task) {
        List lst = new ArrayList()
        task.actions.each {action ->
            TimerTask eventTimer = setTimer(task, action)
            HashMap eventTimerMap = new HashMap()
            eventTimerMap.put(eventTimer, action.activity)
            HashMap taskMap = new HashMap()
            taskMap.put(task, eventTimerMap)
            lst << taskMap
        }
        return lst
    }

    Map createEvent(Task task, Action action) {
        TimerTask eventTimer = setTimer(task, action)
        HashMap eventTimerMap = new HashMap()
        eventTimerMap.put(eventTimer, action.activity)
        HashMap taskMap = new HashMap()
        taskMap.put(task, eventTimerMap)
        return taskMap
    }
}