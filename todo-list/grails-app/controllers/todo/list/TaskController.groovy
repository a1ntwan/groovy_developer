package todo.list

class TaskController {

    TaskService taskService

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
            redirect(controller: "action", action: "create", params: [id: response.id, num: params.num])
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
}
