package Bibliotheque;

import java.sql.*;

/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.
 * 
 * <pre>
 * Marc Frappier - 83 427 378
 * Universit� de Sherbrooke
 * version 2.0 - 13 novembre 2004
 * ift287 - exploitation de bases de donn�es
 * 
 * Vincent Ducharme
 * Universit� de Sherbrooke
 * version 3.0 - 21 mai 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme ouvre une connexion avec une BD via JDBC.
 * La m�thode serveursSupportes() indique les serveurs support�s.
 * 
 * Pr�-condition
 *   le driver JDBC appropri� doit �tre accessible.
 * 
 * Post-condition
 *   la connexion est ouverte en mode autocommit false et s�rialisable, 
 *   (s'il est support� par le serveur).
 * </pre>
 */
public class Connexion
{
    private Connection conn;

    /**
     * Ouverture d'une connexion en mode autocommit false et s�rialisable (si
     * support�)
     * 
     * @serveur serveur SQL de la BD
     * @bd nom de la base de donn�es
     * @user userid sur le serveur SQL
     * @pass mot de passe sur le serveur SQL
     */
    public Connexion(String serveur, String bd, String user, String pass) throws BiblioException, SQLException
    {
        Driver d;
        try
        {
            d = (Driver)Class.forName("org.postgresql.Driver").newInstance();
            DriverManager.registerDriver(d);
            
            if (serveur.equals("local"))
            {
                conn = DriverManager.getConnection("jdbc:postgresql:" + bd, user, pass);
            }
            else if (serveur.equals("dinf"))
            {
                conn = DriverManager.getConnection("jdbc:postgresql://bd-info2.dinf.usherbrooke.ca:5432/" + bd + "?ssl=true&sslmode=require", user, pass);
            }
            else
            {
                throw new BiblioException("Serveur inconnu");
            }

            // mettre en mode de commit manuel
            conn.setAutoCommit(false);

            // mettre en mode s�rialisable si possible
            // (plus haut niveau d'integrit� l'acc�s concurrent aux donn�es)
            DatabaseMetaData dbmd = conn.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
            {
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                System.out.println("Ouverture de la connexion en mode s�rialisable :\n" + "Estampille "
                        + System.currentTimeMillis() + " " + conn);
            }
            else
            {
                System.out.println("Ouverture de la connexion en mode read committed (default) :\n" + "Heure "
                        + System.currentTimeMillis() + " " + conn);
            }
        } // try
        catch (SQLException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
            throw new SQLException("JDBC Driver non instanci�");
        }
    }

    /**
     * fermeture d'une connexion
     */
    public void fermer() throws SQLException
    {
        conn.rollback();
        conn.close();
        System.out.println("Connexion ferm�e " + conn);
    }

    /**
     * commit
     */
    public void commit() throws SQLException
    {
        conn.commit();
    }

    public void setIsolationReadCommited() throws SQLException
    {
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    }

    /**
     * rollback
     */
    public void rollback() throws SQLException
    {
        conn.rollback();
    }

    /**
     * retourne la Connection jdbc
     */
    public Connection getConnection()
    {
        return conn;
    }

    public void setAutoCommit(boolean m) throws SQLException
    {
        conn.setAutoCommit(false);
    }

    /**
     * Retourne la liste des serveurs support�s par ce gestionnaire de
     * connexions
     */
    public static String serveursSupportes()
    {
        return "local : PostgreSQL install� localement\n"
             + "dinf  : PostgreSQL install� sur les serveurs du d�partement\n";
    }
}// Classe Connexion
