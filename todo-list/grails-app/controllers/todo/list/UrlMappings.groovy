package todo.list

class UrlMappings {

    static mappings = {

        "/tasks"(resources: "task") {
            "/actions"(resources: "action")
        }

        "/api/actions/add"(controller: "action", action: "addJSON", method: "POST")
        "/api/actions/update"(controller: "action", action: "updateJSON", method: "PUT")

        "/api/tasks/add"(controller: "task", action: "saveJSON", method: "POST")
        "/api/tasks/update"(controller: "task", action: "updateJSON", method: "PUT")

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
