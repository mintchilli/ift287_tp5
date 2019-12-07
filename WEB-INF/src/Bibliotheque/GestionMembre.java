package Bibliotheque;

import java.sql.*;
import java.util.List;

/**
 * Gestion des transactions de reliées à la gestion de membres
 * dans une bibliothèque.
 *
 * <pre>
 * Marc Frappier
 * Université de Sherbrooke
 * Version 2.0 - 13 novembre 2004
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Vincent Ducharme
 * Université de Sherbrooke
 * Version 3.0 - 11 novembre 2018
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class GestionMembre
{
    private Connexion cx;
    private TableMembres membre;
    private TableReservations reservation;

    /**
     * Creation d'une instance
     */
    public GestionMembre(TableMembres membre, TableReservations reservation) throws BiblioException
    {
        this.cx = membre.getConnexion();
        if (membre.getConnexion() != reservation.getConnexion())
            throw new BiblioException("Les instances de TableMembres et de TableReservations n'utilisent pas la même connexion au serveur");
        this.membre = membre;
        this.reservation = reservation;
    }
    
    public boolean existe(String utilisateur)
            throws SQLException
    {
        return this.membre.existe(utilisateur);
    }
    
    public boolean informationsConnexionValide(String utilisateur, String motDePasse) 
            throws SQLException, BiblioException
    {
        try
        {
            // Vérifie si le membre existe déja
            if (!membre.existe(utilisateur))
                throw new BiblioException("Aucun utilisateur n'existe avec ce nom.");


            TupleMembre user = membre.getMembre(utilisateur);
            if(!user.getMotDePasse().equals(motDePasse))
                throw new BiblioException("Mauvais mot de passe.");
            
            // Commit
            cx.commit();
            
            return true;
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    
    public boolean utilisateurEstAdministrateur(String utilisateur)
            throws SQLException, BiblioException
    {
        try
        {
            TupleMembre m = membre.getMembre(utilisateur);
            if(m == null)
                throw new BiblioException("L'utilisateur n'existe pas");
            
            cx.commit();
            return m.getNiveauAcces() == 0;
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Ajout d'un nouveau membre dans la base de donnees. S'il existe deja, une
     * exception est levee.
     */
    public void inscrire(String utilisateur, String motDePasse, int acces, String nom, long telephone, int limitePret)
            throws SQLException, BiblioException, Exception
    {
        try
        {
            // Vérifie si le membre existe déja
            if (membre.existe(utilisateur))
                throw new BiblioException("L'utilisateur " + utilisateur + " existe déjà.");

            if(limitePret > 10 || limitePret < 0)
                throw new BiblioException("La limite de prêt doit être compris entre 0 et 10 inclusivement");
            
            if(acces != 0 && acces != 1)
                throw new BiblioException("Le niveau d'accès doit être 0 (administrateur) ou 1 (membre).");
            
            // Ajout du membre.
            membre.inscrire(utilisateur, motDePasse, acces, nom, telephone, limitePret);
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Suppression d'un membre de la base de donnees.
     */
    public void desinscrire(String utilisateur) throws SQLException, BiblioException, Exception
    {
        try
        {
            // Vérifie si le membre existe et son nombre de pret en cours
            TupleMembre tupleMembre = membre.getMembre(utilisateur);
            if (tupleMembre == null)
                throw new BiblioException("Membre inexistant: " + utilisateur);
            if (tupleMembre.getNbPret() > 0)
                throw new BiblioException("Le membre " + utilisateur + " a encore des prets.");
            if (reservation.getReservationMembre(utilisateur) != null)
                throw new BiblioException("Membre " + utilisateur + " a des r�servations");

            // Suppression du membre
            int nb = membre.desinscrire(utilisateur);
            if (nb == 0)
                throw new BiblioException("Membre " + utilisateur + " inexistant");
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
    
    public List<TupleMembre> getListeMembres(boolean avecAdmin) throws SQLException, BiblioException
    {
        try
        {
            List<TupleMembre> membres = membre.getListeMembres(avecAdmin);
            cx.commit();
            return membres;
        }
        catch(Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}// class
