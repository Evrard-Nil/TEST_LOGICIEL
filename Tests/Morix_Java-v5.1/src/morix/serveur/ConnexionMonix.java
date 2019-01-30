package morix.serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * Classe de connexion réseau avec (une entité de) Monix. 
 * 
 * @version 3.0
 * @author Matthias Brun
 * 
 */
public class ConnexionMonix
{
	/**
	 * Encodage de la communication avec Monix.
	 */
	private final static String ENCODAGE = "UTF-8";
	
	/**
	 * Socket de connexion avec Monix.
	 */
	private Socket socket;

	/**
	 * Buffer d'écriture sur la socket de connexion à Monix.
	 */
	private BufferedWriter bufferEcriture;

	/**
	 * Buffer de lecture de la socket de connexion à Monix.
	 */
	private BufferedReader bufferLecture;

	/**
	 * Accesseur à la socket de la connexion.
	 * 
	 * @return la socket de la connexion.
	 */
	public Socket donneSocket()
	{
		return this.socket;
	}
	
	/**
	 * Constructeur de la connexion à Monix.
	 *
	 * @param socket la socket de connexion avec Monix.
	 *
	 * @throws IOException exception d'entrée/sortie.
	 */
	public ConnexionMonix(Socket socket) throws IOException
	{
		try {
			// Initialisation de la socket.
			this.socket = socket;

			// Initialisation des buffers de lecture et d'écriture sur la socket.
			this.bufferLecture = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), ENCODAGE));
			this.bufferEcriture = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), ENCODAGE));	
		} 
		catch (IOException ex) {
			System.err.println("Problème de connexion avec Monix.");
			throw ex;
		}
	}

		
	/**
	 * Envoi d'un message à Monix.
	 *
	 * @param message le message à envoyer à Monix.
	 *
	 * @throws IOException exception d'entrée/sortie.
	 */
	public void ecrire(String message) throws IOException
	{
		try {
			this.bufferEcriture.write(message, 0, message.length());
			this.bufferEcriture.newLine();
			this.bufferEcriture.flush();
		}
		catch (IOException ex) {
			System.err.println("Problème de connexion avec Monix (envoi de message).");
			throw ex;
		}
	}

	/**
	 * Réception d'un message de Monix.
	 *
	 * @return le message provenant de Monix.
	 *
	 * @throws IOException exception d'entrée/sortie.
	 */
	public String lire() throws IOException
	{
		String message = null;

		try {
			message = this.bufferLecture.readLine();
		}
		catch (IOException ex) {
			if (!(ex instanceof SocketException || "Connection reset".equals(ex.getMessage()))) {
				// Si l'exception n'est pas due à une coupure de connexion du client.
				System.err.println("Problème de connexion avec Monix (lecture de message).");
				throw ex;
			}
		}

		return message;
	}		
	
	/**
	 * Fermeture de la connexion.
	 * 
	 * Fermeture des buffers de lecture et d'écriture et fermeture de la socket.
	 * 
	 */
	public void ferme() 
	{
		// Fermeture du buffer d'écriture.
		try {
			this.bufferEcriture.close();
		}
		catch (IOException ex) {
			System.err.println("Problème de fermeture de connexion (buffer écriture : " + this.bufferEcriture + ").");
			System.err.println(ex.getMessage()); 
		}
		
		// Fermeture du buffer de lecture.
		try {
			this.bufferLecture.close();
		}
		catch (IOException ex) {
			System.err.println("Problème de fermeture de connexion (buffer lecture : " + this.bufferLecture + ").");
			System.err.println(ex.getMessage()); 
		}

		// Fermeture de la socket.
		try {
			this.socket.close();
		} 
		catch (IOException ex) {
			System.err.println("Problème de fermeture de connexion (fermeture socket : " + this.socket + ").");
			System.err.println(ex.getMessage()); 
		}
	}
}
