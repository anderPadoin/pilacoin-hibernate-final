<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%--
  Created by IntelliJ IDEA.
  User: cpol
  Date: 22/05/2017
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Página Inicial</title>
    <link href="<c:url value="/ressources/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/ressources/js/jquery-3.4.1.min.js" />"></script>
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
                <li class="nav-item active">
                    <a class="nav-link" href="/hello.priv.">Página Inicial <span class="sr-only">(current)</span></a>
                </li>
                <c:if test="${usuario.admin}">
                    <li class="nav-item">
                        <a class="nav-link" href="/mineracao.adm">Mineração</a>
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
                        <c:if test="${usuario.admin}"><a class="dropdown-item" href="/lista-usuarios.adm">Usuários do sistema</a></c:if>
                        <a class="dropdown-item" href="/usuarios_rede.priv">Usuários da Rede</a>
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

    <div class="jumbotron">
        <h1 class="display-4">Bem vindo ${usuario.login}</h1>
        <p class="lead">Esse é o sistema de mineração e transferência de PilaCoin.</p>
        <hr class="my-4">
        <p>Navegue pelas funcionalidades do sistema pelo menu superior</p>
    </div>
</div>
</body>
</html>
