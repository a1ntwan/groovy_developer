package todo.list

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Transactional
class TaskService {
    
    def save(GrailsParameterMap params) {
        Task task = new Task()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm')
        task.name = params.name
        task.start = LocalDateTime.parse("${params.date} ${params.start}", formatter)
        task.finish = LocalDateTime.parse("${params.date} ${params.finish}", formatter)
        def response = AppUtil.saveResponse(false, task)
        if (task.validate()) {
            task.save(flush: true)
            if (!task.hasErrors()){
                response.isSuccess = true
                response.id = task.id
            }
        }
        return response
    }


    def update(Task task, GrailsParameterMap params) {
        task.properties = params
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
