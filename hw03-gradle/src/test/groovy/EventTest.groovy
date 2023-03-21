import org.junit.jupiter.api.Test

import java.time.LocalDateTime

class EventTest extends GroovyTestCase {

    @Test
    void testCreateEvent() {
        Action testAction1 = new Action(activity: "test1", start: LocalDateTime.parse("14.04.2023 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask1 = new Task(name: "test1",
                start: LocalDateTime.parse("14.04.2023 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("14.04.2023 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        testTask1 += testAction1

        Event event1 = new Event()

        assertEquals(ArrayList, event1.createEvent(testTask1).getClass())
    }

    @Test
    void testTestCreateEvent() {
        Action testAction2 = new Action(activity: "test1", start: LocalDateTime.parse("14.04.2023 21:00", 'dd.MM.yyyy HH:mm'), duration: 3600)

        Task testTask2 = new Task(name: "test1",
                start: LocalDateTime.parse("14.04.2023 21:00", 'dd.MM.yyyy HH:mm'),
                finish: LocalDateTime.parse("14.04.2023 22:00", 'dd.MM.yyyy HH:mm'),
                actions: []
        )

        Event event2 = new Event()

        assertEquals(HashMap, event2.createEvent(testTask2, testAction2).getClass())
    }
}
