<% params.num.toInteger().times { num -> %>
    <div class="form-group">
        <label><g:message code="action.name"/> *</label>
        <g:textField name="name" class="form-control" value="${action?.name}" placeholder="Please enter the name of your action: "/>
        <UIHelper:renderErrorMessage fieldName="name" model="${action}" errorMessage="please.enter.name"/>
    </div>

    <div class="form-group">
        <label><g:message code="action.duration"/> *</label>
        <g:textField name="duration" class="form-control" value="${action?.duration}" placeholder="Please enter the duration of your action: "/>
    </div>
    <br>
    <br>
    <br>
<%}%>