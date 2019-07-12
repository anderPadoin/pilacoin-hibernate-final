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
    <div class="row">
        <div class="col-md-3 col-sm-3 col-lg-3"></div>
        <div class="col-md-6 col-sm-6 col-lg-6">
            <div class="jumbotron">
                <div class="display-4 text-center">Logar em PilaCoin.com</div>
                <form action="/login.html" method="post">
                    <label>Login:</label>
                    <input class="form-control" type="text" name="login">
                    <label>Senha:</label>
                    <input class="form-control" type="password" name="senha">
                    <br>
                    <button class="btn btn-primary form-control" type="submit">Entrar</button>
                </form>
            </div>
            <c:if test="${not empty mensagem}">
                <div class="alert alert-${tipo}" role="alert">
                        ${mensagem}
                </div>
            </c:if>
        </div>
        <div class="col-md-3 col-sm-3 col-lg-3"></div>
    </div>
</div>
</body>
</html>
