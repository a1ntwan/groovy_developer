package todo.list

class UrlMappings {

    static mappings = {

        "/tasks"(resources: "task") {
            "/actions"(resources: "action")
        }

        "/events"(resources: "event")

        "/api/actions/add"(controller: "action", action: "addJSON", method: "POST")
        "/api/actions/update"(controller: "action", action: "updateJSON", method: "PUT")

        "/api/tasks/add"(controller: "task", action: "saveJSON", method: "POST")
        "/api/tasks/update"(controller: "task", action: "updateJSON", method: "PUT")

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }


//        TASKS:
//
//        curl -X POST 'http://127.0.0.1:8080/api/tasks/add' -H "Content-Type: application/json" -d '{"name":"second","date":"2023-10-02","start":"08:00","finish":"09:00", actions: [{"activity":"hello","start":"08:00","finish":"08:30"}, {"activity":"world","start":"08:30","finish":"09:00"}]}'
//
//        curl -X PUT 'http://127.0.0.1:8080/api/tasks/update' -H "Content-Type: application/json" -d '{"task":"2","name":"newsecond","date":"2023-10-02","start":"08:00","finish":"09:00"}'
//
//        curl 'http://127.0.0.1:8080/tasks/1' -H "Accept: application/json"
//
//        curl -X DELETE 'http://127.0.0.1:8080/tasks/1'
//
//
//
//        ACTIONS:
//
//        curl -X POST 'http://127.0.0.1:8080/api/actions/add' -H "Content-Type: application/json" -d '{"task":"1","activity":"activity","start":"10:00","finish":"11:00"}'
//
//        curl 'http://127.0.0.1:8080/tasks/1/actions/3' -H "Accept: application/json"
//
//        curl -X DELETE 'http://127.0.0.1:8080/tasks/1/actions/1'
//
//        curl -X PUT 'http://127.0.0.1:8080/api/actions/update' -H "Content-Type: application/json" -d '{"action":"3","activity":"new","start":"10:00","finish":"11:00"}'
//
//        EVENTS:
//
//        curl 'http://127.0.0.1:8080/events' -H "Accept: application/json"

        "/"(view:"/index")
//        "/"(controller: "task", action: "index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
