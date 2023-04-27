package todo.list.pages

import geb.Page
import geb.Module
import geb.module.DateInput
import geb.module.NumberInput
import geb.module.TimeInput


class TaskPage extends Page {
    static url = "/tasks/create"

    static at = {
        $("div.card-header").text().split("\n")[0].contains("Tasks")
    }

    static content = {
        taskPageForm { module TaskPageFormModule }
    }
}

class TaskPageFormModule extends Module {

    static content = {
        taskName { $("input", id: "name") }
        taskDate { $("input", id: "date").module(DateInput) }
        taskStart { $("input", id: "start").module(TimeInput) }
        taskFinish { $("input", id: "finish").module(TimeInput) }
        taskActionsNum { $("input", id: "num").module(NumberInput) }

        saveButton { $("input", id: "save") }
        updateButton { $("input", id: "update") }
    }

    void  fillEntries(name, date, start, finish, num) {
        taskName.value(name)
        taskDate.value(date)
        taskStart.value(start)
        taskFinish.value(finish)
        taskActionsNum.value(num)
    }

    void saveForm(){
        saveButton.click()
    }

    void updateForm(){
        updateButton.click()
    }

}
