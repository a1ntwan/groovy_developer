import org.junit.jupiter.api.Test
import static org.junit.jupiter.api.Assertions.*

class LoaderTest {
    @Test
    void LoaderPlus() {
        assertEquals(0, Loader.run("ls -la"))
        assertEquals(127, Loader.run("lg -la"))
    }
}