package todo.list

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import com.google.common.base.Strings

@Transactional
class TaskService {

    private def interactWithTask(Task task, params) {
        task.name = Strings.emptyToNull(params.name)
        task.date = DateTimeChecker.checkTaskDate(params.date)
        task.start = DateTimeChecker.checkStartFinishTaskDate(params.date, params.start, params.finish)[0]
        task.finish = DateTimeChecker.checkStartFinishTaskDate(params.date, params.start, params.finish)[1]
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
