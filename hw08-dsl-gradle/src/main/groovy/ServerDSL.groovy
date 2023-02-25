class ServerDSL  {
    String name, description
    String include
    Map http
    Map https
    ArrayList <Closure> mappings
    List <Mapping> maps = []


    private void include(String args) {
        this.include = args
        Delegator delegator = new Delegator()
        DelegatingScript script = delegator.scriptDelegate("configs/${this.include}")
        Proto proto = new Proto()
        script.setDelegate(proto)
        script.run()
        this.http = script['http']
        this.https = script['https']
    }

    private Mapping createMapping(Closure closure) {
        Mapping map = new Mapping()
        closure.resolveStrategy = Closure.DELEGATE_ONLY
        closure.delegate = map
        closure.call()
        return map
    }

    void createMaps() {
        this.mappings.each {this.maps << createMapping(it) }
        this
    }

    void propertyMissing(String name) {
        println("No such property ${name}")
    }

    void methodMissing(String name, Object args) {
        println("No such property ${name} with ${args.toString()}")
    }

}
