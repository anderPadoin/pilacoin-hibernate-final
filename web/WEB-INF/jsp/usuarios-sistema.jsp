<%--
  Created by IntelliJ IDEA.
  User: ander
  Date: 12/07/2019
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Usuários do sistema</title>
    <link href="<c:url value="/ressources/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/ressources/js/jquery-3.3.1.slim.min.js" />"></script>
    <script src="<c:url value="/ressources/js/popper.min.js" />"></script>
    <script src="<c:url value="/ressources/js/bootstrap.min.js" />"></script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">PilaCoin.com</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown"
                aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/hello.priv">Página Inicial <span class="sr-only">(current)</span></a>
                </li>
                <c:if test="${usuario.admin}">
                    <li class="nav-item">
                        <a class="nav-link" href="/mineracao.priv">Mineração</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="/lista_meus_pilas.priv">Meus Pilas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logs.priv">Logs</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink2" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Usuários
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink2">
                        <c:if test="${usuario.admin}"><a class="dropdown-item" href="/lista-usuarios.priv">Usuários do sistema</a></c:if>
                        <a class="dropdown-item" href="/usuarios_rede.priv.priv">Usuários da Rede</a>
                    </div>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                       data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        ${usuario.login}
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                        <a class="dropdown-item" href="/logout.priv">Sair</a>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
    <h4>Usuários na Rede</h4>
    <c:if test="${not empty mensagem}">
        <div class="alert alert-${tipo}" role="alert">
                ${mensagem}
        </div>
    </c:if>
    <a class="btn btn-info" href="/criar_conta.adm">Cadastrar Usuário</a><br>
    <hr>
    <table class="table table-bordered table table-hover">
        <thead class="thead-light">
        <tr>
            <th>Nome</th>
            <th>Login</th>
            <th>Admin</th>
        </tr>

        </thead>
        <tbody>
        <c:forEach var="user" items="${usuarios}">
            <tr>
                <td>${user.nome}</td>
                <td>${user.login}</td>
                <td>
                    <c:if test="${user.admin}">SIM</c:if>
                    <c:if test="${!user.admin}">NÃO</c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
