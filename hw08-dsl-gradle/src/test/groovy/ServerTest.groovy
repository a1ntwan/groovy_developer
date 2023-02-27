import com.sun.net.httpserver.HttpServer
import org.junit.jupiter.api.Test

class ServerTest extends GroovyTestCase {
    @Test
    void testCreateHTTP() {
        Delegator delegator = new Delegator()
        DelegatingScript script = delegator.scriptDelegate("configs/parent_config.conf")

        ServerDSL config = new ServerDSL()
        script.setDelegate(config)
        script.run()

        config.createMaps()

        Server srv = new Server()

        HttpServer http = srv.create(config.name, config.description, config.http['port'].toInteger(), config.maps)

        URLConnection get = new URL("http://127.0.0.1:8080/").openConnection()
        get.setDoOutput(true)
        get.setRequestMethod("GET")
        int getRC = get.getResponseCode()

        assertEquals(200, getRC)

        get = new URL("http://127.0.0.1:8080/login").openConnection()
        get.setDoOutput(true)
        get.setRequestMethod("GET")
        getRC = get.getResponseCode()

        assertEquals(403, getRC)

        srv.stop(http)

    }

    @Test
    void testCreateHTTPS() {
        Delegator delegator = new Delegator()
        DelegatingScript script = delegator.scriptDelegate("configs/parent_config.conf")

        ServerDSL config = new ServerDSL()
        script.setDelegate(config)
        script.run()

        config.createMaps()

        Server srv = new Server()

        HttpServer https = srv.create(config.name, config.description, config.https['port'].toInteger(), config.maps)

        URLConnection get = new URL("http://127.0.0.1:4443/").openConnection()
        get.setDoOutput(true)
        get.setRequestMethod("GET")
        int getRC = get.getResponseCode()

        assertEquals(200, getRC)

        get = new URL("http://127.0.0.1:4443/login").openConnection()
        get.setDoOutput(true)
        get.setRequestMethod("GET")
        getRC = get.getResponseCode()

        assertEquals(403, getRC)

        srv.stop(https)

    }
}
