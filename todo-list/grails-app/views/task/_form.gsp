<div class="form-group">
    <label><g:message code="task.name"/> *</label>
    <g:textField name="name" class="form-control" value="${task?.name}" placeholder="Please enter your task name: "/>
    <UIHelper:renderErrorMessage fieldName="name" model="${task}" errorMessage="please.enter.name"/>
</div>

<div class="form-group">
    <label><g:message code="task.date"/> *</label>
    <g:field type="date" name="date" class="form-control" value="${task?.date}" placeholder="Please enter your task date in format yyyy-MM-dd :"/>
    <UIHelper:renderErrorMessage fieldName="date" model="${task}" errorMessage="please.valid.date"/>
</div>

<div class="form-group">
    <label><g:message code="task.start"/> *</label>
    <g:if test="${task?.start}">
    <g:field type="time" name="start" class="form-control" value="${task?.start.toLocalTime()}" placeholder="Please enter the start time for your task in format HH:mm : "/>
    </g:if>
    <g:else>
    <g:field type="time" name="start" class="form-control" value="${task?.start}" placeholder="Please enter the start time for your task in format HH:mm : "/>
    </g:else>
    <UIHelper:renderErrorMessage fieldName="start" model="${task}" errorMessage="please.valid.time"/>
</div>

<div class="form-group">
    <label><g:message code="task.finish"/> *</label>
    <g:if test="${task?.finish}">
    <g:field type="time" name="finish" class="form-control" value="${task?.finish.toLocalTime()}" placeholder="Please enter the finish time for your task in format HH:mm : "/>
    </g:if>
    <g:else>
    <g:field type="time" name="finish" class="form-control" value="${task?.finish}" placeholder="Please enter the finish time for your task in format HH:mm : "/>
    </g:else>
    <UIHelper:renderErrorMessage fieldName="finish" model="${task}" errorMessage="please.valid.time"/>
</div>

<div class="form-group">
    <label><g:message code="task.num"/> *</label>
    <g:field type="number" name="num" class="form-control" value="${num}" placeholder="Please enter the number of your actions: "/>
</div>