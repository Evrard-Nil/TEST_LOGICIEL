package morix.serveur;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Classe canal de diffusion du serveur. 
 * 
 * <p>La diffusion des informations de modification du stock se fait via un canal de diffusion.</p>
 *
 * @version 3.0
 * @author Matthias Brun
 * 
 * @see morix.serveur.ServiceMorix
 */
public class CanalDiffusion
{
	/**
	 * L'ensemble des clients du canal.
	 */
	private Hashtable<String, Client> clients;

	/**
	 * Constructeur d'un canal de diffusion.
	 */
	public CanalDiffusion()
	{
		this.clients = new Hashtable<String, Client>();
	}

	/**
	 * Ajout d'un client dans le canal.
	 *
	 * @param client le client à ajouter dans le canal.
	 */
	public void ajouteClient(Client client)
	{
		// Si le client n'est pas déjà dans le canal.
		if (this.clients.get(client.donneId()) == null) {
			this.clients.put(client.donneId(), client);
		}
	}

	/**
	 * Suppression d'un client dans le canal.
	 *
	 * @param client le client à enlever du canal.
	 */
	public void enleveClient(Client client)
	{
		// Si le client est dans le canal.
		if (this.clients.get(client.donneId()) != null) {
			this.clients.remove(client.donneId());
		}
	}

	/**
	 * Envoyer un message à tous les clients sur le canal de diffusion.
	 *
	 * @param message le message à envoyer.
	 */
	public void envoieClients(String message)
	{
		// Synchronisation :
		// Protéger l'accés par plusieurs thread sur l'ensemble des clients.
		// (cf. java.util.ConcurrentModificationException)
		synchronized (this.clients) {

			// Pour chaque client.
			final Iterator<String> iter = this.clients.keySet().iterator();

			while (iter.hasNext()) {
				final Client contact = this.clients.get(iter.next());

				// Envoi du message.
				contact.envoieMessage(message);
			}
		}
	}

}


