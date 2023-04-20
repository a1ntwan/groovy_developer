import org.junit.jupiter.api.Test

import java.time.LocalDateTime

class TaskTest extends GroovyTestCase {

    @Test
    void testMinus() {
        Action testAction = new Action(activity: "test1", start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask = new Task(name: "test1",
                start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("10.05.2023 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask += testAction

        testTask -= testAction
        assertEquals(0, testTask.actions.size())
    }

    @Test
    void testPlus() {
        Action testAction = new Action(activity: "test1", start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask = new Task(name: "test1",
                start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("10.05.2023 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask += testAction

        assertEquals(1, testTask.actions.size())
    }
}
