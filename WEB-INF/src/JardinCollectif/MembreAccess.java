package JardinCollectif;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MembreAccess {

	Connexion conn;

	public MembreAccess(Connexion cx) {
		conn = cx;
	}

	public boolean inscrireMembre(String prenom, String nom, String motDePasse, boolean acces) {
		try {
			PreparedStatement s = conn.getConnection()
					.prepareStatement("insert into Membre(nom, prenom, motDePasse, estAdmin) values(?,?,?,?)");
			s.setString(1, nom);
			s.setString(2, prenom);
			s.setString(3, motDePasse);
			s.setBoolean(4, acces);

			s.execute();
			conn.commit();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean supprimerMembre(int noMembre) {
		try {
			PreparedStatement s = conn.getConnection().prepareStatement("delete from Membre where nomembre = ?");
			s.setInt(1, noMembre);
			s.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean makeAdmin(int noMembre) {
		try {
			PreparedStatement s = conn.getConnection()
					.prepareStatement("UPDATE membre SET estadmin = 'true' WHERE nomembre = ?");
			s.setInt(1, noMembre);
			s.execute();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public ArrayList<Integer> getMembreLots(int noMembre) {
		ArrayList<Integer> ret = new ArrayList<Integer>();
		try {
			PreparedStatement s = conn.getConnection()
					.prepareStatement("SELECT idlot FROM membrelot WHERE nomembre = ?");

			s.setInt(1, noMembre);

			s.execute();
			ResultSet rs = s.getResultSet();

			while (rs.next()) {
				ret.add(rs.getInt("idlot"));
			}

			return ret;

		} catch (SQLException e) {
			e.printStackTrace();
			return ret;
		}

	}

	public int getMembreCount(int idLot) {
		try {
			PreparedStatement s = conn.getConnection()
					.prepareStatement("SELECT count(*) FROM membrelot WHERE idlot = ?");

			s.setInt(1, idLot);

			s.execute();
			ResultSet rs = s.getResultSet();

			rs.next();

			return rs.getInt("count");

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public ArrayList<String> getMembreList() {
		try {
			PreparedStatement s = conn.getConnection().prepareStatement("SELECT nom, prenom, estadmin FROM membre;");

			s.execute();
			ResultSet rs = s.getResultSet();

			ArrayList<String> ret = new ArrayList<String>();

			while (rs.next()) {
				String data = "Prï¿½nom : ";
				data += rs.getString("prenom");
				data += ", Nom : ";
				data += rs.getString("nom");
				data += ", Est un administrateur: ";
				if (rs.getBoolean("estadmin"))
					data += "oui";
				else
					data += "non";
				ret.add(data);
			}

			return ret;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Membre> getMembres() {
		try {
			PreparedStatement s = conn.getConnection().prepareStatement("SELECT * FROM membre;");

			s.execute();
			ResultSet rs = s.getResultSet();

			ArrayList<Membre> ret = new ArrayList<Membre>();

			while (rs.next()) {
				Membre m = new Membre();
				m.setPrenom(rs.getString("prenom"));
				m.setNom(rs.getString("nom"));
				if (rs.getBoolean("estadmin"))
					m.setIsAdmin(true);
				else
					m.setIsAdmin(false);
				m.setPassword(rs.getString("motdepasse"));
				m.setId(rs.getInt("nomembre"));

				ret.add(m);
			}

			return ret;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getMembre(int noMembre) {
		try {
			PreparedStatement s = conn.getConnection().prepareStatement("SELECT nom, prenom, estadmin FROM membre WHERE nomembre = ?");

			s.setInt(1, noMembre);
			s.execute();
			
			ResultSet rs = s.getResultSet();

			String data = "";

			while (rs.next()) {
				data = "Prï¿½nom : ";
				data += rs.getString("prenom");
				data += ", Nom : ";
				data += rs.getString("nom");
				data += ", Est un administrateur: ";
				if (rs.getBoolean("estadmin"))
					data += "oui";
				else
					data += "non";
			}

			return data;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
    public boolean informationsConnexionValide(String prenom, String nom, String motDePasse) 
            throws SQLException, IFT287Exception
    {
        try
        {
        	
        	PreparedStatement s = conn.getConnection().prepareStatement("SELECT * FROM membre WHERE prenom = ? AND nom = ? AND motdepasse = ?");
        	
			s.setString(1, prenom);
			s.setString(2, nom);
			s.setString(3, motDePasse);
			s.execute();
			
			ResultSet rs = s.getResultSet();
			rs.next();
			
			Membre m = new Membre(rs.getString("prenom"), rs.getString("nom"), rs.getString("motdepasse"), rs.getInt("nomembre"));
            // Vérifie si le membre existe déja
            if (m.getId() == null)
                throw new IFT287Exception("Aucun utilisateur n'existe avec ce nom.");


            if(!m.getPassword().equals(motDePasse))
                throw new IFT287Exception("Mauvais mot de passe.");
            
            return true;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

}
