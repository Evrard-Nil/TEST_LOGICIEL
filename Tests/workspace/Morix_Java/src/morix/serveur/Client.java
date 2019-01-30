package morix.serveur;

/**
 * Interface client du serveur. 
 * 
 * @version 3.0
 * @author Matthias Brun
 * 
 * @see morix.serveur.ServiceMorix
 */
public interface Client
{
	/**
	 * Accesseur à l'identifiant du client.
	 * 
	 * @return l'identifiant du client.
	 *
	 */
	public String donneId();

	/**
	 * Envoyer un message à un client.
	 * 
	 * @param message le message à envoyer.
	 */
	public void envoieMessage(String message);
	
	/**
	 * Envoyer un message d'erreur à un client.
	 * 
	 * @param message le message à envoyer.
	 */
	public void envoieMessageErreur(String message);
	
	/**
	 * termine la communication avec un client.
	 *
	 */
	public void termineCommunication();
}
