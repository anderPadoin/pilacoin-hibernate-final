<%--
  Created by IntelliJ IDEA.
  User: ander
  Date: 11/07/2019
  Time: 15:08
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
    <div class="row">
        <div class="col-md-3 col-sm-3 col-lg-3"></div>
        <div class="col-md-6 col-sm-6 col-lg-6">
            <div class="jumbotron">
                <div class="display-4 text-center">Criar Conta</div>
                <c:if test="${not empty mensagem}">
                    <div class="alert alert-danger">
                            ${mensagem}
                    </div>
                </c:if>
                <form method="post" action="/cria-usuario.adm">
                    <label>Nome:</label>
                    <input type="text" name="nome" required class="form-control" type="text">
                    <label>Login:</label>
                    <input type="text" name="login" class="form-control" type="text">
                    <label>Senha:</label>
                    <input type="password" required name="senhaStr" class="form-control">

                    <input type="checkbox" required name="admin" value="true"> Usuário Administrador
                    <input type="hidden" name="admin" value="false">
                    <br>
                    <button type="submit" class="form-control btn btn-primary">Cadastrar</button>
                </form>
            </div>

        </div>
        <div class="col-md-3 col-sm-3 col-lg-3"></div>
    </div>


</div>
</body>
</html>
