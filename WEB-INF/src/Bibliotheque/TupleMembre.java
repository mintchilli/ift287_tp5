package Bibliotheque;

/**
 * Permet de représenter un tuple de la table membre.
 * 
 * <pre>
 * Marc Frappier
 * Université de Sherbrooke
 * Version 2.0 - 13 novembre 2004
 * IFT287 - Exploitation de BD relationnelles et OO
 *
 * Vincent Ducharme
 * Université de Sherbrooke
 * Version 3.0 - 17 juin 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Vincent Ducharme
 * Université de Sherbrooke
 * Version 4.0 - 10 novembre 2018
 * IFT287 - Exploitation de BD relationnelles et OO
 * </pre>
 */

public class TupleMembre
{
    private String utilisateurName;
    private String motDePasse;
    private String nom;
    private long telephone;
    private int limitePret;
    private int nbPret;
    private int acces;
    
    public TupleMembre()
    {
    }
    
    public TupleMembre(String utilisateur, String motDePasse, int acces, String nom, long telephone, int limitePret, int nbPret)
    {
        this.setUtilisateurName(utilisateur);
        this.setMotDePasse(motDePasse);
        this.setNiveauAcces(acces);
        this.setNom(nom);
        this.setTelephone(telephone);
        this.setLimitePret(limitePret);
        this.setNbPret(nbPret);
    }
    
    /**
     * @return Le niveau d'accès du membre
     */
    public int getNiveauAcces()
    {
        return acces;
    }
    
    /**
     * @param niveau Le nouveau niveau d'accès du membre
     */
    public void setNiveauAcces(int niveau)
    {
        this.acces = niveau;
    }
    
    /**
     * @return Le nom d'utilisateur du membre
     */
    public String getUtilisateurName()
    {
        return utilisateurName;
    }
    
    /**
     * @param utilisateurName Le nouveau nom d'utilisateur du membre
     */
    public void setUtilisateurName(String utilisateurName)
    {
        this.utilisateurName = utilisateurName;
    }
    
    /**
     * @return le mot de passe du membre
     */
    public String getMotDePasse()
    {
        return motDePasse;
    }
    
    /**
     * @param motDePasse Le nouveau mot de passe
     */
    public void setMotDePasse(String motDePasse)
    {
        this.motDePasse = motDePasse;
    }
    
    /**
     * @return Le nom du membre
     */
    public String getNom()
    {
        return nom;
    }
    
    /**
     * @param nom Le nouveau nom du membre
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }
    
    /**
     * @return Le téléphone
     */
    public long getTelephone()
    {
        return telephone;
    }
    /**
     * @param telephone Le nouveau téléphone
     */
    public void setTelephone(long telephone)
    {
        this.telephone = telephone;
    }
    
    /**
     * @return Le nombre maximum de prêts
     */
    public int getLimitePret()
    {
        return limitePret;
    }
    
    /**
     * @param limitePret La nouvelle limite de prêts
     */
    public void setLimitePret(int limitePret)
    {
        this.limitePret = limitePret;
    }
    
    /**
     * @return Le nombre de prêt courant
     */
    public int getNbPret()
    {
        return nbPret;
    }
    
    /**
     * @param nbPret Le nouveau nombre de prêt courant
     */
    public void setNbPret(int nbPret)
    {
        this.nbPret = nbPret;
    }
}
