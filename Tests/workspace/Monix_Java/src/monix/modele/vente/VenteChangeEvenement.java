package monix.modele.vente;

import java.util.LinkedHashMap;

/**
 * Classe de gestion des évènements de notification de changement dans une vente.
 *
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.modele.vente.VenteEcouteur
 * @see monix.modele.vente.Vente
 *
 */
public class VenteChangeEvenement
{
	/** 
	 * Le prix de la vente.
	 */
	private Double prix;

	/**
	 * L'ensemble des achats de la vente.
	 */
	private LinkedHashMap<String, Achat> achats;
	
	/**
	 * Constructeur d'un évènement de notification de changement.
	 *
	 * @param prix prix de la vente.
	 * @param achats ensemble des achats de la vente.
	 *
	 */
	public VenteChangeEvenement(Double prix, LinkedHashMap<String, Achat> achats)
	{
		this.prix = prix;
		this.achats = achats;
	}
	
	/**
	 * Donne le prix de la vente.
	 *
	 * @return le prix de la vente.
	 *
	 */
	public Double donnePrix()
	{
		return this.prix;
	}

	/**
	 * Donne l'ensemble des achats de la vente.
	 *
	 * @return l'ensemble des achats de la vente.
	 *
	 */
	public LinkedHashMap<String, Achat> donneAchats()
	{
		return this.achats;
	}

}
