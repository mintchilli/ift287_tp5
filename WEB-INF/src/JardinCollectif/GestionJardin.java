package JardinCollectif;

import java.sql.*;

import JardinCollectif.Connexion;

/**
 * Syst�me de gestion d'une biblioth�que
 *
 * <pre>
 * Marc Frappier - 83 427 378
 * Universit� de Sherbrooke
 * version 2.0 - 13 novembre 2004
 * ift287 - exploitation de bases de donn�es
 *
 * Vincent Ducharme
 * Universit� de Sherbrooke
 * version 3.0 - 17 juin 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet de g�rer les transaction de base d'une
 * biblioth�que.  Il g�re des livres, des membres et des
 * r�servations. Les donn�es sont conserv�es dans une base de
 * donn�es relationnelles acc�d�e avec JDBC.
 *
 * Pr�-condition
 *   la base de donn�es de la biblioth�que doit exister
 *
 * Post-condition
 *   le programme effectue les maj associ�es � chaque
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
     * @param bd nom de la bade de donn�es
     * @param user user id pour �tablir une connexion avec le serveur SQL
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
