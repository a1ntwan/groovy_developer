package todo.list

class UIHelperTagLib {

    static namespace = "UIHelper"

    def renderErrorMessage = { attrs, body ->
        def model = null
        if (attrs.model.getClass() != LinkedHashMap) {
            model = attrs.model
        } else {
            model = attrs.model.model
        }
        String fieldName = attrs.fieldName
        String errorMessage = attrs.errorMessage ? g.message(code: attrs.errorMessage) : g.message(code: "invalid.input")
        if (model) {
            println('----------------------------------------------------------------------------')
            println(model.errors)
            println('----------------------------------------------------------------------------')
        }

        if (model && model.errors && model.errors.getFieldError(fieldName)) {
            out << "<small class='form-text text-danger''><strong>${errorMessage}</strong></small>"
        }
    }
}

