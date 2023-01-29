class Loader {

    static int run(String cmd) {
        println("Running ${cmd}")
        Process process = new ProcessBuilder([ "sh", "-c", cmd]).redirectErrorStream(true).start()
        process.outputStream.close()
        process.inputStream.eachLine {println(it)}
        process.waitFor()
        return process.exitValue()
    }
}