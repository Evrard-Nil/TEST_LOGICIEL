package monix.modele.vente;

import monix.modele.stock.Produit;

/**
 * Classe de gestion des achats. 
 * 
 * @version 3.0
 * @author Matthias Brun
 */
public class Achat
{
	/**
	 * Le produit acheté.
	 */
	private Produit produit;

	/** 
	 * La quantité de produit acheté.
	 */
	private Integer quantite;


	/**
	 * Constructeur d'un achat.
	 *
	 * @param produit le produit acheté.
	 * @param quantite la quantité de produit acheté.
	 *
	 */
	public Achat(Produit produit, Integer quantite)
	{
		this.produit = produit;
		this.quantite = quantite;
	}


	/**
	 * Donne le produit acheté.
	 *
	 * @return le produit acheté.
	 *
	 */
	public Produit donneProduit()
	{
		return this.produit;
	}

	/**
	 * Donne la quantité de produit acheté.
	 *
	 * @return la quantité de produit acheté.
	 *
	 */
	public Integer donneQuantite()
	{
		return this.quantite;
	}

	/**
	 * Augmente la quantité de produit acheté.
	 *
	 * @param quantite la quantité de produit à ajouter à l'achat.
	 *
	 */
	public void incrementeQuantite(Integer quantite)
	{
		this.quantite += quantite;
	}

	/**
	 * Diminue la quantité de produit acheté.
	 *
	 * @param quantite la quantité de produit à soustraire de l'achat.
	 *
	 */
	public void decrementeQuantite(Integer quantite)	
	{
		this.quantite -= quantite;
	}

}
