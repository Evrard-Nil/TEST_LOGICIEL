package monix.controleur;

import monix.modele.stock.Stock;
import monix.modele.vente.Vente;
import monix.vue.VueCaisse;
import monix.vue.VueClient;
import monix.vue.VueStock;

/**
 * Classe de contrôleur de vente (architecture MVC). 
 * 
 * @version 3.0
 * @author Matthias Brun
 */
public class ControleurVente 
{
	/**
	 * Vue de la caisse.
	 */
	private VueCaisse vueCaisse;

	/**
	 * Vue du ticket client.
	 */
	private VueClient vueClient;

	/**
	 * Vue du stock.
	 */
	private VueStock vueStock;

	/**
	 * Vente concernée par le contrôleur.
	 */	
	private Vente vente;

	/**
	 * Stock associé à la vente.
	 */
	private Stock stock;
	
	/**
	 * Constructeur du contrôleur de vente.
	 *
	 * @param vente vente concernée par le contrôleur.
	 * @param stock stock associé à la vente.
	 *
	 */
	public ControleurVente(Vente vente, Stock stock)
	{
		this.vente = vente;
		this.stock = stock;
		
		this.vueCaisse = new VueCaisse(this);
		this.vueClient = new VueClient(this);
		this.vueStock  = new VueStock(this);
		
		this.ajouteVenteEcouteurs();
		this.ajouteStockEcouteurs();
		this.afficheVues();
	}

	/**
	 * Donne la vente concernée par le contrôleur.
	 *
	 * @return la vente concernée par le contrôleur.
	 */
	public Vente donneVente()
	{
		return this.vente;
	}

	/**
	 * Donne le stock associé à la vente concernée par le contrôleur.
	 *
	 * @return le stock associé à la vente..
	 */
	public Stock donneStock()
	{
		return this.stock;
	}

	/**
	 * Ajoute la vue de la caisse et la vue du ticket de caisse à la liste des écouteurs de la vente.
	 *
	 */
	private void ajouteVenteEcouteurs() 
	{
		this.vente.ajouteVenteEcouteur(this.vueCaisse);
		this.vente.ajouteVenteEcouteur(this.vueClient);
	}
	
	/** 
	 * Ajoute la vue du stock à la liste des écouteurs du stock.
	 *
	 */
	private void ajouteStockEcouteurs()
	{
		this.stock.ajouteStockEcouteur(this.vueStock);
	}

	/**
	 * Affiche les vues sous la responsabilité du contrôleur.
	 *
	 */
	final public void afficheVues()
	{
		this.vueCaisse.affiche();
		this.vueClient.affiche();
		this.vueStock.affiche();
	}
	
	/**
	 * Ferme les vues sous la responsabilité du contrôleur.
	 *
	 */
	final public void fermeVues()
	{
		this.vueCaisse.ferme();
		this.vueClient.ferme();
		this.vueStock.ferme();
	}

}
