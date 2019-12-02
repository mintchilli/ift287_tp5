package JardinCollectif.DataAcces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class MembreAccess {

	Connexion conn;

	public MembreAccess(Connexion cx) {
		conn = cx;
	}

	public boolean inscrireMembre(String prenom, String nom, String motDePasse) {
		try {
			PreparedStatement s = conn.getConnection()
					.prepareStatement("insert into Membre(nom, prenom, nomutilisateur, motDePasse) values(?,?,?,?)");
			s.setString(1, nom);
			s.setString(2, prenom);
			s.setString(3, prenom+nom);
			s.setString(4, motDePasse);

			s.execute();

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
				String data = "Pr�nom : ";
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

	public String getMembre(int noMembre) {
		try {
			PreparedStatement s = conn.getConnection().prepareStatement("SELECT nom, prenom, estadmin FROM membre WHERE nomembre = ?");

			s.setInt(1, noMembre);
			s.execute();
			
			ResultSet rs = s.getResultSet();

			String data = "";

			while (rs.next()) {
				data = "Pr�nom : ";
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

}
