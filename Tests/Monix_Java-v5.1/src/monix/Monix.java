package monix;

import java.util.Locale;
import java.util.ResourceBundle;

import monix.controleur.ControleurVente;
import monix.modele.stock.Stock;
import monix.modele.stock.StockBouchon;
import monix.modele.stock.StockMorix;
import monix.modele.vente.Vente;

/**
 * Classe principale du programme Monix. 
 * 
 * Programme de caisse enregistreuse de vente de produits.
 * Programme à usage pédagogique.
 * 
 * @version 5.1
 * @author Matthias Brun
 */
public final class Monix
{
	/**
	 * Fichier de configuration du programme.
	 */
	public static final ResourceBundle CONFIGURATION = ResourceBundle.getBundle("Configuration");
	
	/**
	 * Fichier des messages du programme dans la langue déduite du fichier de configuration.
	 */
	public final static ResourceBundle MESSAGES = ResourceBundle.getBundle("Messages", 
		new Locale(Monix.CONFIGURATION.getString("LANGAGE").trim(), Monix.CONFIGURATION.getString("PAYS").trim()));
	
	/**
	 * Option de lancement du programme avec un bouchon du stock.
	 */
	private final static String OPTION_BOUCHON_STOCK = "-b";
	
	/**
	 * Constructeur privé de Monix.
	 * 
	 * Ce constructeur privé assure la non-instanciation de Monix dans un programme.
	 * (Monix est la classe principale du programme Monix)
	 */
	private Monix() 
	{
		// Constructeur privé pour assurer la non-instanciation de Monix.
	}

	/**
	 * Main du programme.
	 *
	 * <p>
	 * Cette fonction main lance le programme qui consiste à :
	 * <ul>
	 * <li> créer une instance de stock (bouchon ou stock distant) ;</li>
	 * <li> créer une instance de vente ;</li>
	 * <li> créer une instance de contrôleur de vente.</li>
	 * </ul>
	 * </p>
	 * <p>
	 * Remarque : les instances de vues de la vente sont créées par le contrôleur (architecture MVC).
	 * </p>
	 *
	 * @param args un argument pour lancer le programme avec un bouchon du stock est possible.
	 */
	public static void main(String[] args)
	{	
		System.out.println("Monix 5.1");
		System.out.println("Monix embauche");

		Stock stock = null;

		try {
			if ((args.length > 0) && (OPTION_BOUCHON_STOCK.equals(args[0]))) {
				System.out.println("Création du stock (bouchon)");
				stock = new StockBouchon();
			} else {
				System.out.println("Connexion au stock de Morix");
				stock = new StockMorix(
						Monix.CONFIGURATION.getString("STOCK_ADRESSE").trim(),
						Integer.parseInt(Monix.CONFIGURATION.getString("STOCK_PORT").trim())
				);
			}
			
			final Vente vente = new Vente(stock);

			new ControleurVente(vente, stock);
		}
		catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
}
