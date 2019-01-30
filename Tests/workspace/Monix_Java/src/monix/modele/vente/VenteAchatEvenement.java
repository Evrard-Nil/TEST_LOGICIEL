package monix.modele.vente;

import monix.modele.stock.Produit;

/**
 * Classe de gestion des évènements de notification d'achat dans une vente.
 *
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.modele.vente.VenteEcouteur
 * @see monix.modele.vente.Vente
 *
 */
public class VenteAchatEvenement
{
	/**
	 * Produit concerné par l'achat.
	 */
	private Produit produit;	

	/**
	 * Quantité de produit acheté.
	 */
	private Integer quantite;

	/**
	 * Constructeur d'un évènement de notification d'achat.
	 *
	 * @param produit produit concerné par l'achat.
	 * @param quantite de produit acheté.
	 *
	 */
	public VenteAchatEvenement(Produit produit, Integer quantite)
	{
		this.produit = produit;
		this.quantite = quantite;
	}
	

	/**
	 * Donne le produit concerné par l'achat.
	 *
	 * @return le produit concerné par l'achat.
	 *
	 */
	public Produit donneProduit()
	{
		return this.produit;
	}

	/**
	 * Donne la quantité de produit concerné par l'achat.
	 *
	 * @return la quantité de produit concerné par l'achat.
	 *
	 */
	public Integer donneQuantite()
	{
		return this.quantite;
	}

}
