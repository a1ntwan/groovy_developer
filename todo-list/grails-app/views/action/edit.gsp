%{--Include Main Layout--}%
<meta name="layout" content="main"/>

<div class="card">
    <div class="card-header">
        <g:message code="action" args="['Update']"/>
    </div>
    <div class="card-body">
        <g:form controller="action" action="update">
            <g:hiddenField name="id" value="${action.id}"/>
            <g:render template="form" model="[edit:'yes']"/>
            <div class="form-action-panel">
                <g:submitButton class="btn btn-primary" name="update" params="${[num: 1]}" value="${g.message(code: "update")}"/>
                <g:link controller="task" action="index" class="btn btn-primary"><g:message code="cancel"/></g:link>
            </div>
        </g:form>
    </div>
</div>