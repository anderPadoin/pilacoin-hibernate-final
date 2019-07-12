<%--
  Created by IntelliJ IDEA.
  User: cpol
  Date: 22/05/2017
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <link href="<c:url value="/ressources/css/bootstrap.min.css" />" rel="stylesheet">
    <script src="<c:url value="/ressources/js/jquery-3.3.1.slim.min.js" />"></script>
    <script src="<c:url value="/ressources/js/bootstrap.min.js" />"></script>
</head>
<body>
<div class="container">
    <br>
    <c:if test="${not empty mensagem}">
        <div class="alert alert-success">
                ${mensagem}
        </div>
    </c:if>
    <div class="jumbotron">
        <h1 class="display-4">Bem vindo ao PilaCoin.com</h1>
        <p class="lead">Seu sistema de carteira e mineração de PilaCoins</p>
<br>
        <hr>
        <br>
        <div class="row">
            <div class="col text-center">
                <a href="/logar.html" class="btn btn-primary">Acessar o sistema</a>
            </div>

        </div>
    </div>
</div>
</body>
</html>
