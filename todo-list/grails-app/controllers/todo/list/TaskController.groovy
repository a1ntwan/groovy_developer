package todo.list

import grails.rest.RestfulController

class TaskController extends RestfulController {

    static responseFormats = ['html', 'json']

    TaskController() {
        super(Task)
    }

    TaskService taskService
    ActionService actionService

    def index() {
        def response = taskService.list(params)
        [taskList: response.list, total: response.count]
    }

    def details(Integer id) {
        def response = taskService.getById(id)
        if (!response){
            redirect(controller: "task", action: "index")
        } else {
            [task: response]
        }
    }

    def create() {
        [task: flash.redirectParams]
    }

    def save() {
        def response = taskService.save(params)
        if (!response.isSuccess) {
            flash.redirectParams = response.model
            flash.message = AppUtil.infoMessage(g.message(code: "unable.to.save"), false)
            redirect(controller: "task", action: "create")
        } else {
            flash.message = AppUtil.infoMessage(g.message(code: "saved"))
            ActionController.task = response.task
            if (params.num) {
                ActionController.num = params.num.toInteger()
            } else {
                params.num = 1
                ActionController.num = params.num
            }
            redirect(controller: "action", action: "create", params: [num: params.num])
        }
    }

    def edit(Integer id) {
        if (flash.redirectParams) {
            [task: flash.redirectParams]
        } else {
            def response = taskService.getById(id)
            if (!response) {
                flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
                redirect(controller: "task", action: "index")
            } else {
                [task: response]
            }
        }
    }


    def update() {
        def response = taskService.getById(params.id)
        if (!response){
            flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
            redirect(controller: "task", action: "index")
        } else {
            response = taskService.update(response, params)
            if (!response.isSuccess){
                flash.redirectParams = response.model
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.update"), false)
                redirect(controller: "task", action: "edit")
            } else {
                flash.message = AppUtil.infoMessage(g.message(code: "updated"))
                redirect(controller: "task", action: "index")
            }
        }
    }

    def delete(Integer id) {
        def response = taskService.getById(id)
        if (!response){
            flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
            redirect(controller: "task", action: "index")
        } else {
            response = taskService.delete(response)
            if (!response){
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.delete"), false)
            } else {
                flash.message = AppUtil.infoMessage(g.message(code: "deleted"))
            }
            redirect(controller: "task", action: "index")
        }
    }

    def add() {
        def response = taskService.save(params)
        if (!response.isSuccess) {
            flash.redirectParams = response.model
            flash.message = AppUtil.infoMessage(g.message(code: "unable.to.save"), false)
            redirect(controller: "task", action: "create")
        } else {
            flash.message = AppUtil.infoMessage(g.message(code: "saved"))
            ActionController.task = response.task
            ActionController.num = params.num.toInteger()
            redirect(controller: "action", action: "create", params: [num: params.num])
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////                         API JSON CONTROLLERS                                      ///////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    def saveJSON() {
        def responseTask = taskService.save(request.JSON)
        if (!responseTask.isSuccess) {
            flash.redirectParams = responseTask.model
            flash.message = AppUtil.infoMessage(g.message(code: "unable.to.save"), false)
            redirect(controller: "task", action: "create")
        } else {
            flash.message = AppUtil.infoMessage(g.message(code: "saved"))
            Task task = responseTask.task
            int num = request.JSON.actions.size()
            def responseActions = actionService.save(request.JSON, task, num)
            if (!responseActions.isSuccess) {
                flash.redirectParams = responseActions.model
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.save"), false)
                chain(controller: "action", action: "create", model: [params: [num: num, task: task, models: flash.redirectParams]])
            } else {
                flash.message = AppUtil.infoMessage(g.message(code: "saved"))
                redirect(controller: "task", action: "index")
            }
        }
    }

    def updateJSON() {
        def response = taskService.getById(request.JSON.task)
        if (!response){
            flash.message = AppUtil.infoMessage(g.message(code: "invalid.entity"), false)
            redirect(controller: "task", action: "index")
        } else {
            response = taskService.update(response, request.JSON)
            if (!response.isSuccess){
                flash.redirectParams = response.model
                flash.message = AppUtil.infoMessage(g.message(code: "unable.to.update"), false)
                redirect(controller: "task", action: "edit")
            } else {
                flash.message = AppUtil.infoMessage(g.message(code: "updated"))
                redirect(controller: "task", action: "index")
            }
        }
    }
}
