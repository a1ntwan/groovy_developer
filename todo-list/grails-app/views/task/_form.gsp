<div class="form-group">
    <label><g:message code="task.name"/> *</label>
    <g:textField name="name" class="form-control" value="${task?.name}" placeholder="Please enter your task name: "/>
    <UIHelper:renderErrorMessage fieldName="name" model="${task}" errorMessage="please.enter.name"/>
</div>

<div class="form-group">
    <label><g:message code="task.date"/> *</label>
    <g:textField name="date" class="form-control" value="${task?.date}" placeholder="Please enter your task date in format dd.MM.yyyy :"/>
</div>

<div class="form-group">
    <label><g:message code="task.start"/> *</label>
    <g:textField name="start" class="form-control" value="${task?.start}" placeholder="Please enter the start time for your task in format HH:mm : "/>
</div>

<div class="form-group">
    <label><g:message code="task.finish"/> *</label>
    <g:textField name="finish" class="form-control" value="${task?.finish}" placeholder="Please enter the finish time for your task in format HH:mm : "/>
</div>

<div class="form-group">
    <label><g:message code="task.num"/> *</label>
    <g:field type="number" name="num" class="form-control" value="${task?.num}" placeholder="Please enter the number of your actions: "/>
</div>