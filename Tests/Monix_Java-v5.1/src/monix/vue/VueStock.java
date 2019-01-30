package monix.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JScrollPane;

import monix.Monix;
import monix.controleur.ControleurVente;
import monix.controleur.VueVente;
import monix.modele.stock.StockEcouteur;

/**
 * Classe de la vue du stock (architecture MVC).
 * 
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.controleur.VueVente
 * @see monix.modele.stock.StockEcouteur
 *
 */
public class VueStock extends VueVente implements StockEcouteur
{
	/**
	 * La fenêtre de la vue.
	 */
	private Fenetre	fenetre;

	/**
	 * Le conteneur de la vue.
	 */
	private Container contenu;

	/**
	 * Le panneau principal de la vue.
	 * 
	 * @see monix.vue.PanStock
	 */
	private PanStock panStock;

	/**
	 * Le défilement du panneau de la vue.
	 */
	private JScrollPane defilementStock;

	/**
	 * Constructeur de la vue.
	 *
	 * @param controleur le contrôleur associé à la vue.
	 *
	 * @see monix.controleur.VueVente
	 */
	public VueStock(ControleurVente controleur)
	{
		super(controleur);

		this.fenetre = new Fenetre(
			Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_LARGEUR")),
			Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_HAUTEUR")),
			Monix.MESSAGES.getString("STOCK_FENETRE_TITRE")
		);

		this.construireFenetre();
	}

	/**
	 * Construire les panneaux et les widgets de contrôle de la vue.
	 *
	 */
	private void construireFenetre()
	{
		this.contenu = this.fenetre.getContentPane();
		this.contenu.setLayout(new BorderLayout());

		this.panStock = new PanStock(donneStock());
		
		this.defilementStock = new JScrollPane(this.panStock);	
        	this.defilementStock.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.contenu.add(this.defilementStock);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à un changement dans le stock.  
	 * Ré-affiche le panneau principal de la vue lorsque le stock change.
	 * </p>
	 * 
	 * @see monix.modele.stock.StockEcouteur
	 *
	 */
	@Override
	public void stockChange()
	{
		this.panStock.repaint();
	}

	
	/**
	 * {@inheritDoc}
	 *
	 * @see monix.controleur.VueVente
	 */
	@Override
	public void affiche() 
	{
		this.fenetre.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see monix.controleur.VueVente
	 */
	@Override
	public void ferme() 
	{
		this.fenetre.dispose();
	}

}
