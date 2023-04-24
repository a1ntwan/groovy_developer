package todo.list

import grails.rest.RestfulController


class ActionController extends RestfulController {

    static responseFormats = ['html', 'json']

    ActionController() {
        super(Action)
    }

    TaskService taskService
    ActionService actionService
    static Task task
    static int num


    def create() {
        [task: flash.redirectParams]
    }

    def add() {
        task = taskService.getById(params.task)
        def response = actionService.add(params, task)
        if (!response.isSuccess) {
            flash.redirectParams = response.model
            flash.message = AppUtil.infoMessage(g.message(code: "unable.to.save"), false)
            redirect(controller: "action", action: "add", params: [num: num, task: task])
        } else {
            flash.message = AppUtil.infoMessage(g.message(code: "saved"))
            num = 1
            task = null
            redirect(controller: "task", action: "index")
        }
    }

    def save() {
        def response = actionService.save(params, task, num)
        if (!response.isSuccess) {
            flash.redirectParams = response.model
            flash.message = AppUtil.infoMessage(g.message(code: "unable.to.save"), false)
            chain(controller: "action", action: "create", model: [params: [num: num, task: task, models: flash.redirectParams]])
        } else {
            flash.message = AppUtil.infoMessage(g.message(code: "saved"))
            num = 1
            task = null
            redirect(controller: "task", action: "index")
        }
    }

    def delete(Integer id) {
        def response = actionService.getById(id)
        if (!response){
            flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
            redirect(controller: "task", action: "index")
        } else {
            response = actionService.delete(response)
            if (!response.result){
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.delete"), false)
            } else {
                flash.message = AppUtil.infoMessage(g.message(code: "deleted"))
            }
            if ((response.task.actions) && (response.task.actions.size() > 0)) {
                redirect(controller: "task", action: "details", params: [id: response.task.id])
            } else {
                redirect(controller: "task", action: "delete", params: [id: response.task.id])
            }
        }
    }

    def edit(Integer id) {
        if (flash.redirectParams) {
            [action: flash.redirectParams]
        } else {
            def response = actionService.getById(id)
            if (!response) {
                flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
                redirect(controller: "task", action: "index")
            } else {
                [action: response]
            }
        }
    }

    def update() {
        def response = actionService.getById(params.id)
        if (!response){
            flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
            redirect(controller: "task", action: "index")
        } else {
            response = actionService.update(response, params)
            if (!response.isSuccess){
                flash.redirectParams = response.model
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.update"), false)
                redirect(controller: "action", action: "edit", params: [num: 1])
            } else {
                flash.message = AppUtil.infoMessage(g.message(code: "updated"))
                redirect(controller: "task", action: "details")
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////                         API JSON CONTROLLERS                                      ///////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    def addJSON() {
        task = taskService.getById(request.JSON.task)
        def response = actionService.add(request.JSON, task)
        if (!response.isSuccess) {
            flash.redirectParams = response.model
            flash.message = AppUtil.infoMessage(g.message(code: "unable.to.save"), false)
            redirect(controller: "action", action: "add", params: [num: num, task: task])
        } else {
            flash.message = AppUtil.infoMessage(g.message(code: "saved"))
            num = 1
            task = null
            redirect(controller: "task", action: "index")
        }
    }

    def updateJSON() {
        def response = actionService.getById(request.JSON.action)
        if (!response){
            flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
            redirect(controller: "task", action: "index")
        } else {
            response = actionService.update(response, request.JSON)
            if (!response.isSuccess){
                flash.redirectParams = response.model
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.update"), false)
                redirect(controller: "action", action: "edit", params: [num: 1])
            } else {
                flash.message = AppUtil.infoMessage(g.message(code: "updated"))
                redirect(controller: "task", action: "details")
            }
        }
    }
}
