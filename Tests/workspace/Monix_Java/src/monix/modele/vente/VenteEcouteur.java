package monix.modele.vente;

import java.util.EventListener;

/**
 * Interface de gestion des écouteurs d'une vente (architecture MVC).
 *
 * @version 3.0
 * @author Matthias Brun
 *
 * @see java.util.EventListener
 * @see monix.modele.vente.Vente
 *
 */
public interface VenteEcouteur extends EventListener 
{

	/**
	 * Notifie l'ajout d'un achat.
	 * 
	 * @param ev évènement d'achat lié à une vente.
	 *
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	public void venteAjouteAchat(VenteAchatEvenement ev);

	/**
	 * Notifie l'annulation d'un achat.
	 * 
	 * @param ev évènement d'achat lié à une vente.
	 *
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	public void venteAnnuleAchat(VenteAchatEvenement ev);

	/**
	 * Notifie le changement d'une vente.
	 * 
	 * @param ev évènement de changement lié à une vente.
	 *
	 * @see monix.modele.vente.VenteChangeEvenement
	 *
	 */
	public void venteChange(VenteChangeEvenement ev);
	
	/**
	 * Notifie la clôture d'une vente.
	 * 
	 */
	public void venteCloture();

}
