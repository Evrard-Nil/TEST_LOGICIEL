package felix;

import java.util.ResourceBundle;

import felix.controleur.ControleurFelix;

/**
 * Classe principale de Felix. 
 * 
 * Client du chat permettant d'échanger des messages avec d'autres utilisateurs 
 * via des composants Felix (ou autres) connectés à un composant serveur Camix.
 * 
 * Programme à usage pédagogique.
 * 
 * ATTENTION : Ce programme comporte des fautes intentionnelles.
 * 
 * @version 3.0
 * @author Matthias Brun 
 *
 */
public final class Felix
{
	/**
	 * Fichier de configuration de Felix.
	 */
	public static final ResourceBundle CONFIGURATION = ResourceBundle.getBundle("Configuration");

	/**
	 * Constructeur privé de Felix.
	 * 
	 * Ce constructeur privé assure la non-instanciation de Felix dans un programme
	 * (Felix est la classe principale du programme Felix).
	 */
	private Felix() 
	{
		// Constructeur privé pour assurer la non-instanciation de Felix.
	}

	/**
	 * Main du programme.
	 *
	 * <p>
	 * Cette fonction lance le programme Felix qui créer une instance de contrôleur de Felix.
	 * </p>
	 *
	 * @param args aucun argument attendu.
	 * 
	 * @see felix.controleur.ControleurFelix
	 */
	public static void main(String[] args)
	{
		System.out.println("Felix v.3.0");

		new ControleurFelix();
	}
}

