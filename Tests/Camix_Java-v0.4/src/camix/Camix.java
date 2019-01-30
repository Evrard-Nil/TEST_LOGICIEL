package camix;

import java.io.IOException;
import java.util.ResourceBundle;

import camix.service.ServiceChat;

/**
 * Classe principale du programme Camix. 
 * 
 * @version 3.0
 * @author Matthias Brun 
 *
 */
public final class Camix
{
	/**
	 * Fichier de configuration du serveur.
	 */
	public static final ResourceBundle CONFIGURATION = ResourceBundle.getBundle("Configuration");

	/**
	 * Constructeur privé de Camix.
	 * 
	 * Ce constructeur privé assure la non-instanciation de Camix dans un programme
	 * (Camix est la classe principale du programme Camix).
	 */
	private Camix() 
	{
		// Constructeur privé pour assurer la non-instanciation de Camix.
	}

	/**
	 * Main du programme.
	 *
	 * <p>
	 * Cette fonction lance le programme serveur qui ouvre le service de chat.
	 * </p>
	 *
	 * @param args aucun argument attendu.
	 *
	 */
	public static void main(String[] args)
	{	
		System.out.println("Camix v4.0");

		try {
			// Création du service.
			new ServiceChat(
					Camix.CONFIGURATION.getString("CANAL_PAR_DEFAUT").trim(),
					Integer.parseInt(Camix.CONFIGURATION.getString("PORT_SERVICE_CHAT").trim()));
		}
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

}
