package morix.stock;

/**
 * Classe de gestion des produits. 
 * 
 * @version 3.0
 * @author Matthias Brun
 */
public class Produit
{
	/**
	 * Identifiant du produit.
	 */
	private String id;

	/**
	 * Libellé de description du produit.
	 */
	private String libelle;

	/**
	 * Prix du produit.
	 */
	private Double prix;

	/**
	 * Quantité de produit (dans la base).
	 */
	private Integer quantite;


	/**
	 * Constructeur d'un produit.
	 *	
	 * @param id l'identifiant du produit.
	 * @param libelle le texte qui décrit le produit.
	 * @param prix le prix du produit.
	 * @param quantite la quantité de produit.
	 *
	 */ 
	public Produit(String id, String libelle, Double prix, Integer quantite)
	{
		this.id = id;
		this.libelle = libelle;
		this.prix = prix;
		this.quantite = quantite;
	}


	/**
	 * Donne l'identifiant du produit.
	 *
	 * @return l'identifiant sous forme d'une chaîne de caractères.
	 *
	 */
	public String donneId()
	{
		return this.id;
	}

	/**
	 * Donne le libellé de description du produit.
	 * 
	 * @return le libellé du produit.
	 *
	 */
	public String donneLibelle()
	{
		return this.libelle;
	}

	/** 
	 * Donne le prix du produit.
	 *
	 * @return le prix du produit.
	 *
	 */
	public Double donnePrix()
	{
		return this.prix;
	}

	/** 
	 * Donne la quantité de produit.
	 *
	 * @return la quantité de produit.
	 *
	 */
	public Integer donneQuantite()
	{
		return this.quantite;
	}

}
