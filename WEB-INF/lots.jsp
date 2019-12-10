<%@page import="jardinCollectifServlet.JardinHelper"%>
<%@ page
	import="java.util.*,java.text.*,jardinCollectifServlet.*,JardinCollectif.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>IFT287 - Système de gestion de bibliothèque</title>
<meta name="author" content="Vincent Ducharme">
<meta name="description"
	content="Page d'accueil du système de gestion de la bilbiothèque.">

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
		<jsp:include page="/WEB-INF/navigation.jsp" />
		<h1 class="text-center">Système de gestion du Jardin Collectif</h1>


		<h3 class="text-center">Lots</h3>
		<div class="col-8 offset-2">
			<table class="table">
				<thead class="thead-dark">
					<tr>
							<form name="Ajouterlot" method="get"
								action="AjouterLot">
								<input class="btn btn-primary"
									type="SUBMIT" name="ajouterLot" value="Ajouter Lot">
							</form>
					</tr>
					<tr>
						<th scope="col">Nom du lot</th>
						<th scope="col">Nombre de membres maximal</th>
						
						<%
						    if (session.getAttribute("admin") != null)
						    {
						%>
						<th scope="col">Supprimer</th>
						<%
						    }
						%>
					</tr>
				</thead>
				<tbody>
					<%
					    List<Lot> lots = JardinHelper.getJardinInterro(session).getGestionLot().getLots();
					    for (Lot l : lots)
					    {
					%>
					<tr>
						<td><%=l.getNomLot()%></td>
						<td><%=l.getNoMaxMembre()%></td>
						<%
						    if (session.getAttribute("admin") != null)
						        {
						%>
						<td>
							<form name="SupprimerLotForm" method="post"
								action="SuppressionLot">
								<input type="hidden" name="idLot" id="idLot"
									value="<%=l.getIdLot()%>" /> <input class="btn btn-primary"
									type="SUBMIT" name="supprimer" value="supprimer">
							</form>
						</td>
						<%
						    } // end if admin
						%>
					
					<tr>
					<tr>
						<td></td>
						<td colspan="2">
							<%
							    } // end for all members
							%>
						</td>
					</tr>
				</tbody>
			</table>
		</div>


		<br>
		<%-- inclusion d'une autre page pour l'affichage des messages d'erreur--%>
		<jsp:include page="/WEB-INF/messageErreur.jsp" />
		<br>
	</div>
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
