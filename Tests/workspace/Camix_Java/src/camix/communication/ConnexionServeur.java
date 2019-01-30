package camix.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe de connexion du serveur Camix.
 * 
 * @version 4.0
 * @author Matthias Brun 
 * 
 */
public class ConnexionServeur
{
	/**
	 * La socket de connexion des nouveaux clients.
	 */
	private ServerSocket socketChat; 

	/**
	 * Constructeur de la connexion serveur.
	 * 
	 * @param port le port d'écoute de la socket serveur.
	 * 
	 * @throws IOException exception d'entrée/sortie.
	 */
	public ConnexionServeur(int port) throws IOException
	{
		try {
			this.socketChat = new ServerSocket(port);
		}
		catch (IOException ex) {
			System.err.print("Problème de création de la socket serveur.");
			throw ex;
		}
	}
	
	/**
	 * Accepte une connexion client.
	 * 
	 * @return la socket de communication avec le client.
	 * 
	 * @throws IOException exception d'entrée/sortie.
	 */
	public Socket accepteConnexionClient() throws IOException
	{
		return this.socketChat.accept();
	}
	
	
	
	
	
	/**
	 * Fermeture de la connexion serveur.
	 *
	 * @throws IOException exception d'entrée/sortie.
	 */
	public void ferme() throws IOException
	{
		// Fermeture de la socket serveur.
		try {
			this.socketChat.close();
		} 
		catch (IOException ex) {
			System.err.println("Problème de fermeture de la socket " + this.socketChat);
			throw ex;
		}	
	}
}
