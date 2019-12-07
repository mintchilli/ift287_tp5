package Bibliotheque;

import java.sql.*;

/**
 * Permet d'effectuer les accès à la table reservation.
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
 * version 3.0 - 17 juin 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Cette classe gère tous les accès à la table reservation.
 *
 * </pre>
 */

public class TableReservations
{
    private PreparedStatement stmtExiste;
    private PreparedStatement stmtExisteLivre;
    private PreparedStatement stmtExisteMembre;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtDelete;
    private Connexion cx;

    /**
     * Creation d'une instance.
     */
    public TableReservations(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtExiste = cx.getConnection().prepareStatement("select idReservation, idLivre, utilisateur, dateReservation "
                + "from reservation where idReservation = ?");
        stmtExisteLivre = cx.getConnection()
                .prepareStatement("select idReservation, idLivre, utilisateur, dateReservation "
                        + "from reservation where idLivre = ? " + "order by dateReservation");
        stmtExisteMembre = cx.getConnection().prepareStatement(
                "select idReservation, idLivre, utilisateur, dateReservation " + "from reservation where utilisateur = ? ");
        stmtInsert = cx.getConnection()
                .prepareStatement("insert into reservation (idReservation, idlivre, utilisateur, dateReservation) "
                        + "values (?,?,?,to_date(?,'YYYY-MM-DD'))");
        stmtDelete = cx.getConnection().prepareStatement("delete from reservation where idReservation = ?");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Verifie si une reservation existe.
     */
    public boolean existe(int idReservation) throws SQLException
    {
        stmtExiste.setInt(1, idReservation);
        ResultSet rset = stmtExiste.executeQuery();
        boolean reservationExiste = rset.next();
        rset.close();
        return reservationExiste;
    }

    /**
     * Lecture d'une reservation.
     */
    public TupleReservation getReservation(int idReservation) throws SQLException
    {
        stmtExiste.setInt(1, idReservation);
        ResultSet rset = stmtExiste.executeQuery();
        TupleReservation tupleReservation = null;
        if (rset.next())
        {
            int idLivre = rset.getInt(2);
            String utilisateur = rset.getString(3);
            Date date = rset.getDate(4);
            tupleReservation = new TupleReservation(idReservation, idLivre, utilisateur, date);
        }
        return tupleReservation;
    }

    /**
     * Lecture de la première reservation d'un livre.
     */
    public TupleReservation getReservationLivre(int idLivre) throws SQLException
    {
        stmtExisteLivre.setInt(1, idLivre);
        ResultSet rset = stmtExisteLivre.executeQuery();
        TupleReservation tupleReservation = null;
        if (rset.next())
        {
            int idRes = rset.getInt(1);
            String utilisateur = rset.getString(3);
            Date date = rset.getDate(4);
            tupleReservation = new TupleReservation(idRes, idLivre, utilisateur, date);
        }
        return tupleReservation;
    }

    /**
     * Lecture de la première reservation d'un livre.
     */
    public TupleReservation getReservationMembre(String utilisateur) throws SQLException
    {
        stmtExisteMembre.setString(1, utilisateur);
        ResultSet rset = stmtExisteMembre.executeQuery();
        TupleReservation tupleReservation = null;
        if (rset.next())
        {
            int idRes = rset.getInt(1);
            int idLivre = rset.getInt(2);
            Date date = rset.getDate(4);
            tupleReservation = new TupleReservation(idRes, idLivre, utilisateur, date);
        }
        return tupleReservation;
    }

    /**
     * Réservation d'un livre.
     */
    public void reserver(int idReservation, int idLivre, String utilisateur, String dateReservation) throws SQLException
    {
        stmtInsert.setInt(1, idReservation);
        stmtInsert.setInt(2, idLivre);
        stmtInsert.setString(3, utilisateur);
        stmtInsert.setString(4, dateReservation);
        stmtInsert.executeUpdate();
    }

    /**
     * Suppression d'une reservation.
     */
    public int annulerRes(int idReservation) throws SQLException
    {
        stmtDelete.setInt(1, idReservation);
        return stmtDelete.executeUpdate();
    }
}
