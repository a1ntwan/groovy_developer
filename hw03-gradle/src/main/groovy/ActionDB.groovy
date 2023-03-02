class ActionDB {
    List<Map> actionDB

    ActionDB() {
        this.actionDB = []
    }

    ActionDB plus(Task task) {
        Event event = new Event()
        this.actionDB += event.createEvent(task)
        this
    }

    ActionDB plus(Task task, Action action) {
        Event event = new Event()
        this.actionDB << event.createEvent(task, action)
        this
    }

    ActionDB minus(String taskName) {
        this.actionDB.each { it ->
            it.each {task, actions ->
                if (task.name == taskName) {
                    actions.each { event, action ->
                        event.cancel()
                        println("Event for task ${task.name} action ${action} has been cancelled !")
                        this.actionDB -= it
                    }
                }
            }
        }
        this
    }

    ActionDB minus(String taskName, String actionName) {
        this.actionDB.each { it ->
            it.each {task, actions ->
                if (task.name == taskName) {
                    actions.each { event, action ->
                        if (action == actionName) {
                            event.cancel()
                            println("Event for task ${task.name} action ${action} has been cancelled !")
                            this.actionDB -= it
                        }
                    }
                }
            }
        }
        this
    }
}