package Bibliotheque;

import java.sql.*;

/**
 * Gestion des transactions de reliées aux prêts de livres aux membres dans une
 * bibliothèque.
 *
 * <pre>
 * Marc Frappier - 83 427 378
 * Université de Sherbrooke
 * version 2.0 - 13 novembre 2004
 * ift287 - exploitation de bases de données
 * 
 * Vincent Ducharme
 * Université de Sherbrooke
 * version 3.0 - 17 juin 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet de gérer les transactions prêter,
 * renouveler et retourner.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */

public class GestionPret
{
    private TableLivres livre;
    private TableMembres membre;
    private TableReservations reservation;
    private Connexion cx;

    /**
     * Creation d'une instance. La connection de l'instance de livre et de
     * membre doit être la même que cx, afin d'assurer l'intégrité des
     * transactions.
     */
    public GestionPret(TableLivres livre, TableMembres membre, TableReservations reservation) throws BiblioException
    {
        if (livre.getConnexion() != membre.getConnexion() || reservation.getConnexion() != membre.getConnexion())
            throw new BiblioException(
                    "Les instances de livre, de membre et de reservation n'utilisent pas la même connexion au serveur");
        this.cx = livre.getConnexion();
        this.livre = livre;
        this.membre = membre;
        this.reservation = reservation;
    }

    /**
     * Pret d'un livre à un membre. Le livre ne doit pas être prêté. Le membre
     * ne doit pas avoir dépassé sa limite de pret.
     */
    public void preter(int idLivre, String utilisateur, String datePret) throws SQLException, BiblioException, Exception
    {
        try
        {
            // Verfier si le livre est disponible
            TupleLivre tupleLivre = livre.getLivre(idLivre);
            if (tupleLivre == null)
                throw new BiblioException("Livre inexistant: " + idLivre);
            if (!tupleLivre.isUtilisateurNull())
                throw new BiblioException("Livre " + idLivre + " deja prete a " + tupleLivre.getUtilisateur());

            // Vérifie si le membre existe et sa limite de pret
            TupleMembre tupleMembre = membre.getMembre(utilisateur);
            if (tupleMembre == null)
                throw new BiblioException("Membre inexistant: " + utilisateur);
            if (tupleMembre.getNbPret() >= tupleMembre.getLimitePret())
                throw new BiblioException("Limite de pret du membre " + utilisateur + " atteinte");

            // Vérifie s'il existe une réservation pour le livre
            TupleReservation tupleReservation = reservation.getReservationLivre(idLivre);
            if (tupleReservation != null)
                throw new BiblioException("Livre réservé par : " + tupleReservation.getUtilisateur() + " idReservation : "
                        + tupleReservation.getIdReservation());

            // Enregistrement du pret.
            int nb1 = livre.preter(idLivre, utilisateur, datePret);
            if (nb1 == 0)
                throw new BiblioException("Livre supprimé par une autre transaction");
            int nb2 = membre.preter(utilisateur);
            if (nb2 == 0)
                throw new BiblioException("Membre supprimé par une autre transaction");
            
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
     * Renouvellement d'un pret. Le livre doit être prêté. Le livre ne doit pas
     * être réservé.
     */
    public void renouveler(int idLivre, String datePret) throws SQLException, BiblioException, Exception
    {
        try
        {
            // Verifier si le livre est prêté
            TupleLivre tupleLivre = livre.getLivre(idLivre);
            if (tupleLivre == null)
                throw new BiblioException("Livre inexistant: " + idLivre);
            if (tupleLivre.isUtilisateurNull())
                throw new BiblioException("Livre " + idLivre + " n'est pas prete");

            // Verifier si date renouvellement >= datePret
            if (Date.valueOf(datePret).before(tupleLivre.getDatePret()))
                throw new BiblioException("Date de renouvellement inferieure à la date de pret");

            // Vérifie s'il existe une réservation pour le livre
            TupleReservation tupleReservation = reservation.getReservationLivre(idLivre);
            if (tupleReservation != null)
                throw new BiblioException("Livre réservé par : " + tupleReservation.getUtilisateur() + " idReservation : "
                        + tupleReservation.getIdReservation());

            // Enregistrement du pret.
            int nb1 = livre.preter(idLivre, tupleLivre.getUtilisateur(), datePret);
            if (nb1 == 0)
                throw new BiblioException("Livre supprime par une autre transaction");
            
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
     * Retourner un livre prêté Le livre doit être prêté.
     */
    public void retourner(int idLivre, String dateRetour) throws SQLException, BiblioException, Exception
    {
        try
        {
            // Verifier si le livre est prêté
            TupleLivre tupleLivre = livre.getLivre(idLivre);
            if (tupleLivre == null)
                throw new BiblioException("Livre inexistant: " + idLivre);
            if (tupleLivre.isUtilisateurNull())
                throw new BiblioException("Livre " + idLivre + " n'est pas prêté ");

            // Verifier si date retour >= datePret
            if (Date.valueOf(dateRetour).before(tupleLivre.getDatePret()))
                throw new BiblioException("Date de retour inferieure à la date de pret");

            // Retour du pret.
            int nb1 = livre.retourner(idLivre);
            if (nb1 == 0)
                throw new BiblioException("Livre supprimé par une autre transaction");

            int nb2 = membre.retourner(tupleLivre.getUtilisateur());
            if (nb2 == 0)
                throw new BiblioException("Livre supprimé par une autre transaction");
            
            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}
