package Bibliotheque;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Gestion des transactions d'interrogation dans une bibliothèque.
 * 
 * <pre>
 * 
 *  
 *   Marc Frappier - 83 427 378
 *   Université de Sherbrooke
 *   version 2.0 - 13 novembre 2004
 *   ift287 - exploitation de bases de données
 *  
 *   Ce programme permet de faire diverses interrogations
 *   sur l'état de la bibliothèque.
 *  
 *   Pré-condition
 *     la base de données de la bibliothèque doit exister
 *  
 *   Post-condition
 *     le programme effectue les maj associées à chaque
 *     transaction
 * 
 * 
 * </pre>
 */

public class GestionInterrogation
{
    private PreparedStatement stmtLivresRetard;
    private PreparedStatement stmtLivresTitreMot;
    private PreparedStatement stmtListeTousLivres;
    private PreparedStatement stmtListeTousLivresMembre;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionInterrogation(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtLivresRetard = cx.getConnection().prepareStatement("SELECT idlivre, titre, auteur, datePret, "
                + "utilisateur, to_date(?,'YYYY-MM-DD') - datePret - 14 as retard FROM livre "
                + "WHERE utilisateur = ? AND to_date(?,'YYYY-MM-DD') - datePret > 14 "
                + "ORDER BY retard");

        stmtLivresTitreMot = cx.getConnection()
                .prepareStatement("select t1.idLivre, t1.titre, t1.auteur, t1.utilisateur, t1.datePret + 14 "
                        + "from livre t1 " + "where lower(titre) like ?");

        stmtListeTousLivres = cx.getConnection().prepareStatement(
                "select t1.idLivre, t1.titre, t1.auteur, t1.utilisateur, t1.datePret " + "from livre t1");
        
        stmtListeTousLivresMembre = cx.getConnection().prepareStatement(
                "SELECT idLivre, titre, auteur, utilisateur, datePret FROM livre WHERE utilisateur = ?");
    }

    /**
     * Affiche les livres prêtés depuis plus de 14 jours.
     */
    public List<TupleLivre> listerLivresRetard(String userID, String date) throws SQLException
    {
        List<TupleLivre> livres = new LinkedList<TupleLivre>();
        
        stmtLivresRetard.setString(1, date);
        stmtLivresRetard.setString(2, userID);
        stmtLivresRetard.setString(3, date);
        ResultSet rset = stmtLivresRetard.executeQuery();

        while (rset.next())
        {
            TupleLivre livre = new TupleLivre(rset.getInt("idLivre"), rset.getString("titre"), rset.getString("auteur"), rset.getDate("dateAcquisition"),  rset.getString("utilisateur"),  rset.getDate("datePret"));
            livres.add(livre);
        }
        rset.close();
        cx.commit();
        return livres;
    }

    /**
     * Affiche les livres contenu un mot dans le titre
     */
    public void listerLivresTitre(String mot) throws SQLException
    {
        stmtLivresTitreMot.setString(1, "%" + mot + "%");
        ResultSet rset = stmtLivresTitreMot.executeQuery();

        int idMembre;
        System.out.println("idLivre titre auteur idMembre dateRetour");
        while (rset.next())
        {
            System.out.print(rset.getInt(1) + " " + rset.getString(2) + " " + rset.getString(3));
            idMembre = rset.getInt(4);
            if (!rset.wasNull())
            {
                System.out.print(" " + idMembre + " " + rset.getDate(5));
            }
            System.out.println();
        }
        cx.commit();
    }

    /**
     * Affiche tous les livres de la BD
     */
    public List<TupleLivre> listerLivres() throws SQLException
    {
        List<TupleLivre> livres = new LinkedList<TupleLivre>();
        
        ResultSet rset = stmtListeTousLivres.executeQuery();
        while (rset.next())
        {
            TupleLivre livre = new TupleLivre(rset.getInt("idLivre"), rset.getString("titre"), rset.getString("auteur"), rset.getDate("dateAcquisition"),  rset.getString("utilisateur"),  rset.getDate("datePret"));
            livres.add(livre);
        }
        rset.close();
        cx.commit();
        return livres;
    }
    
    /**
     * Affiche tous les livres de la BD pour un membre
     */
    public List<TupleLivre> listerLivresMembre(String utilisateur) throws SQLException
    {
        List<TupleLivre> livres = new LinkedList<TupleLivre>();
        
        stmtListeTousLivresMembre.setString(1, utilisateur);
        ResultSet rset = stmtListeTousLivresMembre.executeQuery();
        while (rset.next())
        {
            TupleLivre livre = new TupleLivre(rset.getInt("idLivre"), rset.getString("titre"), rset.getString("auteur"), rset.getDate("dateAcquisition"),  rset.getString("utilisateur"),  rset.getDate("datePret"));
            livres.add(livre);
        }
        rset.close();
        cx.commit();
        return livres;
    }
}
