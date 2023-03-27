%{--Include Main Layout--}%
<meta name="layout" content="main"/>

<div class="card">
    <div class="card-header">
        <g:message code="task" args="['Details']"/>
    </div>
    <div class="card-body">
        <g:if test="${task}">
            <table class="table">
                <tr>
                    <th class="text-right"><g:message code="name"/></th><td class="text-left">${task.name}</td>
                </tr>
                <tr>
                    <th class="text-right"><g:message code="date"/></th><td class="text-left">${task.date}</td>
                </tr>
                <tr>
                    <th class="text-right"><g:message code="start"/></th><td class="text-left">${task.start}</td>
                </tr>
                <tr>
                    <th class="text-right"><g:message code="finish"/></th><td class="text-left">${task.finish}</td>
                </tr>
            </table>
        </g:if>
        <div class="form-action-panel">
            <g:link controller="task" action="index" class="btn btn-primary"><g:message code="cancel"/></g:link>
        </div>
    </div>
</div>