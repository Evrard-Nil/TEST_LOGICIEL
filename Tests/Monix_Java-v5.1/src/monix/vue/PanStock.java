package monix.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.swing.JPanel;

import monix.Monix;
import monix.modele.stock.Produit;
import monix.modele.stock.Stock;

/**
 * Classe du panneau principal de la vue du stock (architecture MVC).
 * 
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.vue.VueStock
 *
 */
public class PanStock extends JPanel
{
	/**
	 * UID auto-généré.
	 */
	private static final long serialVersionUID = -5013087995489564379L;

	/** 
	 * Largeur du panneau.
	 */
	private Integer idLargeur;

	/** 
	 * Largeur du libellé à afficher.
	 */
	private Integer libelleLargeur;
	
	/** 
	 * Largeur de la quantité à afficher.
	 */
	private	Integer quantiteLargeur;
	
	/** 
	 * Largeur du prix à afficher.
	 */
	private	Integer prixLargeur;

	/**
	 * Le stock concerné par le panneau.
	 */
	private transient Stock stock;

	/**
	 * Le constructeur du panneau.
	 *
	 * @param stock le stock concerné.
	 *
	 */
	public PanStock(Stock stock)
	{
		super();

		this.stock = stock;

		this.idLargeur = Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_TICKET_ID_LARGEUR"));
		this.libelleLargeur = Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_TICKET_LIBELLE_LARGEUR"));
		this.quantiteLargeur = Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_TICKET_QUANTITE_LARGEUR"));
		this.prixLargeur = Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_TICKET_PRIX_LARGEUR"));

		setBackground(Color.white);
	}

	/**
	 * Affiche le panneau.
	 *
	 * @param g le composant graphique.
	 *
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		this.afficheProduits(g);
	}

	/**
	 * Affiche les informations sur les produits stockés.
	 *
	 * @param g le composant graphique.
	 *
	 */
	private void afficheProduits(Graphics g) 
	{
		final int x = 10;
		int y = Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_MARGE_PRODUIT_HAUTEUR"));

		final FontMetrics fm = g.getFontMetrics();

		final LinkedHashMap<String, Produit> produits = this.stock.donneProduits();

		setPreferredSize(
			new Dimension(
				Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_LARGEUR")) 
				- Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_FENETRE_MARGE_PRODUIT_LARGEUR")),
				2 * produits.size() * fm.getHeight() + y
			)
		);

		final Iterator<String> iter = produits.keySet().iterator();

		while (iter.hasNext()) {

			final Produit produit = produits.get(iter.next());

			this.afficheFormatStock(g, fm, x, y, 
				produit.donneId(), 
				produit.donneLibelle(), 
				produit.donnePrix(), 
				produit.donneQuantite());

			y += 2 * fm.getHeight();
		}
	}

	/**
	 * Formate les informations à afficher.
	 *
	 * @param g le composant graphique.
	 * @param fm les métriques de la fonte utilisée.
	 * @param x abscisse d'origine.
	 * @param y ordonnée d'origine.
	 * @param id l'identifiant du produit.
	 * @param libelle le libellé du produit.
	 * @param prix le prix unitaire du produit.
	 * @param quantite la quantité de produit présente dans le stock.
	 *
	 */
	private void afficheFormatStock(
			Graphics g, FontMetrics fm, int x, int y, String id, String libelle, Double prix, Integer quantite)
	{
		final String chaineDeTronquage = "...";
		final Integer tailleTronquageProgressif = chaineDeTronquage.length() + 1;
		
		String libelleAffiche = libelle;
		Integer decallage = 0;
		
		g.drawString(id, x, y);
		decallage = this.idLargeur;

		while (fm.stringWidth(libelleAffiche) > this.libelleLargeur) {
			libelleAffiche = libelleAffiche.substring(0, libelleAffiche.length() - tailleTronquageProgressif) 
								+ chaineDeTronquage;
		}
		g.drawString(libelleAffiche, x + decallage, y);
		decallage += this.libelleLargeur;

		g.drawString(prix.toString() + " €", x + decallage, y);
		decallage += this.prixLargeur;
		
		g.drawString(quantite.toString(), x + decallage, y);
		decallage += this.quantiteLargeur;

		g.drawLine(x, y + fm.getHeight() / 2, x + decallage, y + fm.getHeight() / 2);
	}

}
