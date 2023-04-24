%{--Include Main Layout--}%
<meta name="layout" content="main"/>

<div class="card">
    <div class="card-header">
        <g:message code="action" args="['Create']"/>
    </div>
    <div class="card-body">
        <g:if test="${params.add}">
        <g:form controller="action" action="add" params="${[task: params.task]}">

            %{--Partial Templating--}%
            <g:render template="form"/>
            <div class="form-action-panel">
                <g:submitButton class="btn btn-primary" name="add" value="${g.message(code: "add")}"/>
                <g:link controller="task" action="index" class="btn btn-primary"><g:message code="cancel"/></g:link>
            </div>
        </g:form>
        </g:if>
        <g:else>
       <g:form controller="action" action="save">

            %{--Partial Templating--}%
            <g:render template="form"/>
            <div class="form-action-panel">
                <g:submitButton class="btn btn-primary" name="save" value="${g.message(code: "save")}"/>
                <g:link controller="task" action="index" class="btn btn-primary"><g:message code="cancel"/></g:link>
            </div>
        </g:form>
        </g:else>
    </div>
</div>