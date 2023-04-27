package todo.list.pages

import geb.Page
import geb.Module

class HomePage extends Page {

    static url = "/"

    static at = {
        $("div.card-header").text().split("\n")[0] == "List Tasks"
    }


    static content = {
        searchHeader { $("h3") }
        searchInput { $("input", id: "colValue") }
        searchButton { $("button.btn") }

        reloadButton { $("a.btn.btn-primary") }
        createButton (to: TaskPage) { $("a.btn.btn-success") }

        tableOfTasks { $("table.table.table-bordered") }
        tableRows { tableOfTasks.$('tbody > tr').moduleList(TableRows) }

        confirmWindow { $("div.jconfirm-content") }
        confirmButton { $(".jconfirm-buttons > button:nth-child(1)") }
    }
}

class TableRows extends Module {
    static content = {
        cell { $("td") }
        name { cell[0].text() }
        start { cell[1].text() }
        finish { cell[2].text() }
        deleteButton { cell[3].$("a.btn.btn-secondary.delete-confirmation") }
        detailsButton { cell[3].$("a.btn-secondary:nth-child(2)") }
        editButton { cell[3].$("a.btn:nth-child(3)") }
    }
}