package jardinCollectifServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjouterLot extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet AjoutLot : POST");
        
        System.out.println("Servlet AjoutLot : POST dispatch vers lots.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/lots.jsp");
        dispatcher.forward(request, response);
    }
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet AjoutLot : GET");
        // Si on a déjà entré les informations de connexion valide

        if (JardinHelper.peutProceder(getServletContext(), request, response))
        {
            System.out.println("Servlet AjoutLot : GET dispatch vers ajouterLot.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ajouterLot.jsp");
            dispatcher.forward(request, response);
        }
    }
}
