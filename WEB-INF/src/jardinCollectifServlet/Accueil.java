package jardinCollectifServlet;

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.*;
import javax.servlet.http.*;

import JardinCollectif.GestionJardin;
import JardinCollectif.IFT287Exception;

/**
 * Servlet qui g�re la connexion d'un utilisateur au syst�me de gestion de
 * biblioth�que
 * 
 * <pre>
 * Vincent Ducharme
 * Universit� de Sherbrooke
 * Version 1.0 - 11 novembre 2018
 * IFT287 - Exploitation de BD relationnelles et OO
 * </pre>
 */

public class Accueil extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Accueil : POST");
        try
        {
            if (!JardinHelper.peutProcederLogin(getServletContext(), request, response))
            {
                System.out.println("Servlet Accueil : POST ne peut pas proc�der.");
                // Le dispatch sera fait par BiblioHelper.peutProceder
                return;
            }

            HttpSession session = request.getSession();

            // Si c'est la premi�re fois qu'on essaie de se logguer, ou
            // d'inscrire quelqu'un
            if (!JardinHelper.gestionnairesCrees(session))
            {
                System.out.println("Servlet Accueil : POST Cr�ation des gestionnaires");
                JardinHelper.creerGestionnaire(getServletContext(), session);
            }

            if (request.getParameter("connecter") != null)
            {
                System.out.println("Servlet Accueil : POST - Connecter");
                try
                {
                    // lecture des param�tres du formulaire login.jsp
                    //String userId = request.getParameter("userId");
                	String prenom = request.getParameter("prenom");
                	String nom = request.getParameter("nom");
                    String motDePasse = request.getParameter("motDePasse");

                    request.setAttribute("prenom", prenom);
                    request.setAttribute("nom", nom);
                    request.setAttribute("motDePasse", motDePasse);
                                        
                    if (prenom == null || prenom.equals(""))
                        throw new IFT287Exception("Le prenom ne peut pas �tre nul!");
                    if (prenom == null || prenom.equals(""))
                        throw new IFT287Exception("Le nom ne peut pas �tre nul!");
                    if (motDePasse == null || motDePasse.equals(""))
                        throw new IFT287Exception("Le mot de passe ne peut pas �tre nul!");
                    
                    String motDePasseEncrypte = toHexString(getSHA(motDePasse));

                    if (JardinHelper.getJardinInterro(session).getGestionMembre().informationsConnexionValide(prenom, nom,
                            motDePasseEncrypte))
                    {
                        session.setAttribute("prenom", prenom);
                        session.setAttribute("nom", prenom);
                       // if(JardinHelper.getBiblioInterro(session).getGestionMembre().utilisateurEstAdministrateur(prenom))
                            session.setAttribute("admin", true);
                        session.setAttribute("etat", new Integer(JardinConstante.CONNECTE));

                        System.out.println("Servlet Accueil : POST dispatch vers accueil.jsp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
                        dispatcher.forward(request, response);
                    }
                    else
                    {
                        throw new IFT287Exception("Les informations de connexion sont erron�es.");
                    }
                }
                catch (Exception e)
                {
                    List<String> listeMessageErreur = new LinkedList<String>();
                    listeMessageErreur.add(e.getMessage());
                    request.setAttribute("listeMessageErreur", listeMessageErreur);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
                    dispatcher.forward(request, response);
                    // pour d�boggage seulement : afficher tout le contenu de l'exception
                    e.printStackTrace();
                }
            }
            else if (request.getParameter("inscrire") != null)
            {
                System.out.println("Servlet Accueil : POST - Inscrire");
                try
                {
                    // lecture des param�tres du formulaire de creerCompte.jsp
                    String userId = request.getParameter("userId");
                    String prenom = request.getParameter("prenom");
                    String motDePasse = request.getParameter("motDePasse");
                    String nom = request.getParameter("nom");

                    request.setAttribute("prenom", prenom);
                    request.setAttribute("motDePasse", motDePasse);
                    request.setAttribute("nom", nom);
                    
                    if (motDePasse == null || motDePasse.equals(""))
                        throw new IFT287Exception("Vous devez entrer un mot de passe!");
                    if (prenom == null || prenom.equals(""))
                        throw new IFT287Exception("Vous devez entrer un prenom!");
                    if (nom == null || nom.equals(""))
                        throw new IFT287Exception("Vous devez entrer un nom!");


                    String accesS = request.getParameter("acces");
                    boolean acces = false;
                    if (accesS != null)
                        acces = Boolean.parseBoolean(accesS);

                    String motDePasseEncrypte = toHexString(getSHA(motDePasse));
                    
                    GestionJardin jardinUpdate = JardinHelper.getJardinUpdate(session);
                    synchronized (jardinUpdate)
                    {
                        jardinUpdate.getGestionMembre().inscrireMembre(prenom, nom, motDePasseEncrypte, acces);
                        JardinHelper.getJardinInterro(session).getConnexion().commit();
                    }

                    // S'il y a d�j� un userID dans la session, c'est parce
                    // qu'on est admin et qu'on inscrit un nouveau membre
                    if (session.getAttribute("prenom") == null)
                    {
                        session.setAttribute("userID", userId);
                        if(acces == true)
                            session.setAttribute("admin", acces == true);
                        session.setAttribute("etat", new Integer(JardinConstante.CONNECTE));

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
                    // pour d�boggage seulement : afficher tout le contenu de l'exception
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
            // pour d�boggage seulement : afficher tout le contenu de l'exception
            e.printStackTrace();
        }
    }

    // Dans les formulaires, on utilise la m�thode POST
    // donc, si le servlet est appel� avec la m�thode GET
    // c'est que l'adresse a �t� demand� par l'utilisateur.
    // On proc�de si la connexion est actives seulement, sinon
    // on retourne au login
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Servlet Accueil : GET");
        if (JardinHelper.peutProceder(getServletContext(), request, response))
        {
            System.out.println("Servlet Accueil : GET dispatch vers accueil.jsp");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
            dispatcher.forward(request, response);
        }
    }
    
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
    {  
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
  
        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 
    
    public static String toHexString(byte[] hash) 
    { 
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);  
  
        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32)  
        {  
            hexString.insert(0, '0');  
        }  
  
        return hexString.toString();  
    }

} // class
