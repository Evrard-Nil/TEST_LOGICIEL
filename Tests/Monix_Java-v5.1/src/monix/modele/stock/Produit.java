package monix.modele.stock;

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
	 * La quantité de produit stocké.
	 */
	private Integer quantite;

	/**
	 * Constructeur d'un produit.
	 *	
	 * @param id l'identifiant du produit.
	 * @param libelle le texte qui décrit le produit.
	 * @param prix le prix du produit.
	 * @param quantite la quantité de produit stocké.
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
	 * Donne la quantité de produit stocké.
	 *
	 * @return la quantité de produit stocké.
	 *
	 */
	public Integer donneQuantite()
	{
		return this.quantite;
	}

	/**
	 * Modifie la quantité de produit stocké.
	 *
	 * @param quantite la nouvelle quantité de produit dans le stock.
	 *
	 */
	public void modifieQuantite(Integer quantite)
	{
		this.quantite = quantite;
	}

	/**
	 * Incrémente la quantité de produit stocké.
	 *
	 * <p>
	 * Si la quantité est positive, cette quantité est ajoutée dans le stock. 
	 * Si la quantité est négative, la quantité de produit sera enlevée.
	 * </p>
	 *
	 * @param quantite la quantité de produit à changer.
	 *
	 */
	public void incrementeQuantite(Integer quantite)	
	{
		this.quantite += quantite;
	}
}
