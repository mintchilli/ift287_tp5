package jardinCollectifServlet;

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.*;
import javax.servlet.http.*;

import Bibliotheque.BiblioException;
import JardinCollectif.GestionJardin;


public class Lots extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Lots : POST");
        try
        {    
            HttpSession session = request.getSession();

            if (request.getParameter("connecter") != null)
            {
                System.out.println("Servlet Lots : POST - Lots");
                try
                {
                    // lecture des paramètres du formulaire login.jsp
                    //String userId = request.getParameter("userId");
                	String prenom = request.getParameter("prenom");
                	String nom = request.getParameter("nom");
                    String motDePasse = request.getParameter("motDePasse");

                    request.setAttribute("prenom", prenom);
                    request.setAttribute("nom", nom);
                    request.setAttribute("motDePasse", motDePasse);
                                        
                    if (prenom == null || prenom.equals(""))
                        throw new BiblioException("Le prenom ne peut pas être nul!");
                    if (prenom == null || prenom.equals(""))
                        throw new BiblioException("Le nom ne peut pas être nul!");
                    if (motDePasse == null || motDePasse.equals(""))
                        throw new BiblioException("Le mot de passe ne peut pas être nul!");

                    if (JardinHelper.getJardinInterro(session).getGestionMembre().informationsConnexionValide(prenom, nom,
                            motDePasse))
                    {
                        session.setAttribute("prenom", prenom);
                        session.setAttribute("nom", prenom);
                       // if(JardinHelper.getBiblioInterro(session).getGestionMembre().utilisateurEstAdministrateur(prenom))
                            session.setAttribute("admin", true);
                        session.setAttribute("etat", new Integer(BiblioConstantes.CONNECTE));

                        System.out.println("Servlet Accueil : POST dispatch vers accueil.jsp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
                        dispatcher.forward(request, response);
                    }
                    else
                    {
                        throw new BiblioException("Les informations de connexion sont erronées.");
                    }
                }
                catch (Exception e)
                {
                    List<String> listeMessageErreur = new LinkedList<String>();
                    listeMessageErreur.add(e.getMessage());
                    request.setAttribute("listeMessageErreur", listeMessageErreur);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
                    dispatcher.forward(request, response);
                    // pour déboggage seulement : afficher tout le contenu de l'exception
                    e.printStackTrace();
                }
            }
            else if (request.getParameter("inscrire") != null)
            {
                System.out.println("Servlet Accueil : POST - Inscrire");
                try
                {
                    // lecture des paramètres du formulaire de creerCompte.jsp
                    String userId = request.getParameter("userId");
                    String prenom = request.getParameter("prenom");
                    String motDePasse = request.getParameter("motDePasse");
                    String nom = request.getParameter("nom");

                    request.setAttribute("prenom", prenom);
                    request.setAttribute("motDePasse", motDePasse);
                    request.setAttribute("nom", nom);
                    
                    if (motDePasse == null || motDePasse.equals(""))
                        throw new BiblioException("Vous devez entrer un mot de passe!");
                    if (prenom == null || prenom.equals(""))
                        throw new BiblioException("Vous devez entrer un prenom!");
                    if (nom == null || nom.equals(""))
                        throw new BiblioException("Vous devez entrer un nom!");


                    String accesS = request.getParameter("acces");
                    boolean acces = false;
                    if (accesS != null)
                        acces = Boolean.parseBoolean(accesS);
                    
                    GestionJardin jardinUpdate = JardinHelper.getJardinUpdate(session);
                    synchronized (jardinUpdate)
                    {
                        jardinUpdate.getGestionMembre().inscrireMembre(prenom, nom, motDePasse, acces);
                        JardinHelper.getJardinInterro(session).getConnexion().commit();
                    }

                    // S'il y a déjà un userID dans la session, c'est parce
                    // qu'on est admin et qu'on inscrit un nouveau membre
                    if (session.getAttribute("prenom") == null)
                    {
                        session.setAttribute("userID", userId);
                        if(acces == true)
                            session.setAttribute("admin", acces == true);
                        session.setAttribute("etat", new Integer(BiblioConstantes.CONNECTE));

                        System.out.println("Servlet Accueil : POST dispatch vers accueil.jsp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
                        dispatcher.forward(request, response);
                    }
                    else
                    {
                        // Vers gestionMembre?
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
                        dispatcher.forward(request, response);
                    }
                }
                catch (Exception e)
                {
                    List<String> listeMessageErreur = new LinkedList<String>();
                    listeMessageErreur.add(e.getMessage());
                    request.setAttribute("listeMessageErreur", listeMessageErreur);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/creerCompte.jsp");
                    dispatcher.forward(request, response);
                    // pour déboggage seulement : afficher tout le contenu de l'exception
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            List<String> listeMessageErreur = new LinkedList<String>();
            listeMessageErreur.add(e.getMessage());
            request.setAttribute("listeMessageErreur", listeMessageErreur);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
            dispatcher.forward(request, response);
            // pour déboggage seulement : afficher tout le contenu de l'exception
            e.printStackTrace();
        }
    }

    // Dans les formulaires, on utilise la méthode POST
    // donc, si le servlet est appelé avec la méthode GET
    // c'est que l'adresse a été demandé par l'utilisateur.
    // On procède si la connexion est actives seulement, sinon
    // on retourne au login
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Lots : GET");
        if (JardinHelper.peutProceder(getServletContext(), request, response))
        {
            System.out.println("Servlet lots : GET dispatch vers lots.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/lots.jsp");
            dispatcher.forward(request, response);
        }
    }

} // class
