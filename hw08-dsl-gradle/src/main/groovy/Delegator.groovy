import org.codehaus.groovy.control.CompilerConfiguration

class Delegator {

    Delegator() {}

    DelegatingScript scriptDelegate(String path) {
        CompilerConfiguration cc = new CompilerConfiguration()
        cc.setScriptBaseClass(DelegatingScript.class.getName())
        GroovyShell sh = new GroovyShell(ServerExec.class.getClassLoader(), new Binding(), cc)
        DelegatingScript script = (DelegatingScript)sh.parse(new File(path))
        return script
    }
}
