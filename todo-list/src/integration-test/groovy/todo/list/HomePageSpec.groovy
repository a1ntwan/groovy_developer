package todo.list

import geb.spock.GebSpec
import grails.testing.mixin.integration.Integration
import todo.list.pages.*



@SuppressWarnings('JUnitPublicNonTestMethod')
@SuppressWarnings('MethodName')
@Integration
class HomePageSpec extends GebSpec {


    void 'Home page'() {
        given:
        System.setProperty "geb.env", "firefox"

        when:
        to HomePage

        then:
        at HomePage
    }

    void 'Create task valid'() {
        given:
        System.setProperty "geb.env", "firefox"
        to HomePage
        def size =  tableRows.size()
        createButton.click()

        and:
        at TaskPage
        page.taskPageForm.fillEntries("validTestTask1", "2024-12-05", "09:00", "11:00", 1)
        page.taskPageForm.saveForm()

        and:
        at ActionPage
        page.actionPageForm.fillEntries("validTestAction1", "09:00", "10:00")
        page.actionPageForm.saveForm()

        when:
        at HomePage
        def newSize = tableRows.size()

        then:
        newSize > size
    }

    void 'Create invalid task (wrong date) then valid'() {
        given:
        System.setProperty "geb.env", "firefox"
        to HomePage
        def size =  tableRows.size()
        createButton.click()

        and:
        at TaskPage
        page.taskPageForm.fillEntries("validTestTask2", "2020-10-05", "09:00", "11:00", 1)
        page.taskPageForm.saveForm()

        and:
        at TaskPage
        page.taskPageForm.fillEntries("validTestTask2", "2024-10-05", "09:00", "11:00", 1)
        page.taskPageForm.saveForm()

        and:
        at ActionPage
        page.actionPageForm.fillEntries("validTestAction1", "09:00", "10:00")
        page.actionPageForm.saveForm()

        when:
        at HomePage
        def newSize = tableRows.size()

        then:
        newSize > size
    }

    void 'Create invalid task (wrong time) then valid'() {
        given:
        System.setProperty "geb.env", "firefox"
        to HomePage
        def size =  tableRows.size()
        createButton.click()

        and:
        at TaskPage
        page.taskPageForm.fillEntries("validTestTask3", "2024-11-05", "11:00", "09:00", 1)
        page.taskPageForm.saveForm()

        and:
        at TaskPage
        page.taskPageForm.fillEntries("validTestTask3", "2024-11-05", "09:00", "11:00", 1)
        page.taskPageForm.saveForm()

        and:
        at ActionPage
        page.actionPageForm.fillEntries("validTestAction1", "09:00", "10:00")
        page.actionPageForm.saveForm()

        when:
        at HomePage
        def newSize = tableRows.size()

        then:
        newSize > size
    }

    void 'delete task'() {
        given:
        System.setProperty "geb.env", "firefox"
        to HomePage
        def size =  tableRows.size()

        and:
        at HomePage
        tableRows[0].deleteButton.click()
        waitFor { confirmWindow }
        confirmButton.click()

        when:
        at HomePage
        def newSize = tableRows.size()

        then:
        newSize < size
    }

    void 'search task and get busy time'() {
        given:
        System.setProperty "geb.env", "firefox"
        to HomePage

        when:
        at HomePage
        page.searchInput << "2024-10-05"
        searchButton.click()

        then:
        searchHeader.text().contains("Total busy time is: hours: 2 minutes: 0")
    }

    void 'Change task name'() {
        given:
        System.setProperty "geb.env", "firefox"
        to HomePage

        and:
        at HomePage
        def row = tableRows.findResult { it.name == "validTestTask1" ? it : null }
        row.editButton.click()

        and:
        at TaskPage
        page.taskPageForm.fillEntries("updatedTask", "2024-12-05", "09:00", "11:00", 1)
        page.taskPageForm.updateForm()

        when:
        at HomePage

        then:
        tableRows.findResult { it.name == "updatedTask" ? it : null }
    }

    void 'Add action to task'() {
        given:
        System.setProperty "geb.env", "firefox"
        to HomePage

        and:
        at HomePage
        def row = tableRows.findResult { it.name == "updatedTask" ? it : null }
        row.detailsButton.click()

        and:
        at DetailsPage
        def size = $("table > tbody > tr").sum().size()
        addActionToTask.click()

        and:
        at ActionPage
        page.actionPageForm.fillEntries("addedTestAction", "10:00", "11:00")
        page.actionPageForm.addForm()

        and:
        at HomePage
        row = tableRows.findResult { it.name == "updatedTask" ? it : null }
        row.detailsButton.click()

        when:
        at DetailsPage
        def newSize = $("table > tbody > tr").sum().size()

        then:
        newSize - size == 3
    }
}
