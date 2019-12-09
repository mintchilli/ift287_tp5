package jardinCollectifServlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PromouvoirAdministrateur extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet promouvoirAdmin : POST");
        try
        {
            HttpSession session = request.getSession();
            int idUser = Integer.parseInt(request.getParameter("idMembre"));
            JardinHelper.getJardinInterro(session).getGestionMembre().makeAdmin(idUser);
            
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
        dispatcher.forward(request, response);
    }

}
