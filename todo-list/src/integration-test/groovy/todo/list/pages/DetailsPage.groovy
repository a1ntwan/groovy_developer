package todo.list.pages

import geb.Module
import geb.Page
import org.openqa.selenium.By

class DetailsPage extends Page {
    static at = {
        $("div.card-header").text().split("\n")[0] == "Details Tasks"
    }

    static content = {
        detailsTable { $("table") }
        taskTableRows { detailsTable.$('tbody').moduleList(TaskTableRows) }
        addActionToTask { $("a.btn.btn-secondary")[0] }
    }

}

class TaskTableRows extends Module {
    static content = {
        row { $("tr") }
        taskName { row[0].$("td")[0].text() }
        taskStart { row[1].$("td")[0].text() }
        taskFinish { row[2].$("td")[0].text() }
    }
}
