<%--
  Created by IntelliJ IDEA.
  User: ander
  Date: 12/07/2019
  Time: 08:46
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Usuários na Rede</title>
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
                        <c:if test="${usuario.admin}"><a class="dropdown-item" href="/lista-usuarios.adm">Usuários do
                            sistema</a></c:if>
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

    <table class="table table-bordered table-hover">
        <thead class="thead-light">
        <tr>
            <th>ID Usuário</th>
            <th>Endereço IP</th>
            <th>Ação</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="i" value="1"></c:set>
        <c:forEach items="${usuarios}" var="user">
            <tr>
                <td>${user.id.replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("\'", "&apos;")}</td>
                <td>${user.endereco}</td>
                <td>


                    <!-- Button trigger modal -->
                    <button type="button" class="btn btn-info" data-toggle="modal"
                            data-target="#carteira_${i}">
                        Ver Carteira
                    </button>

                    <c:if test="${usuario.admin}">
                    <button type="button" class="btn btn-info" data-toggle="modal"
                            data-target="#transf_${i}">
                        Transferir PilaCoin
                    </button>
                    </c:if>

                    <!-- Modal -->
                    <div class="modal fade" id="carteira_${i}" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Carteira de ${user.id}</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <div class="row">
                                        <c:forEach items="${user.meusPilas}" var="pila">
                                            <div class="col-md-3 col-sm-3 col-3"><h4><span
                                                    class="badge badge-pill badge-info">Pila_${pila.id}</span></h4>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="transf_${i}" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel2" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel2">Carteira de ${user.id}</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form method="post" action="/trans_pila_rede.adm">
                                        <b>Usuário: </b><span
                                            class="text-info">${user.id}</span>
                                        <br>
                                        <input type="hidden" name="idDestino" value="${user.id}">
                                        <label>Selecione o pila coin que deseja transferir:</label>
                                        <br>
                                        <select id="selPila" class="form-control" name="idPila">
                                            <option disabled selected>Escolha um PilaCoin</option>
                                            <c:forEach items="${userAdmin.meusPilas}" var="pilaAdmin">
                                                <option value="${pilaAdmin.id}">Pila_${pilaAdmin.id}</option>
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
            </tr>

            <c:set var="i" value="${i + 1}"></c:set>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    $('#selPila').on('change', function () {
        $('#transferir').prop('disabled', !$(this).val());
    }).trigger('change');
</script>
</body>
</html>
