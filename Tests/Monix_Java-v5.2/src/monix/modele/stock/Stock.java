package monix.modele.stock;

import java.util.LinkedHashMap;

/**
 * Interface de gestion des stocks. 
 * 
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.modele.stock.StockAbstrait
 * @see monix.modele.stock.StockEcouteur
 *
 */
public interface Stock
{
	/**
	 * Donne l'ensemble des produits du stock.
	 *
	 * @return l'ensemble des produits du stock dans une table de hachage.
	 *
	 */
	public LinkedHashMap<String, Produit> donneProduits();

	/**
	 * Change une certaine quantité d'un produit dans le stock.
	 *
	 * <p>Si la quantité est positive, cette quantité sera ajoutée dans le stock. 
	 * Si la quantité est négative, la quantité de produit sera enlevée.</p>
	 * 
	 * @param produit le produit concerné.
	 * @param quantite la quantité de produit à changer.
	 *
	 */
	public void changeQuantiteProduit(Produit produit, Integer quantite);

	/**
	 * Donne un produit stocké à partir de son identifiant.
	 *
	 * @param id l'identifiant du produit.
	 *
	 * @return le produit dont l'identifiant est id, <code>null</code> si le produit n'est pas dans le stock.
	 *
	 */
	public Produit donneProduit(String id);
	

	/**
	 * Ajoute des vues à la liste des écouteurs du stock (architecture MVC).
	 *
	 * @param ecouteur vue à ajouter à la liste.
	 *
	 * @see monix.modele.stock.StockEcouteur
	 *
	 */
	public void ajouteStockEcouteur(StockEcouteur ecouteur);

	/**
	 * Enlève des vues de la liste des écouteurs du stock (architecture MVC).
	 *
	 * @param ecouteur vue à enlever de la liste.
	 *
	 * @see monix.modele.stock.StockEcouteur
	 *
	 */
	public void enleveStockEcouteur(StockEcouteur ecouteur);

	/**
	 * Signale à la liste des écouteurs que le stock a changé (architecture MVC).
	 *
	 * @see monix.modele.stock.StockEcouteur
	 *
	 */
	public void signalStockChange();

}
