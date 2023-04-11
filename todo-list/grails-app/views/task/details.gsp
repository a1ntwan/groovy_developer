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
                    <th class="text-right"><g:message code="Task name"/></th><td class="text-left">${task.name}</td>
                    <td>
                        <div class="btn-group">
                            <g:link controller="action" action="create" params="${[task: task.id, num: 1, add: true]}" class="btn btn-secondary"><i class="fas fa-eye"></i></g:link>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th class="text-right"><g:message code="Task Start"/></th><td class="text-left">${task.start}</td>
                </tr>
                <tr>
                    <th class="text-right"><g:message code="Task Finish"/></th><td class="text-left">${task.finish}</td>
                </tr>
                <g:each in="${task.actions}" var="action" >
                    <tr>
                        <th class="text-right"><g:message code="Activity Name"/></th><td class="text-left">${action.activity}</td>
                        <td>
                            <div class="btn-group">
                                <g:link controller="action" action="delete" id="${action.id}" class="btn btn-secondary delete-confirmation"><i class="fas fa-trash"></i></g:link>
                                <g:link controller="action" action="edit" id="${action.id}" params="${[num: 1]}" class="btn btn-secondary"><i class="fas fa-eye"></i></g:link>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th class="text-right"><g:message code="Activity Start"/></th><td class="text-left">${action.start}</td>
                    </tr>
                    <tr>
                        <th class="text-right"><g:message code="Activity Finish"/></th><td class="text-left">${action.finish}</td>
                    </tr>
                </g:each>
            </table>
        </g:if>
        <div class="form-action-panel">
            <g:link controller="task" action="index" class="btn btn-primary"><g:message code="cancel"/></g:link>
        </div>
    </div>
</div>