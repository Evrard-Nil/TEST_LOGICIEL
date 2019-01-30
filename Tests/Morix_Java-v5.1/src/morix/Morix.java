package morix;

import java.util.ResourceBundle;

import morix.serveur.ServiceMorix;

/**
 * Classe principale du programme Morix. 
 * 
 * Programme serveur de gestion d'un stock de produits enregistrés dans une base de données (MySQL).
 * Utilisation prévue avec un client Monix.
 * 
 * Programme à usage pédagogique.
 * 
 * @version 5.1
 * @author Matthias Brun
 */
public final class Morix
{
	/**
	 * Fichier de configuration du programme.
	 */
	public static final ResourceBundle CONFIGURATION = ResourceBundle.getBundle("Configuration");

	/**
	 * Constructeur privé de Morix.
	 * 
	 * Ce constructeur privé assure la non-instanciation de Morix dans un programme.
	 * (Morix est la classe principale du programme Morix)
	 */
	private Morix() 
	{
		// Constructeur privé pour assurer la non-instanciation de Morix.
	}

	/**
	 * Main du programme.
	 *
	 * <p>
	 * Cette fonction main lance le programme qui consiste à :
	 * <ul>
	 * <li> créer le service (avec sa connexion à la base de donnée) et
	 * <li> lancer le service de Morix.
	 * </ul>
	 * </p>
	 *
	 * @param args aucun argument attendu.
	 *
	 */
	public static void main(String[] args)
	{	
		System.out.println("Morix 5.1");

		System.out.println("Morix embauche");

		try {
			// Création du service.
			final ServiceMorix morix = new ServiceMorix();
			
			// Lancement du service.
			morix.lanceService(Integer.parseInt(Morix.CONFIGURATION.getString("SERVEUR_PORT").trim()));
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
}

