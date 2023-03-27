package todo.list

import grails.web.servlet.mvc.GrailsParameterMap

class ActionController {

    ActionService actionService
    long taskId = 0

    def create() {
        println(params.id)
        this.taskId = Long.valueOf(params.id)
        [action: flash.redirectParams]
    }

    def save() {
        params['taskId'] = this.taskId
        def response = actionService.save(params)
        if (!response.isSuccess) {
            flash.redirectParams = response.model
            flash.message = AppUtil.infoMessage(g.message(code: "unable.to.save"), false)
            redirect(controller: "action", action: "create")
        } else {
            flash.message = AppUtil.infoMessage(g.message(code: "saved"))
            redirect(controller: "task", action: "index")
        }
    }

}
