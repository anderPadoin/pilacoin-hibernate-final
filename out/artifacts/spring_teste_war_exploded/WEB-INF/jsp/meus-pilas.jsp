<%--
  Created by IntelliJ IDEA.
  User: ander
  Date: 05/07/2019
  Time: 13:45
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meus Pilas</title>
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
                <li class="nav-item">
                    <a class="nav-link" href="/hello.priv">Página Inicial <span class="sr-only">(current)</span></a>
                </li>
                <c:if test="${usuario.admin}">
                    <li class="nav-item">
                        <a class="nav-link" href="/mineracao.adm">Mineração</a>
                    </li>
                </c:if>
                <li class="nav-item active">
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
    <h4 class="my-2">Meus PilaCoins</h4>
    <c:if test="${not empty mensagem}">
        <div class="alert alert-${tipo}" role="alert">
                ${mensagem.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "&apos;")}
        </div>
    </c:if>

    <table class="table table-bordered table-hover">
        <thead class="thead-light">
        <tr>
            <th>Pila</th>
            <th>Criador</th>
            <c:if test="${usuario.admin}">
                <th>Ação</th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pilas}" var="pila">
            <tr>
                <td>${pila.id}</td>
                <td>${pila.idCriador.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("\'", "&apos;")}</td>
                <c:if test="${usuario.admin}">
                    <td>

                        <!-- Button trigger modal -->
                        <button class="btn btn-info" data-toggle="modal" data-target="#modal_${pila.id}">
                            Transferir
                        </button>
                    </td>

                    <!-- Modal -->
                    <div class="modal fade" id="modal_${pila.id}" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Transferir pila</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form method="post" action="/trans_pila.adm">
                                        <b>Pila: </b><span class="text-info">${pila.id}</span> <b>| Criador: </b><span
                                            class="text-info">${pila.idCriador.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("\'", '&apos;')}</span>
                                        <br>
                                        <input type="hidden" name="idPila" value="${pila.id}">
                                        <label>Selecione o usuário na rede que deseja transferir o pila:</label>
                                        <br>
                                        <select id="selusuarios" class="form-control" name="idDestino">
                                            <option disabled selected>Escolha um usuário</option>
                                            <c:forEach items="${usuarios}" var="user">
                                                <option value="${user}">${user.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("\'", '&apos;')}</option>
                                            </c:forEach>
                                        </select>

                                        <br>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar
                                        </button>
                                        <button type="submit" id="transferir" class="btn btn-primary">Transferir
                                        </button>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </div>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    $('#selusuarios').on('change', function () {
        $('#transferir').prop('disabled', !$(this).val());
    }).trigger('change');
</script>
</body>
</html>
