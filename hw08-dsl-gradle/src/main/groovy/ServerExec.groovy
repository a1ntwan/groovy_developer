class ServerExec {
    static void main(String[] args) {
        Delegator delegator = new Delegator()
        DelegatingScript script = delegator.scriptDelegate("configs/parent_config.conf")

        ServerDSL config = new ServerDSL()
        script.setDelegate(config)
        script.run()

        config.createMaps()

        Server srv = new Server()

        srv.create(config.name, config.description, config.http['port'].toInteger(), config.maps)
        srv.create(config.name, config.description, config.https['port'].toInteger(), config.maps)

    }
}
