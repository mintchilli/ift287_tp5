<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	<div class="navbar-collapse collapse">
    	<ul class="nav navbar-nav">
	<%
	    if (session.getAttribute("admin") != null)
	    {
	%>
			<li><a class="nav-item nav-link" href="Inscription">Inscrire un membre</a></li>
	<%
	    }
	%>
			<li><a class="nav-item nav-link" href="Lots">Lots</a></li>
			<li><a class="nav-item nav-link" href="Plantes">Plantes</a></li>
		</ul>
		</div>
		<div class="navbar-collapse collapse justify-content-end">
		<ul class="nav navbar-nav navbar-right">
			<li><a class="nav-item nav-link" href="Logout">Déconnexion</a></li>
		</ul>
	</div>
</nav>
