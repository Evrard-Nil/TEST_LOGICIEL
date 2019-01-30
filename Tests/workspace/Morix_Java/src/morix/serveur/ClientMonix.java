package morix.serveur;

import java.io.IOException;
import java.net.Socket;

/**
 * Classe client du serveur Morix. 
 * 
 * @version 3.0
 * @author Matthias Brun
 * 
 * @see morix.serveur.ServiceMorix
 */
public class ClientMonix extends Thread implements Client 
{
	/**
	 * Identifiant du client.
	 */
	private String id;
	
	/**
	 * La communication avec l'entité Monix du client.
	 */
	private CommunicationMonix communication;

	/**
	 * Serveur Morix du client.
	 */
	private ServiceMorix morix;
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.serveur.Client
	 */
	@Override
	public String donneId()
	{
		return this.id;
	}


	/**
	 * Constructeur d'un client de Morix.
	 *
	 * @param morix service Morix du client.
	 * @param socket la socket de connexion du client.
	 *
	 * @throws IOException exception d'entrée/sortie sur le buffer de transmission.
	 *
	 */
	public ClientMonix(ServiceMorix morix, Socket socket) throws IOException
	{
		super();
		
		this.morix = morix;
		
		try {
			// Création d'une communication pour le client.
			this.communication = new CommunicationMonix(socket);
		}
		catch (IOException ex) {
			System.err.println("Problème de mise en place d'une gestion de client Monix.");
			throw ex;
		}
		
		// Utilisation de la socket de connexion du client comme identifiant.
		this.id = socket.toString();
	}

	/**
	 * Lancement du service à un client.
	 */
	public void lanceService()
	{
		this.start();
	}
	
	/**
	 * Point d'entrée du thread de service au client 
	 * (atteint via start() dans le lancement du service au client).
	 * 
	 * <p>
	 * Lecture de messages sur la socket de communication avec le client puis traitement du message.
	 * S'arrête quand le message est <tt>null</tt>. 
	 * </p>
	 */
	public void run()
	{
		try {
			while (true) {
				final String message = this.communication.recoitMessage();
				if (message == null) {
					// Fermeture de la connexion par le client.
					break;
				}
				this.traiteMessage(message);
			}
		}
		catch (IOException ex) {
			System.err.println("Problème de gestion d'un client (id : " + this.id + ")");
			System.err.println(ex.getMessage());
		} 
		finally {
			this.morix.fermeConnexion(this);
		}
	}
	
	/**
	 * Traitement d'un message envoyé par le client.
	 * 
	 * @param message le message à traiter.
	 */
	private void traiteMessage(String message)
	{
		if (ProtocoleMorix.estUneCommande(message)) {
			
			switch (ProtocoleMorix.commandeDuMessage(message)) {
			
				case ProtocoleMorix.INSCRIPTION_CLIENT : 
					this.morix.inscriptionCanal(this); 
					break;
					
				case ProtocoleMorix.DONNE_PRODUITS : 
					this.morix.envoieProduits(this);
					break;
					
				case ProtocoleMorix.CHANGE_QUANTITE_PRODUIT : 
					this.morix.changeQuantiteProduit(
						this,
						ProtocoleMorix.parametreChangeQuantiteProduitId(message),
						ProtocoleMorix.parametreChangeQuantiteProduitQuantite(message)
					);
					break;
					
				default : 
					this.envoieMessageErreur("Commande inconnue.");
					break;
			}
		} else {
			// Si le message n'est pas une commande.
			this.envoieMessageErreur("Format de commande erroné.");
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.serveur.Client
	 */
	@Override
	public void envoieMessage(String message)
	{
		try {
			this.communication.envoieMessage(message);
		} 
		catch (IOException ex) { 
			System.err.println("Problème d'envoie d'un message à un client (id : " + this.id + ")"); 
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.serveur.Client
	 */
	@Override
	public void envoieMessageErreur(String message)
	{
		try {
			this.communication.envoieMessageErreur(message);
		} 
		catch (IOException ex) { 
			System.err.println("Problème d'envoie d'un message d'erreur à un client (id : " + this.id + ")"); 
			System.err.println(ex.getMessage());
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.serveur.Client
	 */
	@Override
	public void termineCommunication() 
	{
		this.communication.termine();
	}
}
