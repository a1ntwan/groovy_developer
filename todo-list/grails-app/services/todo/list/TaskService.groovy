package todo.list

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.google.common.base.Strings

@Transactional
class TaskService {

    private def checkTaskDate(inputDate) {
        if (inputDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern('yyyy-MM-dd')
            LocalDate ldt = LocalDate.parse(inputDate, formatter)
            if (ldt.isAfter(LocalDate.now())) {
                return ldt
            }
        }
        return null
    }

    private def checkStartFinishTaskDate(inputDate, inputStart, inputFinish) {
        if ((inputStart) && (inputFinish)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')
            LocalDateTime start = LocalDateTime.parse("${inputDate} ${inputStart}", formatter)
            LocalDateTime finish = LocalDateTime.parse("${inputDate} ${inputFinish}", formatter)
            if (start.toLocalTime().isBefore(finish.toLocalTime())) {
                return [start, finish]
            }
        }
        return [null, null]
    }

    private def interactWithTask(Task task, params) {
        task.name = Strings.emptyToNull(params.name)
        task.date = checkTaskDate(params.date)
        task.start = checkStartFinishTaskDate(params.date, params.start, params.finish)[0]
        task.finish = checkStartFinishTaskDate(params.date, params.start, params.finish)[1]
        return task
    }
    
    def save(params) {
        Task task = new Task()
        task = interactWithTask(task, params)
        def response = AppUtil.saveResponse(false, task)
        if (task.validate()) {
            if (!task.hasErrors()){
                response.isSuccess = true
                response.task = task
            }
        }
        return response
    }

    def update(Task task, params) {
        task = interactWithTask(task, params)
        def response = AppUtil.saveResponse(false, task)
        if (task.validate()) {
            task.save(flush: true)
            if (!task.hasErrors()){
                response.isSuccess = true
            }
        }
        return response
    }


    def getById(Serializable id) {
        return Task.get(id)
    }

    def list(GrailsParameterMap params) {
        params.max = 10
        List<Task> taskList = Task.createCriteria().list(params) {
            if (params?.colName && params?.colValue) {
                like(params.colName, "%" + params.colValue + "%")
            }
            if (!params.sort) {
                order("id", "desc")
            }
        }
        return [list: taskList, count: taskList.totalCount]
    }


    def delete(Task task) {
        try {
            task.delete(flush: true)
        } catch (Exception e) {
            println(e.getMessage())
            return false
        }
        return true
    }
}
