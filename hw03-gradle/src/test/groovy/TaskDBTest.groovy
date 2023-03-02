import org.junit.jupiter.api.Test

import java.time.LocalDateTime

class TaskDBTest extends GroovyTestCase {

    @Test
    void testPlus() {
        TaskDB db = new TaskDB()

        Action testAction3 = new Action(activity: "test1", start: LocalDateTime.parse("01.01.2024 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask3 = new Task(name: "test1",
                start: LocalDateTime.parse("01.01.2024 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("01.01.2024 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask3 += testAction3

        db += testTask3

        assertEquals(1, db.taskDB.size())
    }

    @Test
    void testMinus() {
        TaskDB db = new TaskDB()

        Action testAction4 = new Action(activity: "test1", start: LocalDateTime.parse("01.01.2024 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask4 = new Task(name: "test1",
                start: LocalDateTime.parse("01.01.2024 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("01.01.2024 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask4 += testAction4

        db += testTask4
        db -= "test1"

        assertEquals(0, db.taskDB.size())
    }

    @Test
    void testGetTasksByDate() {
        TaskDB db = new TaskDB()

        Action testAction5 = new Action(activity: "test1", start: LocalDateTime.parse("01.01.2024 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask5 = new Task(name: "test1",
                start: LocalDateTime.parse("01.01.2024 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("01.01.2024 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask5 += testAction5

        db += testTask5

        assertEquals(ArrayList, db.getTasksByDate('01.01.2024').getClass())
    }

    @Test
    void testGetNumTasksByDate() {
        TaskDB db = new TaskDB()

        Action testAction6 = new Action(activity: "test1", start: LocalDateTime.parse("01.01.2024 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask6 = new Task(name: "test1",
                start: LocalDateTime.parse("01.01.2024 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("01.01.2024 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask6 += testAction6

        db += testTask6

        assertEquals(1, db.getTasksByDate('01.01.2024').size())
    }
}
