package todo.list.pages

import geb.Module
import geb.Page
import geb.module.TimeInput

class ActionPage extends Page {
    static url = "/action/create?num=1"

    static at = {
        $("div.card-header").text().split("\n")[0] == "Options"
    }

    static content = {
        actionPageForm { module ActionPageFormModule }
    }
}

class ActionPageFormModule extends Module {

    static content = {
        actionName { $("input", id: "activity") }
        actionStart { $("input", id: "start").module(TimeInput) }
        actionFinish { $("input", id: "finish").module(TimeInput) }

        saveButton { $("input", id: "save") }
        addButton { $("input", id: "add") }
    }

    void  fillEntries(activity, start, finish) {
        actionName.value(activity)
        actionStart.value(start)
        actionFinish.value(finish)
    }

    void saveForm(){
        saveButton.click()
    }

    void addForm(){
        addButton.click()
    }
}
