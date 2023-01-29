class InputParser {
    private String string

    InputParser(String string) {
        this.string = string
    }

    static int convertToSec(String string) {
        switch (string[-1]) {
            case "h":
                return string[0..-2].toInteger() * 3600
            case "m":
                return string[0..-2].toInteger() * 60
            case "s":
                return string.toInteger()
            default:
                println("Please enter valid time format, example: 1h or 10m or 30s !")
                throw new UnsupportedOperationException()
        }
    }
}