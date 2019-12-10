<%@ page import="java.util.*,java.text.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>IFT287 - Système de gestion de bibliothèque</title>
<meta name="author" content="Vincent Ducharme">
<meta name="description"
	content="Page de création de compte du système de gestion de la bilbiothèque.">

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
	integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
	crossorigin="anonymous">

</head>
<body>
	<div class="container">
		<h1 class="text-center">Création d'un lot</h1>
		<div class="col-md-4 offset-md-4">
			<form action="Lots" method="POST">
				<div class="form-group">
					<label for="nomLot">Nom du lot</label> <input class="form-control"
						type="TEXT" name="nomLot"
						value="<%=request.getAttribute("nomLot") != null ? (String) request.getAttribute("nomLot") : ""%>">
				</div>
				<div class="form-group">
					<label for="noMaxMembre">Nombre de membres maximal</label> <input class="form-control"
						type="TEXT" name="noMaxMembre"
						value="<%=request.getAttribute("noMaxMembre") != null ? (String) request.getAttribute("noMaxMembre") : ""%>">
				</div>
				
				<input class="btn btn-primary" type="SUBMIT" name="ajouter lot"
					value="Ajouter le lot">
			</form>
		</div>
	</div>
	<br>
	<%-- inclusion d'une autre page pour l'affichage des messages d'erreur--%>
	<jsp:include page="/WEB-INF/messageErreur.jsp" />
	<br>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
		integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
		crossorigin="anonymous"></script>
</body>
</html>
