package todo.list

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class ActionService {

    def save(GrailsParameterMap params) {
        params.name.eachWithIndex { a, i ->
            Action action = new Action()
            action.activity = params.name[i]
            action.duration = params.duration[i].toInteger()
            action.taskId = params.taskId
            def response = AppUtil.saveResponse(false, action)
            if (action.validate()) {
                action.save(flush: true)
                if (!action.hasErrors()) {
                    response.isSuccess = true
                } else {
                    response.isSuccess = false
                    return response
                }
            }
        }
        return ['isSuccess': true]
    }


    def update(Action action, GrailsParameterMap params) {
        action.properties = params
        def response = AppUtil.saveResponse(false, action)
        if (action.validate()) {
            action.save(flush: true)
            if (!action.hasErrors()){
                response.isSuccess = true
            }
        }
        return response
    }


    def getById(Serializable id) {
        return Action.get(id)
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
            return false
        }
        return true
    }
}
