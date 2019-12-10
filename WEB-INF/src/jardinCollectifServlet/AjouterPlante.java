package jardinCollectifServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjouterPlante extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet AjoutPlante : POST");
      
        System.out.println("Servlet AjoutPlante : POST dispatch vers plantes.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/plantes.jsp");
        dispatcher.forward(request, response);
    }
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet AjoutPlante : GET");
        // Si on a déjà entré les informations de connexion valide

        if (JardinHelper.peutProceder(getServletContext(), request, response))
        {
            System.out.println("Servlet Inscription : GET dispatch vers ajouterPlantes.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ajouterPlantes.jsp");
            dispatcher.forward(request, response);
        }
    }
}
