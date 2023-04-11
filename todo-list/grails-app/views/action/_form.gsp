<% params.num.toInteger().times { num -> %>
    <div class="form-group">
        <label><g:message code="action.activity"/> *</label>
        <g:if test="${task}">
        <g:textField name="activity" class="form-control" value="${task[num].model.activity}" placeholder="Please enter the name of your action: "/>
        <UIHelper:renderErrorMessage fieldName="activity" model="${params.models[num]}" errorMessage="please.enter.action.activity"/>
        </g:if>
        <g:else>
        <g:textField name="activity" class="form-control" value="${action?.activity}" placeholder="Please enter the name of your action: "/>
        </g:else>
    </div>

<div class="form-group">
    <label><g:message code="action.start"/> *</label>
    <g:if test="${task}">
    <g:field type="time" name="start" class="form-control" value="${task[num].model.start.toLocalTime()}" placeholder="Please enter the start time for your action in format HH:mm : "/>
    <UIHelper:renderErrorMessage fieldName="start" model="${params.models[num]}" errorMessage="please.valid.time"/>
    </g:if>
    <g:else>
    <g:field type="time" name="start" class="form-control" value="${action?.start}" placeholder="Please enter the start time for your action in format HH:mm : "/>
    </g:else>
</div>

<div class="form-group">
    <label><g:message code="action.finish"/> *</label>
    <g:if test="${task}">
    <g:field type="time" name="finish" class="form-control" value="${task[num].model.finish.toLocalTime()}" placeholder="Please enter the finish time for your action in format HH:mm : "/>
    <UIHelper:renderErrorMessage fieldName="finish" model="${params.models[num]}" errorMessage="please.valid.time"/>
    </g:if>
    <g:else>
    <g:field type="time" name="finish" class="form-control" value="${action?.finish}" placeholder="Please enter the finish time for your action in format HH:mm : "/>
    </g:else>
</div>
    <br>
    <br>
    <br>
<%}%>