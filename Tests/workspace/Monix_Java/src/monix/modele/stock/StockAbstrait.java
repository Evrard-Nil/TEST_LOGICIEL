package monix.modele.stock;

import java.util.LinkedHashMap;
import javax.swing.event.EventListenerList;

/**
 * Classe abstraite de gestion des stocks qui implante la gestion des écouteurs du stock (architecture MVC).
 *
 * @version 3.0
 * @author Matthias Brun
 *
 */
public abstract class StockAbstrait implements Stock
{
	/** 
	 * Ensemble des produits stockés.
	 */
	final private LinkedHashMap<String, Produit> produits;

	/** 
	 * Ensemble des écouteurs du stock.
	 */
	final private EventListenerList ecouteurs;

	/**
	 * Constructeur du stock.
	 * 
	 * <p><ul>
	 * <li> Création de la table de l'ensemble des produits.</li>
	 * <li> Création de la liste des écouteurs du stock.</li>
	 * </ul></p>
	 *
	 */
	public StockAbstrait()
	{
		this.produits = new LinkedHashMap<String, Produit>();

		this.ecouteurs = new EventListenerList();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see monix.modele.stock.Stock
	 * 
	 */
	@Override
	final public LinkedHashMap<String, Produit> donneProduits()
	{
		return this.produits;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see monix.modele.stock.Stock
	 * 
	 */
	@Override
	final public Produit donneProduit(String id)
	{
		Produit produit = null;

		if (this.donneProduits().containsKey(id)) {
			produit = this.donneProduits().get(id);
		}
		return produit;
	}

	/**
	 * Ajoute un produit dans le stock.
	 *
	 * @param produit le produit à ajouter.
	 *
	 */	
	final protected void ajouteProduit(Produit produit)
	{
		this.donneProduits().put(produit.donneId(), produit);
		this.signalStockChange();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see monix.modele.stock.Stock
	 * 
	 */
	@Override
	final public void ajouteStockEcouteur(StockEcouteur ecouteur)
	{
		this.ecouteurs.add(StockEcouteur.class, ecouteur);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see monix.modele.stock.Stock
	 * 
	 */
	@Override
	final public void enleveStockEcouteur(StockEcouteur ecouteur)
	{
		this.ecouteurs.remove(StockEcouteur.class, ecouteur);
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see monix.modele.stock.Stock
	 * 
	 */
	@Override
	final public void signalStockChange()
	{
		final StockEcouteur[] ecouteursListe = (StockEcouteur[]) this.ecouteurs.getListeners(StockEcouteur.class);
		
		for (StockEcouteur ecouteur : ecouteursListe) {
			ecouteur.stockChange();
		}
	}

}
