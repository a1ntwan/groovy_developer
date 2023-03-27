package todo.list

import java.time.LocalDateTime

class Task {

    LocalDateTime start
    LocalDateTime finish
    String name

    static hasMany = [action: Action]

    static constraints = {
        start(nullable: false)
        finish(nullable: false)
        name(nullable: false)
    }

//    def beforeInsert() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern('dd.MM.yyyy HH:mm')
//
//        String dateTimeStart = "${this.stringDate} ${this.stringStart}"
//        this.start = LocalDateTime.parse(dateTimeStart, formatter)
//
//        String dateTimeFinish = "${this.stringDate} ${this.stringFinish}"
//        this.finish = LocalDateTime.parse(dateTimeFinish, formatter)
//    }

    def beforeUpdate() {

    }
}
