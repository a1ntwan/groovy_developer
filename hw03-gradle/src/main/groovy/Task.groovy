import java.time.LocalDateTime


class Task {
    LocalDateTime start
    LocalDateTime finish
    String name
    List<Action> actions

    LocalDateTime checkTaskDate(LocalDateTime date) {
        if ((!date.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) &&
        (date.toLocalTime().isAfter(LocalDateTime.now().toLocalTime()))) {
            return date
        } else {
            println("The date for your task ${this.name} is too old !!!")
            throw new IllegalArgumentException()
        }
    }

    LocalDateTime checkFinishTaskDate(LocalDateTime start, LocalDateTime finish) {
        if (start.toLocalTime().isBefore(finish.toLocalTime())) {
            return finish
        } else {
            println("Your finish datetime can't be before the start for your task ${this.name} !!!")
            throw new IllegalArgumentException()
        }
    }

    void checkActionsDuration(List<Action> list){
        int totalTime = 0
        list.each {
            totalTime += it.duration
        }
        if (totalTime > (finish.toLocalTime().toSecondOfDay() - start.toLocalTime().toSecondOfDay())) {
            println("Actions can't overlap your ${this.name} task time!")
            throw new UnsupportedOperationException()
        }
    }

    Task minus(Action action) {
        this.actions -= action
        this
    }

    Task plus(Action action) {

        List<Action> tempActions = []

        tempActions += this.actions
        tempActions << action
        checkActionsDuration(tempActions)

        tempActions = []

        if (this.actions.size() == 0) {
            action.start = this.start
            this.actions << action
            println("Your action ${action.activity} has been added as the first of all actions for task ${this.name}")
            this
        } else if (this.actions.size() == 1) {
            if (this.actions[0].start.toLocalTime() == this.start.toLocalTime()) {
                action.start = this.finish.minusSeconds(action.duration)
                this.actions << action
                println("Your action ${action.activity} has been added to the end of all actions for task ${this.name}")
                this
            } else {
                action.start = this.start
                this.actions[0] = this.actions[1]
                this.actions[0] = action
                println("Your action ${action.activity} has been added as the first of all actions for task ${this.name}")
                this
            }

        } else if (this.actions.size() >= 2) {
            for (int i = 0; i < this.actions.size() - 1; i++) {
                if ((this.finish.toLocalTime().toSecondOfDay() - this.actions[-1].start.toLocalTime().plusSeconds(this.actions[-1].duration).toSecondOfDay()) >= action.duration) {
                    tempActions += this.actions
                    action.start = this.finish.minusSeconds(action.duration)
                    tempActions << action
                    println("Your action ${action.activity} has been added to the end of all actions for task ${this.name}")
                    break
                } else if (this.start.toLocalTime() == this.actions[0].start.toLocalTime()) {
                    if ((this.actions[i + 1].start.toLocalTime().toSecondOfDay() - this.actions[i].start.toLocalTime().toSecondOfDay()) > action.duration) {
                        tempActions += this.actions[0..i]
                        action.start = this.actions[i].start + this.actions[i].duration
                        tempActions << action
                        tempActions += this.actions[i + 1..-1]
                        println("Your action ${action.activity} has been added between ${this.actions[i].activity} and ${this.actions[i + 1].activity} for task ${this.name}")
                        break
                    }
                } else {
                    action.start = this.start
                    tempActions << action
                    tempActions += this.actions
                    println("Your action ${action.activity} has been added as the first of all actions for task ${this.name}")
                    break
                }
            }
            this.actions = tempActions
            this
        }
    }
}