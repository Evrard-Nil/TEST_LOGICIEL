package monix.modele.vente;

import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.swing.event.EventListenerList;

import monix.modele.stock.Produit;
import monix.modele.stock.Stock;

/**
 * Classe de gestion des ventes. 
 * 
 * @version 3.0
 * @author Matthias Brun
 */
public class Vente
{
	/**
	 * Stock associé à la vente.
	 */
	private Stock stock;

	/**
	 * Ensemble des achats de la vente.
	 */
	private LinkedHashMap<String, Achat> achats;

	/**
	 * Prix de la vente.
	 */
	private Double prix;

	/**
	 * Liste des écouteurs de la vente (vues de l'architecture MVC).
	 */
	private EventListenerList ecouteurs;

	
	/**
	 * Constructeur d'une vente.
	 *
	 * @param stock le stock associé à la vente.
	 *
	 */
	public Vente(Stock stock)
	{
		this.stock = stock;
		this.achats = new LinkedHashMap<String, Achat>();
		this.prix = Double.valueOf(0);

		this.ecouteurs = new EventListenerList();
	}


	/**
	 * Donne le stock associé à la vente.
	 *
	 * @return le stock associé à la vente.
	 */
	public Stock donneStock()
	{
		return this.stock;
	}

	/**
	 * Donne l'ensemble des achats de la vente.
	 *
	 * @return l'ensemble des achats de la vente.
	 */
	public LinkedHashMap<String, Achat> donneAchats()
	{
		return this.achats;
	}

	/**
	 * Donne le prix de la vente.
	 *
	 * @return le prix de la vente.
	 */
	public Double donnePrix()
	{
		return this.prix;
	}


	/**
	 * Ajoute un achat de produit à l'ensemble des achats de la vente.
	 * 
	 * @param id l'identifiant du produit acheté.
	 * @param quantite la quantité de produit acheté.
	 *
	 * @throws AchatImpossibleException si aucun produit du stock ne correspond à celui précisé par l'identifiant.
	 *
	 */
	public void ajouteAchatProduit(String id, Integer quantite) throws AchatImpossibleException
	{
		if (quantite < 0) {
			throw new AchatImpossibleException();
		} else {

			final Produit produit = this.stock.donneProduit(id);
			
			if (produit == null) {
				throw new AchatImpossibleException();
			} else {
				this.ajouteAchat(id, produit, quantite);
			}
		}
	}
	
	/**
	 * Annule l'achat d'un produit de l'ensemble des achats de la vente.
	 * 
	 * @param id l'identifiant du produit à annuler.
	 * @param quantite la quantité de produit à annuler.
	 *
	 * @throws AchatImpossibleException si aucun produit du stock ne correspond à celui précisé par l'identifiant.
	 *
	 */
	public void annuleAchatProduit(String id, Integer quantite) throws AchatImpossibleException
	{
		if (quantite < 0) {
			throw new AchatImpossibleException();
		} else {
			
			final Produit produit = this.stock.donneProduit(id);

			if (produit == null) {
				throw new AchatImpossibleException();
			} else {
				this.annuleAchat(id, produit, quantite);
			}
		}
	}


	/**
	 * Ajoute un achat de produit à l'ensemble des achats de la vente.
	 * 
	 * @param id l'identifiant du produit acheté.
	 * @param produit le produit acheté.
	 * @param quantite la quantité de produit acheté.
	 *
	 */
	private void ajouteAchat(String id, Produit produit, Integer quantite)
	{
		if (this.achats.containsKey(id)) {
			this.achats.get(id).incrementeQuantite(quantite);
		} else {
			this.achats.put(id, new Achat(produit, quantite));
		}

		this.effaceProduitQuantiteNulle(id);

		this.incrementePrix(quantite * produit.donnePrix());

		this.signalAjouteAchat(produit, quantite);	
	}

	/**
	 * Annule l'achat d'un produit de l'ensemble des achats de la vente.
	 * 
	 * @param id l'identifiant du produit à annuler.
	 * @param produit le produit à annuler.
	 * @param quantite la quantité de produit à annuler.
	 *
	 */
	private void annuleAchat(String id, Produit produit, Integer quantite)
	{
		if (this.achats.containsKey(id)) {
			this.achats.get(id).decrementeQuantite(quantite);
		} else {
			this.achats.put(id, new Achat(produit, -quantite));
		}

		this.effaceProduitQuantiteNulle(id);

		this.decrementePrix(quantite * produit.donnePrix());
	
		this.signalAnnuleAchat(produit, quantite);
	}


	/**
	 * Augmente le prix de la vente.
	 *
	 * @param prix le prix à ajouter au prix de la vente.
	 *
	 */
	private void incrementePrix(Double prix)
	{
		this.prix += prix;
	}

	/**
	 * Diminuer le prix de la vente.
	 *
	 * @param prix le prix à soustraire au prix de la vente.
	 *
	 */
	private void decrementePrix(Double prix)	
	{
		this.prix -= prix;
	}


	/**
	 * Efface de la liste des achats l'achat d'un produit particulier si la quantité est nulle.
	 * 
	 * @param id l'identifiant du produit dont la quantité est vérifiée dans la liste des achats.
	 *
	 */
	private void effaceProduitQuantiteNulle(String id)
	{
		if (this.achats.get(id).donneQuantite() == 0) {
			this.achats.remove(id);
		}
	}


	/**
	 * Mettre fin à la vente.
	 *
	 */
	public void clotureVente()
	{
		final Iterator<String> iter = this.achats.keySet().iterator();

		while (iter.hasNext()) {
			final Achat achat = this.achats.get(iter.next());
			this.stock.changeQuantiteProduit(achat.donneProduit(), -achat.donneQuantite());
		}
		
		this.achats = new LinkedHashMap<String, Achat>();
		this.prix = Double.valueOf(0);

		this.signalVenteCloture();
	}

	
	/**
	 * Ajouter des vues à la liste des écouteurs de la vente (architecture MVC).
	 *
	 * @param ecouteur vue à ajouter à la liste.
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 *
	 */
	public void ajouteVenteEcouteur(VenteEcouteur ecouteur)
	{
		this.ecouteurs.add(VenteEcouteur.class, ecouteur);
	}

	/**
	 * Enlever des vues de la liste des écouteurs de la vente (architecture MVC).
	 *
	 * @param ecouteur vue à enlever de la liste.
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 *
	 */
	public void enleveVenteEcouteur(VenteEcouteur ecouteur)
	{
		this.ecouteurs.remove(VenteEcouteur.class, ecouteur);
	}
	
	/**
	 * Signaler à la liste des écouteurs qu'un produit a été ajouté à la vente.
	 *
	 * @param produit le produit qui vient d'être ajouté à la vente.
	 * @param quantite la quantité de produit qui vient d'être ajoutée.
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	private void signalAjouteAchat(Produit produit, Integer quantite)
	{
		final VenteEcouteur[] ecouteursListe = (VenteEcouteur[]) this.ecouteurs.getListeners(VenteEcouteur.class);
		
		for (VenteEcouteur ecouteur : ecouteursListe) {
			ecouteur.venteAjouteAchat(new VenteAchatEvenement(produit, quantite));
		}
		this.signalVenteChange();
	}

	/**
	 * Signaler à la liste des écouteurs qu'un produit a été annulé de la vente.
	 *
	 * @param produit le produit qui vient d'être annulé à la vente.
	 * @param quantite la quantité de produit qui vient d'être annulée.
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	private void signalAnnuleAchat(Produit produit, Integer quantite)
	{
		final VenteEcouteur[] ecouteursListe = (VenteEcouteur[]) this.ecouteurs.getListeners(VenteEcouteur.class);
		
		for (VenteEcouteur ecouteur : ecouteursListe) {
			ecouteur.venteAnnuleAchat(new VenteAchatEvenement(produit, quantite));
		}
		this.signalVenteChange();
	}

	/**
	 * Signaler à la liste des écouteurs que la vente a changé.
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteChangeEvenement
	 *
	 */
	private void signalVenteChange()
	{
		final VenteEcouteur[] ecouteursListe = (VenteEcouteur[]) this.ecouteurs.getListeners(VenteEcouteur.class);
		
		for (VenteEcouteur ecouteur : ecouteursListe) {
			ecouteur.venteChange(new VenteChangeEvenement(this.donnePrix(), this.donneAchats()));
		}
	}

	/**
	 * Signaler à la liste des écouteurs que la vente est terminée.
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 *
	 */
	private void signalVenteCloture()
	{
		final VenteEcouteur[] ecouteursListe = (VenteEcouteur[]) this.ecouteurs.getListeners(VenteEcouteur.class);
		
		for (VenteEcouteur ecouteur : ecouteursListe) {
			ecouteur.venteCloture();
		}
	}

}

