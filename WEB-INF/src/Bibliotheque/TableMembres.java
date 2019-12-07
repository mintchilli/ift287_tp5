package Bibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet d'effectuer les accès à la table membre.
 * 
 * <pre>
 *
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
 * Cette classe gère tous les accès à la table membre.
 *
 * </pre>
 */

public class TableMembres
{
    private PreparedStatement stmtExisteUtilisateur;
    private PreparedStatement stmtListeUtilisateur;
    private PreparedStatement stmtInsert;
    private PreparedStatement stmtUpdateIncrNbPret;
    private PreparedStatement stmtUpdateDecNbPret;
    private PreparedStatement stmtDelete;
    private Connexion cx;

    public TableMembres(Connexion cx) throws SQLException
    {
        this.cx = cx;
        stmtListeUtilisateur = cx.getConnection().prepareStatement(
                "SELECT utilisateur, motDePasse, acces, nom, telephone, limitePret, nbpret FROM membre WHERE acces = ?");
        stmtExisteUtilisateur = cx.getConnection().prepareStatement(
                "SELECT utilisateur, motDePasse, acces, nom, telephone, limitePret, nbpret FROM membre WHERE utilisateur = ?");
        stmtInsert = cx.getConnection().prepareStatement(
                "INSERT INTO membre (utilisateur, motDePasse, acces, nom, telephone, limitepret, nbpret) "
                        + "VALUES (?,?,?,?,?,?,0)");
        stmtUpdateIncrNbPret = cx.getConnection()
                .prepareStatement("UPDATE membre SET nbpret = nbpret + 1 WHERE utilisateur = ?");
        stmtUpdateDecNbPret = cx.getConnection()
                .prepareStatement("UPDATE membre SET nbpret = nbpret - 1 WHERE utilisateur = ?");
        stmtDelete = cx.getConnection().prepareStatement("DELETE FROM membre WHERE utilisateur = ?");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Verifie si un membre existe.
     */
    public boolean existe(String utilisateur) throws SQLException
    {
        stmtExisteUtilisateur.setString(1, utilisateur);
        ResultSet rset = stmtExisteUtilisateur.executeQuery();
        boolean membreExiste = rset.next();
        rset.close();
        return membreExiste;
    }

    /**
     * Lecture d'un membre à partir du nom d'utilisateur.
     */
    public TupleMembre getMembre(String utilisateur) throws SQLException
    {
        stmtExisteUtilisateur.setString(1, utilisateur);
        ResultSet rset = stmtExisteUtilisateur.executeQuery();
        TupleMembre tupleMembre = null;
        if (rset.next())
        {
            String motDePasse = rset.getString(2);
            int acces = rset.getInt(3);
            String nom = rset.getString(4);
            long telephone = rset.getLong(5);
            int limitePret = rset.getInt(6);
            int nbPret = rset.getInt(7);

            tupleMembre = new TupleMembre(utilisateur, motDePasse, acces, nom, telephone, limitePret, nbPret);
            rset.close();
        }
        return tupleMembre;
    }

    /**
     * Ajout d'un nouveau membre.
     */
    public void inscrire(String utilisateur, String motDePasse, int acces, String nom, long telephone, int limitePret)
            throws SQLException
    {
        stmtInsert.setString(1, utilisateur);
        stmtInsert.setString(2, motDePasse);
        stmtInsert.setInt(3, acces);
        stmtInsert.setString(4, nom);
        stmtInsert.setLong(5, telephone);
        stmtInsert.setInt(6, limitePret);
        stmtInsert.executeUpdate();
    }

    /**
     * Incrementer le nb de pret d'un membre.
     */
    public int preter(String utilisateur) throws SQLException
    {
        stmtUpdateIncrNbPret.setString(1, utilisateur);
        return stmtUpdateIncrNbPret.executeUpdate();
    }

    /**
     * Decrementer le nb de pret d'un membre.
     */
    public int retourner(String utilisateur) throws SQLException
    {
        stmtUpdateDecNbPret.setString(1, utilisateur);
        return stmtUpdateDecNbPret.executeUpdate();
    }

    /**
     * Suppression d'un membre.
     */
    public int desinscrire(String utilisateur) throws SQLException
    {
        stmtDelete.setString(1, utilisateur);
        return stmtDelete.executeUpdate();
    }

    public List<TupleMembre> getListeMembres(boolean avecAdmin) throws SQLException
    {
        List<TupleMembre> membres = new ArrayList<TupleMembre>();
        stmtListeUtilisateur.setInt(1, 1);
        ResultSet rset = stmtListeUtilisateur.executeQuery();
        while (rset.next())
        {
            String motDePasse = rset.getString(2);
            int acces = rset.getInt(3);
            String nom = rset.getString(4);
            long telephone = rset.getLong(5);
            int limitePret = rset.getInt(6);
            int nbPret = rset.getInt(7);

            TupleMembre tupleMembre = new TupleMembre(rset.getString(1), motDePasse, acces, nom, telephone, limitePret, nbPret);
            membres.add(tupleMembre);
        }
        if (avecAdmin)
        {
            stmtListeUtilisateur.setInt(1, 0);
            rset = stmtListeUtilisateur.executeQuery();
            while (rset.next())
            {
                String motDePasse = rset.getString(2);
                int acces = rset.getInt(3);
                String nom = rset.getString(4);
                long telephone = rset.getLong(5);
                int limitePret = rset.getInt(6);
                int nbPret = rset.getInt(7);

                TupleMembre tupleMembre = new TupleMembre(rset.getString(1), motDePasse, acces, nom, telephone, limitePret, nbPret);
                membres.add(tupleMembre);
            }
        }
        rset.close();
        return membres;
    }
}
