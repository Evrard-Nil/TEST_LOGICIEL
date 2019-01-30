package monix.communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Classe de connexion réseau avec Morix. 
 * 
 * @version 3.0
 * @author Matthias Brun
 * 
 */
public class ConnexionMorix
{
	/**
	 * Encodage de la communication avec Morix.
	 */
	private final static String ENCODAGE = "UTF-8";
	
	/**
	 * Socket de connexion à Morix.
	 */
	private Socket socket;

	/**
	 * Buffer d'écriture sur la socket de connexion à Morix.
	 */
	private BufferedWriter bufferEcriture;

	/**
	 * Buffer de lecture de la socket de connexion à Morix.
	 */
	private BufferedReader bufferLecture;

	/**
	 * Constructeur de la connexion à Morix.
	 *
	 * @param adresse l'adresse du serveur de Morix.
	 * @param port le port de connexion à Morix.
	 *
	 * @throws IOException exception d'entrée/sortie.
	 */
	public ConnexionMorix(String adresse, Integer port) throws IOException
	{
		try {
			// Initialisation de la socket.
			this.socket = new Socket(adresse, port);

			// Initialisation des buffers de lecture et d'écriture sur la socket.
			this.bufferLecture = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), ENCODAGE));
			this.bufferEcriture = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream(), ENCODAGE));	
		} 
		catch (IOException ex) {
			System.err.println("Problème de connexion à Morix.");
			throw ex;
		}
	}

		
	/**
	 * Envoi d'un message à Morix.
	 *
	 * @param message le message à envoyer à Morix.
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
			System.err.println("Problème d'envoi de message à Morix.");
			throw ex;
		}
	}

	/**
	 * Réception d'un message de Morix.
	 *
	 * @return le message provenant de Morix.
	 *
	 * @throws IOException exception d'entrée/sortie.
	 */
	public String lire() throws IOException
	{
		String message = null;

		try {
			message = this.bufferLecture.readLine();

			if (message == null) {
				System.err.println("Fin d'emission du serveur Morix.");
				throw new EOFException();
			}
		}
		catch (IOException ex) {
			System.err.println("Problème de lecture d'un message de Morix.");
			throw ex;
		}

		return message;
	}		
	
}
