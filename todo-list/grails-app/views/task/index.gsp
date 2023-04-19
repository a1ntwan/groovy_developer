%{--Include Main Layout--}%
<meta name="layout" content="main"/>

<div class="card">
    <div class="card-header">
        <g:message code="task" args="['List']"/>

        %{--Actions--}%
        <span class="float-right">

        %{--Search Panel --}%
        <div class="btn-group">
            <g:form controller="task" action="index" method="GET">
                <div class="input-group" id="search-area">
                    <g:select name="colName" class="form-control" from="[date: 'Date']" value="${params?.colName}" optionKey="key" optionValue="value"/>
                    <g:field type="date" name="colValue" class="form-control" value="${params?.colValue}"/>
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="submit">Search</button>
                    </span>
                </div>
            </g:form>
        </div>

        %{--Create and Reload Panel--}%
        <div class="btn-group">
            <g:link controller="task" action="create" class="btn btn-success"><g:message code="create"/></g:link>
            <g:link controller="task" action="index" class="btn btn-primary"><g:message code="reload"/></g:link>
        </div>
        </span>
    </div>

    %{--Table Panel--}%
    <div class="card-body">
        <table class="table table-bordered">
            <thead class="thead-dark">
            <tr>
                <g:sortableColumn property="Name" title="${g.message(code: "name")}"/>
                <g:sortableColumn property="Start" title="${g.message(code: "start")}"/>
                <g:sortableColumn property="Finish" title="${g.message(code: "finish")}"/>
                <th class="action-row"><g:message code="action"/></th>
            </tr>
            </thead>
            <tbody>
                <g:set var="counter" value="${0}"/>
                <g:each in="${taskList}" var="info">
                <g:if test="${info?.start}">
                <g:set var="counter" value="${counter + info.duration}"/>
                </g:if>
                    <tr>
                        <td>${info?.name}</td>
                        <td>${info?.start}</td>
                        <td>${info?.finish}</td>
                        %{--Table Options --}%
                        <td>
                            <div class="btn-group">
                                <g:link controller="task" action="delete" id="${info.id}" class="btn btn-secondary delete-confirmation"><i class="fas fa-trash"></i></g:link>
                                <g:link controller="task" action="details" id="${info.id}" class="btn btn-secondary"><i class="fas fa-eye"></i></g:link>
                                <g:link controller="task" action="edit" id="${info.id}" class="btn btn-secondary"><i class="fas fa-edit" aria-hidden="true"></i></g:link>
                            </div>
                        </td>
                    </tr>
                </g:each>
                <g:if test="${byDate}">
                <h3> Total busy time is: hours: ${counter / 3600} minutes: ${counter % 3600 / 60} </h3>
                </g:if>
                <br>
            </tbody>
        </table>

    %{--Pagination Area--}%
    <div class="paginate">
        <g:paginate total="${total ?: 0}" />
    </div>
    </div>
</div>