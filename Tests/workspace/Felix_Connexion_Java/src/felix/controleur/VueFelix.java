package felix.controleur;


/**
 * Classe abstraite de vue contrôlée par un ControleurFelix (architecture MVC). 
 * 
 * @version 3.0
 * @author Matthias Brun 
 *
 * @see felix.controleur.ControleurFelix
 *
 */
public abstract class VueFelix
{
	/**
	 * Le contrôleur du programme.
	 */
	private ControleurFelix controleur;


	/**
	 * Constructeur d'une vue de la vente.
	 *
	 * @param controleur le contrôleur de la vue.
	 *
	 */
	public VueFelix(ControleurFelix controleur)
	{	
		this.controleur = controleur;
	}
	
	/**
	 * Donne le contrôleur du programme.
	 *
	 * @return le contrôleur du programme.
	 *
	 */
	public ControleurFelix donneControleur()
	{
		return this.controleur;
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
