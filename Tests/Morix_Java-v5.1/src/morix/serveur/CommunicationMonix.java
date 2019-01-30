package morix.serveur;

import java.io.IOException;
import java.net.Socket;

/**
 * Classe de communication avec un client de Morix. 
 * 
 * @version 3.0
 * @author Matthias Brun
 * 
 * @see morix.serveur.ClientMonix
 */
public class CommunicationMonix
{
	/**
	 * Connexion avec l'entité Monix du client utilisateur de la communication.
	 */
	private ConnexionMonix connexion;

	
	/**
	 * Constructeurs de la communication avec une entité de Monix d'un client.
	 *
	 * @param socket la socket de communication avec l'entité de Monix du client.
	 * 
	 * @throws IOException exception d'entrée/sortie.
	 */
	public CommunicationMonix(Socket socket) throws IOException
	{
		try {
			this.connexion = new ConnexionMonix(socket);
		}
		catch (IOException ex) {
			System.err.println("Problème de mise en place de la communication avec Monix.");
			throw ex;
		}
	}

	
	/**
	 * Envoie d'un message à un client Monix.
	 * 
	 * @param message le message à envoyer.
	 * 
	 * @throws IOException exception d'entrée/sortie.
	 */
	public void envoieMessage(String message) throws IOException
	{
		try {
			this.connexion.ecrire(message);
		}
		catch (IOException ex) {
			System.err.println("Problème de communication avec un client (envoie de message).");
			throw ex;
		}
	}
	
	/**
	 * Envoie un message d'erreur au client.
	 *
	 * @param message le message à envoyer.
	 * 
	 * @throws IOException exception d'entrée/sortie.
	 */
	public void envoieMessageErreur(String message) throws IOException
	{
		this.envoieMessage(ProtocoleMorix.MESSAGE_ERREUR + message);
	}	
	
	/**
	 * Réception d'un message du client.
	 * 
	 * @return le message reçu.
	 * 
	 * @throws IOException exception d'entrée/sortie.
	 */
	public String recoitMessage() throws IOException 
	{
		try {
			return this.connexion.lire();
		}
		catch (IOException ex) {
			System.err.println("Problème de communication avec un client (réception de message).");
			throw ex;
		}
	}
	
	/**
	 * Termine la communication avec le client.
	 * 
	 * <p>Ferme la connexion.</p>
	 */
	public void termine()
	{
		this.connexion.ferme();
	}
}

