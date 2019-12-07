package Bibliotheque;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Permet d'effectuer les accès à la table livre.
 * 
 * <pre>
 * 
 * Marc Frappier - 83 427 378
 * Université de Sherbrooke
 * version 2.0 - 13 novembre 2004
 * ift287 - exploitation de bases de données
 * 
 * Vincent Ducharme
 * Université de Sherbrooke
 * Version 3.0 - 17 juin 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe gère tous les accès à la table livre.
 * 
 * </pre>
 */

public class TableLivres
{
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdate;
    private PreparedStatement stmtDelete;
    private PreparedStatement stmtListePret;
    private Connexion cx;

    /**
     * Creation d'une instance. Des �nonc�s SQL pour chaque requ�te sont
     * pr�compil�s.
     */
    public TableLivres(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtExiste = cx.getConnection().prepareStatement(
                "select idlivre, titre, auteur, dateAcquisition, utilisateur, datePret from livre where idlivre = ?");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into livre (idLivre, titre, auteur, dateAcquisition, utilisateur, datePret) "
                        + "values (?,?,?,?,null,null)");
        stmtUpdate = cx.getConnection()
                .prepareStatement("update livre set utilisateur = ?, datePret = ? " + "where idLivre = ?");
        stmtDelete = cx.getConnection().prepareStatement("delete from livre where idlivre = ?");
        stmtListePret = cx.getConnection()
                .prepareStatement("select idLivre, titre, auteur, dateAcquisition, utilisateur, datePret "
                        + "from livre where utilisateur = ?");
    }

    /**
     * Retourner la connexion associ�e.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Verifie si un livre existe.
     */
    public boolean existe(int idLivre) throws SQLException
    {
        stmtExiste.setInt(1, idLivre);
        ResultSet rset = stmtExiste.executeQuery();
        boolean livreExiste = rset.next();
        rset.close();
        return livreExiste;
    }

    /**
     * Lecture d'un livre.
     */
    public TupleLivre getLivre(int idLivre) throws SQLException
    {
        stmtExiste.setInt(1, idLivre);
        ResultSet rset = stmtExiste.executeQuery();
        TupleLivre tupleLivre = null;
        if (rset.next())
        {
            String titre = rset.getString(2);
            String auteur = rset.getString(3);
            Date dateAc = rset.getDate(4);
            String utilisateur = rset.getString(5);
            Date datePret = rset.getDate(6);
            tupleLivre = new TupleLivre(idLivre, titre, auteur, dateAc, utilisateur, datePret);
            rset.close();
        }
        return tupleLivre;
    }

    /**
     * Ajout d'un nouveau livre dans la base de donnees.
     */
    public void acquerir(int idLivre, String titre, String auteur, String dateAcquisition) throws SQLException
    {
        /* Ajout du livre. */
        stmtInsert.setInt(1, idLivre);
        stmtInsert.setString(2, titre);
        stmtInsert.setString(3, auteur);
        stmtInsert.setDate(4, Date.valueOf(dateAcquisition));
        stmtInsert.executeUpdate();
    }

    /**
     * Enregistrement de l'emprunteur d'un livre.
     */
    public int preter(int idLivre, String utilisateur, String datePret) throws SQLException
    {
        /* Enregistrement du pret. */
        stmtUpdate.setString(1, utilisateur);
        stmtUpdate.setDate(2, Date.valueOf(datePret));
        stmtUpdate.setInt(3, idLivre);
        return stmtUpdate.executeUpdate();
    }

    /**
     * Rendre le livre disponible (non-pr�t�)
     */
    public int retourner(int idLivre) throws SQLException
    {
        /* Enregistrement du pret. */
        stmtUpdate.setNull(1, Types.VARCHAR);
        stmtUpdate.setNull(2, Types.DATE);
        stmtUpdate.setInt(3, idLivre);
        return stmtUpdate.executeUpdate();
    }

    /**
     * Suppression d'un livre.
     */
    public int vendre(int idLivre) throws SQLException
    {
        /* Suppression du livre. */
        stmtDelete.setInt(1, idLivre);
        return stmtDelete.executeUpdate();
    }

    public List<TupleLivre> calculerListePret(String utilisateur) throws SQLException
    {
        stmtListePret.setString(1, utilisateur);
        ResultSet rset = stmtListePret.executeQuery();
        List<TupleLivre> listePret = new LinkedList<TupleLivre>();
        while (rset.next())
        {
            TupleLivre livre = new TupleLivre(rset.getInt("idLivre"),
                                              rset.getString("titre"),
                                              rset.getString("auteur"),
                                              rset.getDate("dateAcquisition"), 
                                              rset.getString("utilisateur"), 
                                              rset.getDate("datePret"));
            
            listePret.add(livre);
        }
        rset.close();
        return listePret;
    }
}
