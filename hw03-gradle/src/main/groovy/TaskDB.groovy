import java.time.LocalDate

class TaskDB {
    List<Task> taskDB

    TaskDB() {
        this.taskDB = []
    }

    TaskDB plus(Task task) {
        if (this.taskDB.size() > 0) {
            this.taskDB.each { it ->
                if (
                       (task.start == it.start && task.finish == it.finish) ||
                       (task.start.isAfter(it.start) && task.finish.isBefore(it.finish)) ||
                       (task.start.isAfter(it.start) && task.start.isBefore(it.finish) && task.finish.isAfter(it.finish)) ||
                       (task.start.isBefore(it.start) && task.finish.isAfter(it.start) && task.finish.isBefore(it.finish))
                   ){
                    println("Your task is crossed by another one: ${it.name} ${it.start.toLocalTime()}-${it.finish.toLocalTime()}")
                    throw new UnsupportedOperationException()
                }
            }
        }
        this.taskDB << task
        println("Task: ${task.name} ${task.start.toLocalDate()} ${task.start.toLocalTime()}-${task.finish.toLocalTime()} has been added")
        this.taskDB.sort { a, b -> a.start <=> b.start }
        this
    }

    TaskDB minus(String name) {
        Closure<Boolean> closure = { it.name == name }
        this.taskDB -= this.taskDB.find(closure)
        println("Task ${name} has been deleted !")
        this
    }

    int size() {
        return this.taskDB.size()
    }

    List<Task> getTasksByDate(String input) {
        LocalDate date = LocalDate.parse(input, 'dd.MM.yyyy')
        Map<LocalDate, List<Task>> tasksByStartTime = this.taskDB.groupBy({ task -> task.start.toLocalDate() })
        if (tasksByStartTime[date] != null) {
            return tasksByStartTime[date]
        } else {
            println('Nothing found !')
            return []
        }
    }

    void getNumTasksByDate(String input) {
        println(getTasksByDate(input).size())
    }

    void getBusyTime(String input) {
        int secondsDelta = 0
        getTasksByDate(input).each {
            secondsDelta += it.finish.toLocalTime().toSecondOfDay() - it.start.toLocalTime().toSecondOfDay()
            println("Your busy time for ${input} is since ${it.start.toLocalTime()} to ${it.finish.toLocalTime()}. Task: ${it.name}")
        }
        println("Total busy time for ${input} is: hours: ${secondsDelta / 3600} Minutes: ${secondsDelta % 3600 / 60}")
    }

}