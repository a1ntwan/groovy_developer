package todo.list

import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap

@Transactional
class EventService {

    def save(GrailsParameterMap params) {
        Event event = new Event(params)
        def response = AppUtil.saveResponse(false, event)
        if (event.validate()) {
            event.save(flush: true)
            if (!event.hasErrors()){
                response.isSuccess = true
            }
        }
        return response
    }
    
    def delete(Event event) {
        try {
            event.delete(flush: true)
        } catch (Exception e) {
            println(e.getMessage())
            return false
        }
        return true
    }
}
