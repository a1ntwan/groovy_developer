import java.time.LocalDateTime
import java.time.format.DateTimeParseException

class TodoList {
    static void main(String[] args) {

        TaskDB db = new TaskDB()
        ActionDB adb = new ActionDB()

        while (true) {
            Scanner basicMenuScanner = new Scanner(System.in)
            println()
            println("Please choose your option: \n" +
                    "1. Create task \n" +
                    "2. Delete task \n" +
                    "3. Change task \n" +
                    "4. Get tasks for a date \n" +
                    "5. Get busy time for a date \n" +
                    "6. Get number of tasks for a date \n"
            )
            String basicMenu = basicMenuScanner.nextLine()

            try {
                switch (basicMenu) {
                    case "1":
                        Task task = new Task()
                        task.with {
                            Scanner taskNameScanner = new Scanner(System.in)
                            println("Please enter your task name: ")
                            String taskName = taskNameScanner.nextLine()
                            name = taskName

                            Scanner taskDateScanner = new Scanner(System.in)
                            println("Please enter your task date in format dd.MM.yyyy : ")
                            String taskDate = taskDateScanner.nextLine()

                            Scanner timeStartScanner = new Scanner(System.in)
                            println("Please enter the start time for your task in format HH:mm : ")
                            String timeStart = timeStartScanner.nextLine()
                            String dateTimeStart = "${taskDate} ${timeStart}"
                            LocalDateTime parsedStart = LocalDateTime.parse(dateTimeStart, 'dd.MM.yyyy HH:mm')
                            start = checkTaskDate(parsedStart)

                            Scanner timeFinishScanner = new Scanner(System.in)
                            println("Please enter the finish time for your task in format HH:mm : ")
                            String timeFinish = timeFinishScanner.nextLine()
                            String dateTimeFinish = "${taskDate} ${timeFinish}"
                            LocalDateTime parsedFinish = LocalDateTime.parse(dateTimeFinish, 'dd.MM.yyyy HH:mm')
                            checkFinishTaskDate(parsedStart, parsedFinish)
                            finish = checkTaskDate(parsedFinish)

                            Scanner numActionsScanner = new Scanner(System.in)
                            println("Please enter the number of your actions: ")
                            int numActions = numActionsScanner.nextLine().toInteger()
                            List<Action> todo = new ArrayList()
                            LocalDateTime actionStart = parsedStart
                            numActions.times {
                                Action action = new Action()
                                action.with {
                                    Scanner actionNameScanner = new Scanner(System.in)
                                    println("Please enter the name of your action: ")
                                    String actionName = actionNameScanner.nextLine()
                                    activity = actionName

                                    Scanner actionDurationScanner = new Scanner(System.in)
                                    println("Please enter the duration of your action: ")
                                    String actionDuration = actionDurationScanner.nextLine()
                                    duration = InputParser.convertToSec(actionDuration)
                                    start = actionStart
                                    actionStart = actionStart.plusSeconds(InputParser.convertToSec(actionDuration.toString()))
                                }
                                todo << action
                            }
                            checkActionsDuration(todo)
                            actions = todo
                        }
                        db + task
                        adb + task
                        break

                    case "2":
                        Scanner getTaskNameScanner = new Scanner(System.in)
                        println("Please enter the name of the task you want to delete: ")
                        String getTaskName = getTaskNameScanner.nextLine()
                        db - getTaskName
                        adb - getTaskName
                        break

                    case "3":
                        Scanner changeTaskMenuScanner = new Scanner(System.in)
                        println("Please choose your option: \n" +
                                "1. Delete action \n" +
                                "2. Add action \n" +
                                "3. Back \n")
                        String changeTaskMenu = changeTaskMenuScanner.nextLine()

                        switch (changeTaskMenu) {

                            case "1":
                                Scanner getTaskNameScanner = new Scanner(System.in)
                                println("Please enter the name of the task you want to edit: ")
                                String getTaskName = getTaskNameScanner.nextLine()
                                Closure<Boolean> taskClosure = { it.name == getTaskName }
                                Task foundTask = db.taskDB.find(taskClosure)

                                Scanner getActionNameScanner = new Scanner(System.in)
                                println("Please enter the name of the action you want to delete: ")
                                String getActionName = getActionNameScanner.nextLine()
                                Closure<Boolean> actionClosure = { it.activity == getActionName }
                                Action foundAction = foundTask.actions.find(actionClosure)
                                foundTask - foundAction
                                adb.minus(getTaskName, getActionName)
                                break

                            case "2":
                                Scanner getTaskNameScanner = new Scanner(System.in)
                                println("Please enter the name of the task you want to edit: ")
                                String getTaskName = getTaskNameScanner.nextLine()
                                Closure<Boolean> taskClosure = { it.name == getTaskName }
                                Task foundTask = db.taskDB.find(taskClosure)
                                Action action = new Action()
                                action.with {
                                    Scanner actionNameScanner = new Scanner(System.in)
                                    println("Please enter the name of your action: ")
                                    String actionName = actionNameScanner.nextLine()
                                    activity = actionName

                                    Scanner actionDurationScanner = new Scanner(System.in)
                                    println("Please enter the duration of your action (example: 1h or 10m or 30s): ")
                                    String actionDuration = actionDurationScanner.nextLine()
                                    duration = InputParser.convertToSec(actionDuration)
                                    start = foundTask.actions[-1].start.plusSeconds(foundTask.actions[-1].duration)
                                }

                                foundTask + action
                                adb.plus(foundTask, action)
                                break

                            case "3":
                                break
                        }
                        break

                    case "4":
                        Scanner tasksForDateScanner = new Scanner(System.in)
                        println("Please enter the date in format dd.MM.YY: ")
                        String tasksForDate = tasksForDateScanner.nextLine()
                        if (db.size() > 0) {
                            println("Your tasks for ${tasksForDate} are: ")
                            db.getTasksByDate(tasksForDate.toString()).each { it ->
                                println(it.name)
                            }
                        } else {
                            println("There are no tasks planned! Please add at least one")
                        }
                        break


                    case "5":
                        Scanner busyTimesForDateScanner = new Scanner(System.in)
                        println("Please enter the date in format dd.MM.YY: ")
                        String busyTimesForDate = busyTimesForDateScanner.nextLine()
                        if (db.size() > 0) {
                            db.getBusyTime(busyTimesForDate)
                        } else {
                            println("There are no tasks planned! Please add at least one")
                        }
                        break

                    case "6":
                        Scanner numOfTasksForDateScanner = new Scanner(System.in)
                        println("Please enter the date in format dd.MM.YY: ")
                        String numOfTasksForDate = numOfTasksForDateScanner.nextLine()
                        if (db.size() > 0) {
                            db.getNumTasksByDate(numOfTasksForDate)
                        } else {
                            println("There are no tasks planned! Please add at least one")
                        }
                        break
                }
            }
            catch (IllegalArgumentException e) {}
            catch (UnsupportedOperationException e) {}
            catch (DateTimeParseException e) {
                println("Wrong date or time format !!!")
            }
            catch (NullPointerException e) {
                println("Wrong task or action name !!!")
            }
        }
    }
}