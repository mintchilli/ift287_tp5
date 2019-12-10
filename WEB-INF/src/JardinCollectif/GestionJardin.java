package JardinCollectif;

import java.sql.*;

import JardinCollectif.Connexion;

/**
 * Système de gestion d'une bibliothèque
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
 * Ce programme permet de gérer les transaction de base d'une
 * bibliothèque.  Il gère des livres, des membres et des
 * réservations. Les données sont conservées dans une base de
 * données relationnelles accédée avec JDBC.
 *
 * Pré-condition
 *   la base de données de la bibliothèque doit exister
 *
 * Post-condition
 *   le programme effectue les maj associées à chaque
 *   transaction
 * </pre>
 */
public class GestionJardin
{
    private Connexion cx;
    private LotAccess gestionLot;
    private MembreAccess gestionMembre;
    private PlanteAccess gestionPlante;

    /**
     * Ouvre une connexion avec la BD relationnelle et alloue les gestionnaires
     * de transactions et de tables.
     * 
     * <pre>
     * 
     * @param serveur SQL
     * @param bd nom de la bade de données
     * @param user user id pour établir une connexion avec le serveur SQL
     * @param password mot de passe pour le user id
     * </pre>
     * @throws IFT287Exception 
     */
    public GestionJardin(String serveur, String bd, String user, String password)
            throws  SQLException, IFT287Exception
    {
        // allocation des objets pour le traitement des transactions
        cx = new Connexion(serveur, bd, user, password);

        setGestionMembre(new MembreAccess(cx));
        setGestionLot(new LotAccess(cx));
        setGestionPlante(new PlanteAccess(cx));
    }

    public void fermer() throws SQLException
    {
        // fermeture de la connexion
        getConnexion().fermer();
    }

    /**
	 * @return the Connexion
	 */
	public Connexion getConnexion()
	{
		return cx;
	}

    /**
     * @return the gestionMembre
     */
    public MembreAccess getGestionMembre()
    {
        return gestionMembre;
    }

    /**
     * @param gestionMembre the gestionMembre to set
     */
    private void setGestionMembre(MembreAccess gestionMembre)
    {
        this.gestionMembre = gestionMembre;
    }
    
    public LotAccess getGestionLot() 
    {
        return gestionLot;
    }
    
    private void setGestionLot(LotAccess gestionLot)
    {
        this.gestionLot = gestionLot;
    }

    public PlanteAccess getGestionPlante() 
    {
        return gestionPlante;
    }
    
    private void setGestionPlante(PlanteAccess gestionPlante)
    {
        this.gestionPlante = gestionPlante;
    }
}
