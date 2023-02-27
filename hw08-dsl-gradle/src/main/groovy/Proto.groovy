class Proto {
    Map http = [:]
    Map https = [:]

    void http(Closure closure) {
        http.with(closure)
    }

    void https(Closure closure) {
        https.with(closure)
    }

}