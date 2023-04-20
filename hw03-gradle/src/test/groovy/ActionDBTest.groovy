import org.junit.jupiter.api.Test

import java.time.LocalDateTime

class ActionDBTest extends GroovyTestCase {

    @Test
    void testPlus() {
        ActionDB db = new ActionDB()

        Action testAction7 = new Action(activity: "test1", start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask7 = new Task(name: "test1",
                start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("10.05.2023 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask7 += testAction7

        db += testTask7

        assertEquals(1, db.actionDB.size())
    }

    @Test
    void testTestPlus() {
        ActionDB db = new ActionDB()

        Action testAction8 = new Action(activity: "test1", start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'), duration: 1800)

        Task testTask8 = new Task(name: "test1",
                start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("10.05.2023 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask8 += testAction8

        db.plus(testTask8, testAction8)

        assertEquals(1, db.actionDB.size())
    }

    @Test
    void testMinus() {
        ActionDB db = new ActionDB()

        Action testAction9 = new Action(activity: "test1", start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask9 = new Task(name: "test1",
                start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("10.05.2023 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask9 += testAction9

        db += testTask9
        db -= 'test1'

        assertEquals(0, db.actionDB.size())
    }

    @Test
    void testTestMinus() {
        ActionDB db = new ActionDB()

        Action testAction9 = new Action(activity: "test1", start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'), duration: 1800)

        Task testTask9 = new Task(name: "test1",
                start: LocalDateTime.parse("10.05.2023 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("10.05.2023 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask9 += testAction9

        db.plus(testTask9, testAction9)
        db.minus("test1", "test1")

        assertEquals(0, db.actionDB.size())
    }
}
