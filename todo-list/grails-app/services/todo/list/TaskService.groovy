package todo.list

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import com.google.common.base.Strings

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit


@Transactional
class TaskService {

    private def interactWithTask(Task task, params) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern('yyyy-MM-dd')
        DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern('yyyy-MM-dd HH:mm')

        task.name = Strings.emptyToNull(params.name)
        task.date = LocalDate.parse(params.date, dateFormatter)
        task.start = LocalDateTime.parse("${task.date} ${params.start}", dateTimeformatter)
        task.finish = LocalDateTime.parse("${task.date} ${params.finish}", dateTimeformatter)
        task.duration = ChronoUnit.SECONDS.between(task.start, task.finish)
        return task
    }

    private def toLocalDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern('yyyy-MM-dd'))
        } catch (DateTimeParseException e) {
            return dateStr
        }  catch (NullPointerException e) {
            return dateStr
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern('yyyy-MM-dd'))
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
        params?.colValue = toLocalDate(params?.colValue)
        List<Task> taskList = Task.createCriteria().list(params) {
            if (params?.colName && params?.colValue && params?.colValue.getClass() != LocalDate) {
                like(params.colName, "%" + params.colValue + "%")
            }
            if (params?.colName && params?.colValue) {
                eq("date", params?.colValue)
                params?.byDate = true
            }
            if (!params.sort) {
                order("id", "desc")
            }
        }
        return [list: taskList, count: taskList.totalCount, byDate: params?.byDate]
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
