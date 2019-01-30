package monix.controleur;

import monix.modele.stock.Stock;
import monix.modele.vente.Vente;

/**
 * Classe abstraite de vue contrôlée par un ControleurVente (architecture MVC). 
 * 
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.controleur.ControleurVente
 *
 */
public abstract class VueVente
{
	/**
	 * Le contrôleur du programme.
	 */
	private ControleurVente controleur;


	/**
	 * Constructeur d'une vue de la vente.
	 *
	 * @param controleur le contrôleur de la vue.
	 *
	 */
	public VueVente(ControleurVente controleur)
	{	
		this.controleur = controleur;
	}
	

	/**
	 * Donne le contrôleur de la vue.
	 *
	 * @return le contrôleur de la vue.
	 *
	 */
	public ControleurVente donneControleur()
	{
		return this.controleur;
	}

	/**
	 * Donne la vente associée à la vue.
	 *
	 * @return la vente associée à la vue.
	 *
	 */
	public Vente donneVente()
	{
		return this.controleur.donneVente();
	}
	
	/**
	 * Donne le stock associé à la vente.
	 *
	 * @return le stock associé à la vente.
	 *
	 */
	public Stock donneStock()
	{
		return this.controleur.donneStock();
	}

	/**
	 * Afficher la fenêtre de la vue.
	 *
	 */ 	
	public abstract void affiche();

	/**
	 * Fermer la fenêtre de la vue.
	 *
	 */
	public abstract void ferme();
}
