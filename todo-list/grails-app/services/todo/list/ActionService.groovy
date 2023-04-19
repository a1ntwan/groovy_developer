package todo.list

import com.google.common.base.Strings
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@Transactional
class ActionService {

    private def interactWithAction(Action action, String activity, String start, String finish, Task task) {
        DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')

        action.activity = Strings.emptyToNull(activity)
        action.task = task
        if (start == '') {
            action.start = null
        } else {
            action.start = LocalDateTime.parse("${task.date} ${start}", dateTimeformatter)
        }
        if (finish == '') {
            action.finish = null
        } else {
            action.finish = LocalDateTime.parse("${task.date} ${finish}", dateTimeformatter)
            action.duration = ChronoUnit.SECONDS.between(action.start, action.finish)
        }
        return action
    }

    private def addTaskMultipleActions(GrailsParameterMap params, Task task, int num) {

        def range = 0..num-1
        def responses = []

        task.actions = []

        range.each{ i ->
            Action action = new Action()
            action = interactWithAction(action, params.activity[i], params.start[i], params.finish[i], task)
            def response = AppUtil.saveResponse(false, action)
            if (action.validate()) {
                if (!action.hasErrors()) {
                    task.addToActions(action)
                    if (!getEvent(action.start)) {
                        Event event = new Event(startTime: action.start)
                        event.save()
                    }
                    response.isSuccess = true
                }
            }

            responses << response
        }
        return [task, responses]
    }

    private def addTaskSingleAction(GrailsParameterMap params, Task task) {
        def responses = []

        task.actions = []

        Action action = new Action()
        action = interactWithAction(action, params.activity, params.start, params.finish, task)
        def response = AppUtil.saveResponse(false, action)
        if (action.validate()) {
            if (!action.hasErrors()) {
                task.addToActions(action)
                if (!getEvent(action.start)) {
                    Event event = new Event(startTime: action.start)
                    event.save()
                }
                response.isSuccess = true
            }
        }

        responses << response

        return [task, responses]
    }

    def save(params, Task task, int num) {

        def result = null

        if (params.getClass() == GrailsParameterMap) {
            if (num > 1) {
                result = addTaskMultipleActions(params, task, num)
            } else {
                result = addTaskSingleAction(params, task)
            }
        } else {
            result = addTaskActionsJSON(params, task)
        }

        def response = AppUtil.saveResponse(false, result[1])
//        if ((result[0].actions) && (result[0].actions.size() == num) && (result[0].duration >= result[0].actions*.duration.sum())) {
        if ((result[0].actions) && (result[0].actions.size() == num) && (result[0].duration >= response.model*.model*.duration.sum())) {
        result[0].save(flush: true)
            response.isSuccess = true
            return response
        }
        return response
    }

    def add(params, Task task) {
        Action action = new Action()
        action = interactWithAction(action, params.activity, params.start, params.finish, task)

        def response = AppUtil.saveResponse(false, action)
        if (action.validate()) {
            action.save(flush: true)
            if (!action.hasErrors()){
                if (!getEvent(action.start)) {
                    Event event = new Event(startTime: action.start)
                    event.save()
                }
                response.isSuccess = true
            }
        }
        return response
    }

    def update(Action action, params) {
        Event oldEvent = getEvent(action.start)
        oldEvent.delete()
        action = interactWithAction(action, params.activity, params.start, params.finish, action.task)
        def response = AppUtil.saveResponse(false, action)
        if (action.validate()) {
            action.save(flush: true)
            if (!action.hasErrors()){
                if (!getEvent(action.start)) {
                    Event event = new Event(startTime: action.start)
                    event.save()
                }
                response.isSuccess = true
            }
        }
        return response
    }


    def getById(Serializable id) {
        return Action.get(id)
    }

    def getEvent(LocalDateTime time) {
        return Event.find { startTime == time }
    }

    def list(GrailsParameterMap params) {
        params.max = params.max ?: GlobalConfig.itemsPerPage()
        List<Action> actionList = Action.createCriteria().list(params) {
            if (params?.colName && params?.colValue) {
                like(params.colName, "%" + params.colValue + "%")
            }
            if (!params.sort) {
                order("id", "desc")
            }
        }
        return [list: actionList, count: actionList.totalCount]
    }


    def delete(Action action) {
        try {
            action.delete(flush: true)
        } catch (Exception e) {
            println(e.getMessage())
            return [result: false, task: null]
        }
        return [result: true, task: action.task]
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////                         API JSON SERVICES                                         ///////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private def addTaskActionsJSON(params, Task task) {
        def responses = []

        task.actions = []

        params.actions.each{ i ->
            Action action = new Action()
            action = interactWithAction(action, i.activity, i.start, i.finish, task)
            def response = AppUtil.saveResponse(false, action)
            if (action.validate()) {
                if (!action.hasErrors()) {
                    task.addToActions(action)
                    if (!getEvent(action.start)) {
                        Event event = new Event(startTime: action.start)
                        event.save()
                    }
                    response.isSuccess = true
                }
            }
            responses << response
        }
        return [task, responses]
    }
}
