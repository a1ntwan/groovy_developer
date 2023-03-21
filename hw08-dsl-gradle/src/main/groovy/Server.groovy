import com.sun.net.httpserver.HttpServer

class Server {

    Server() {
    }

    HttpServer create(String name, String description, int port, List<Mapping> maps) {
        String serverHello = name + "\n\n" + description + "\n"
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("0.0.0.0", port), 0)
        maps.each { mapping ->
            if (mapping.active == 'true') {
                context(httpServer, mapping.url, 200, serverHello)
            } else {
                context(httpServer, mapping.url, 403, "Forbidden\n")
            }
        }
        httpServer.start()
        return httpServer
    }

    void stop(HttpServer server) {
        server.stop(1)
    }

    private void context(HttpServer server, String url, int statusCode, String msg) {
        server.createContext(url, exchange -> {
            exchange.sendResponseHeaders(statusCode, msg.length())
            exchange.responseBody.write(msg.bytes)
        })
    }

}
