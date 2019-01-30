package morix.serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import morix.stock.Produit;
import morix.stock.Stock;


/**
 * Classe de service de Morix.
 * 
 * <p>
 * Ce service consiste à ouvrir une socket d'écoute de connexion pour les clients Monix.
 * Quant un client se connecte, un instance de client (ClientMonix) est créée pour 
 * gérer les interactions avec ce client.
 * </p>
 * 
 * @version 3.0
 * @author Matthias Brun
 * 
 * @see morix.serveur.ClientMonix
 */
public final class ServiceMorix
{
	/**
	 * La socket de connexion des nouveaux clients.
	 */
	private ServerSocket socket; 

	/**
	 * Le canal de diffusion de Morix.
	 */
	private CanalDiffusion canal;

	/**
	 * Le stock de Morix.
	 */
	private Stock stock;


	/**
	 * Constructeur d'un service de Morix.
	 *
	 * @throws Exception toute exception.
	 *
	 */
	public ServiceMorix() throws Exception
	{
		// Création du canal de diffusion.
		this.canal = new CanalDiffusion();
		
		// Socket à null par défaut.
		this.socket = null;

		// Création du stock.
		try {
			this.stock = new Stock();
		}
		catch (Exception ex) {
			System.err.println("Problème de création du stock de Morix.");
			throw ex;
		}
	}

	/**
	 * Lancement du service.
	 * 
	 * @param port le port d'écoute du service.
	 *
	 * @throws Exception toute exception.
	 */
	public void lanceService(Integer port) throws Exception
	{
		// Ouverture de la socket serveur.
		try {
			this.socket = new ServerSocket(port);
		}
		catch (Exception ex) {
			System.err.println("Problème de création de la socket de Morix.");
			if (this.stock != null) {
				this.stock.fermeStock();
			}
			throw ex;
		}
		
		// Lancement du service.
		try {
			this.service();
		}
		catch (Exception ex) {
			System.err.println("Problème lors du service de Morix.");
			if (this.stock != null) {
				this.stock.fermeStock();
			}
			this.fermeSocket();
			throw ex;
		}
	}
	
	/**
	 * Fermeture de la socket de Morix.
	 *
	 * @throws Exception toute exception.
	 *
	 */
	private void fermeSocket() throws Exception
	{
		try {
			this.socket.close();
		} 
		catch (Exception ex) {
			System.err.println("Problème de fermeture de la socket " + this.socket);
			throw ex;
		}	
	}

	/**
	 * Service de Morix.
	 *
	 * @throws IOException exception d'entrée/sortie de fichier (socket).
	 *
	 * @see morix.serveur.ClientMonix
	 *
	 */
	private void service() throws IOException
	{
		while (true) {
			// Création d'une socket de communication avec un client qui se connecte à Morix.
			final Socket socketClient = this.socket.accept();

			// Création d'un client Monix.
			final ClientMonix client = new ClientMonix(this, socketClient);

			// Lancement d'un thread de service au client.
			client.lanceService();
			
			System.out.println("Ouverture connexion client (id : " + client.donneId() + ")");
		}
	}

	/**
	 * Inscrit un client au canal de diffusion de Morix.
	 * 
	 * @param client le client concerné.
	 */
	public void inscriptionCanal(Client client)
	{
		// Synchronization :
		// Pour éviter d'ajouter un client alors qu'un envoi de message est en cours.
		synchronized (this.canal) {
			this.canal.ajouteClient(client);
		}
		client.envoieMessage(ProtocoleMorix.MESSAGE_INSCRIPTION);
	}
	
	/**
	 * Envoie l'ensemble des produits à un client.
	 *
	 * <p>
	 * Si les produits ne sont pas accessibles dans le stock (la base de données), 
	 * un message d'erreur est envoyé au client.
	 * </p>
	 *
	 * @param client le client concerné.
	 * 
	 */
	public void envoieProduits(Client client)
	{
		try {
			// Récupération des produits du stock.
			final ArrayList<Produit> produits = this.stock.donneTousProduits();

			// Formattage du message de transmission des produits du stock.
			final String message = ProtocoleMorix.formatteMessageEnsembleProduits(produits);

			client.envoieMessage(message);
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
			client.envoieMessageErreur("Problème d'accès au stock.");
		}
	}
	
	/**
	 * Change la quantité d'un produit dans le stock.
	 *
	 * @param client le client à l'origine de la demande.
	 * @param id l'identifiant du produit concerné. 
	 * @param quantite la quantité à ajouter ou enlever.
	 */
	public void changeQuantiteProduit(Client client, String id, Integer quantite)
	{
		try {
			// Change la quantité de produit dans la base de données.
			this.stock.changeQuantiteProduit(id, quantite);
			
			// Récupération des informations sur le produit dans la base.
			final Produit produit = this.stock.donneProduit(id);

			if (produit == null) {
				client.envoieMessageErreur("Problème d'accès à un produit inconnu.");
			} else {
				// Envoie de la nouvelle quantité sur le canal de diffusion.
				this.envoieCanal(ProtocoleMorix.formatteMessageModificationQuantiteProduit(produit));
			}
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
			client.envoieMessageErreur("Problème d'accès au stock."); 
		}
	}
	
	/**
	 * Envoie d'un message sur le canal de diffusion.
	 *
	 * @param message le message à envoyer.
	 */
	private void envoieCanal(String message)
	{
		// Synchronisation : 
		// Pour éviter qu'un client ne soit supprimé du canal lors de l'envoi.
		synchronized (this.canal) {
			this.canal.envoieClients(message);
		}
	}

	/**
	 * Fermeture d'une connexion avec un client.
	 *
	 * @param client le client concerné.
	 */
	public void fermeConnexion(Client client) 
	{
		System.out.println("Fermeture connexion client (id : " + client.donneId() + ")");

		// Synchronisation :
		// Pour éviter qu'une connexion soit fermée lors de l'envoi d'un message sur le canal du client.
		synchronized (this.canal) {
			// Suppression du client dans le canal.
			this.canal.enleveClient(client);

			client.termineCommunication();
		}
	}
}

