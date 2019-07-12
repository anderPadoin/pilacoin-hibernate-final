<%--
  Created by IntelliJ IDEA.
  User: ander
  Date: 10/07/2019
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mineração</title>
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
                    <li class="nav-item active">
                        <a class="nav-link" href="/mineracao.adm">Mineração</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="/lista_meus_pilas.priv">Meus Pilas</a>
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
    <h4 class="my-2">Mineração de PilaCoin</h4>
    <div class="jumbotron">

        <c:if test="${!serverFound && not empty serverFound}">
            <div class="alert alert-warning text-center text-uppercase" role="alert">
                <b>Servidor não encontrato. Tente novamente mais tarde</b>
            </div>
        </c:if>
        <div class="alert alert-light">
            <div class="row">
                <div class="col-md-6">
                    Status da Mineração:<br>
                </div>
                <div class="col-md-6 text-left">
                    <c:if test="${status == false}">
                        <span class="text-warning"><b>A mineracao esta suspensa</b></span>

                    </c:if>
                    <c:if test="${status}">

                        <span class="text-success"><b>A mineracao esta em execucao</b></span>
                        <div class="spinner-grow spinner-grow-sm text-success" role="status">
                            <span class="sr-only">Loading...</span>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>


        <c:if test="${status}">
            <a class="btn btn-danger" href="switchmineracao.adm">Suspender Mineracao</a>
        </c:if>
        <c:if test="${!status}">
            <a class="btn btn-success" href="switchmineracao.adm">Iniciar Mineracao</a>
        </c:if>
    </div>

</div>
</body>
</html>
