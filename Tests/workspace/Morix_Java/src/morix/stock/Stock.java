package morix.stock;

import java.util.ArrayList;

import morix.Morix;
import morix.bdd.ConnexionBDD;
import morix.bdd.ConnexionMySQL;

/**
 * Classe de gestion du stock de Morix. 
 * 
 * @version 3.0
 * @author Matthias Brun
 */
public class Stock
{
	/**
	 * La connexion à la base de données.
	 */
	private ConnexionBDD connexionBDD;

	/**
	 * Constructeur d'un stock.
	 * 
	 * @throws Exception toute exception. 
	 *
	 */ 
	public Stock() throws Exception
	{		
		// Connexion à la base de données.
		try {
			this.connexionBDD = new ConnexionMySQL(
				Morix.CONFIGURATION.getString("BDD_URL").trim(),
				Morix.CONFIGURATION.getString("BDD_USER").trim(),
				Morix.CONFIGURATION.getString("BDD_PASSWORD").trim()
			);
		}
		catch (Exception ex) {
			System.err.println("Problème de connexion à la base de données.");
			throw ex;
		}
	}

	/**
	 * Donne un produit à partir de son identifiant.
	 * 
	 * @param id l'identifiant du produit.
	 * @return le produit dont l'identifiant est id, 
	 *         <code>null</code> si le produit n'est pas dans la base de données.
	 *
	 * @throws Exception toute exception.
	 *
	 */
	public Produit donneProduit(String id) throws Exception
	{
		try {
			return this.connexionBDD.donneProduit(id);
		}
		catch (Exception ex) {
			System.err.println("Problème d'accès à un produit du stock.");
			throw ex;
		}
	}
	
	/**
	 * Donne tous les produits de la base de données.
	 * 
	 * @return l'ensemble des produits présents dans le stock.
	 *
	 * @throws Exception toute exception.
	 *
	 */
	public ArrayList<Produit> donneTousProduits() throws Exception
	{
		try {
			return this.connexionBDD.donneTousProduits();
		} 
		catch (Exception ex) {
			System.err.println("Problème d'accès aux produits du stock.");
			throw ex;
		}
	}
	
	/**
	 * Change une certaine quantité d'un produit dans le stock.
	 * 
	 * <p>
	 * Si la quantité est positive, cette quantité sera ajoutée. 
	 * Si la quantité est négative, elle sera enlevée.
	 * </p>
	 * 
	 * @param id l'identifiant du produit.
	 * @param quantite la quantité de produit à changer.
	 * 
	 * @throws Exception toute exception.
	 *
	 */
	public void changeQuantiteProduit(String id, Integer quantite) throws Exception
	{
		try {
			this.connexionBDD.changeQuantiteProduit(id, quantite);
		} 
		catch (Exception ex) {
			System.err.println("Problème de changement de quantité d'un produit du stock.");
			throw ex;
		}
	}

	/**
	 * Fermeture du stock de Morix.
	 * 
	 * <p>Fermeture de la connexion à la base de données.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	public void fermeStock() throws Exception
	{
		try {
			this.connexionBDD.ferme();
		}
		catch (Exception ex) {
			System.err.println("Problème de fermeture du stock.");
			throw ex;
		}
	}
}
