package monix;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JScrollPaneOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;


/**
 * Classe de tests de l'application Monix.
 * 
 * <p>
 * Ces tests s'appuient sur Jemmy 2 pour automatiser la manipulation de l'IHM.
 * Ils illustrent en particulier la manipulation de plusieurs instances du logiciels testé ainsi que 
 * l'enchainement de plusieurs tests sur ces instances.
 * </p>
 * 
 * <p>
 * Ces tests lancent deux instances de Monix avec le paramètre "-b" (utilisation d'un bouchon de stock).
 * Ces tests peuvent être exécutés avec un stock Morix si le paramètre "-b" n'est pas passé au 
 * lancement des instances de Monix. Dans ce cas, cela nécessite qu'une instance de serveur Morix soit lancée.</p>
 *
 * @version 5.2
 * @author Matthias Brun
 *
 */
public class MonixTestMulti
{
	/**
	 * Nombre d'instances d'application Monix impliquées dans le test.
	 */
	private static final int NBINSTANCES = 2;
	
	/**
	 * L'application à tester.
	 * 
	 * <p>
	 * L'attribut est static pour garantir qu'une seule 
	 * application sera considérée pour cette classe de test.
	 * </p>
	 */
	private static ClassReference application;

	/**
	 * Les paramètres de lancement de l'application.
	 */
	private static String[] parametres;
	
	//
	// Manipulation des widgets des vues caisse de Monix.
	//
	
	/**
	 * Opérateurs de JFrame utiles pour les tests 
	 * (pour la manipulation des fenêtres (ou vue) de la caisse des différentes instances de Monix).
	 */
	private static JFrameOperator[] fenetreCaisse = new JFrameOperator[NBINSTANCES];
	
	
	/**
	 * Opérateurs de JButton utiles pour les tests
	 * (pour la manipulation des boutons "annuler" de la vue de la caisse des différentes instances de Monix).
	 */
	private static JButtonOperator[] boutonAnnuler = new JButtonOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JButton utiles pour les tests
	 * (pour la manipulation des boutons "ajouter" de la vue de la caisse des différentes instances de Monix).
	 */
	private static JButtonOperator[] boutonAjouter = new JButtonOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JButton utiles pour les tests
	 * (pour la manipulation des boutons "fin de vente" de la vue de la caisse des différentes instances de Monix).
	 */
	private static JButtonOperator[] boutonFinVente = new JButtonOperator[NBINSTANCES];

	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "id" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteId = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "quantité" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteQuantite = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "libellé" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteLibelle = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "prix" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] textePrix = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "info" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteInfo = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "total" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteTotal = new JTextFieldOperator[NBINSTANCES];

	//
	// Manipulation des widgets des vue client de Monix.
	//
	
	/**
	 * Opérateurs de JFrame utiles pour les tests 
	 * (pour la manipulation des fenêtres (ou vue) client des différentes instances de Monix).
	 */
	private static JFrameOperator[] fenetreClient = new JFrameOperator[NBINSTANCES];
	
	/**
	 * Les champs texte de la vue.
	 */
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "prix total" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteTotalClient = new JTextFieldOperator[NBINSTANCES];

	/**
	 * Opérateur de TextPane utiles pour les tests
	 * (pour la manipulation des panneaux textes des vues des tickets des différentes instances de Monix).
	 */
	private static JTextPaneOperator[] texteTicket = new JTextPaneOperator[NBINSTANCES];

	/**
	 * Opérateurs de JScrollPane utiles pour les tests
	 * (pour la manipulation des panneaux de défilement du ticket des différentes instances de Monix).
	 */
	private static JScrollPaneOperator[] defilementTicket = new JScrollPaneOperator[NBINSTANCES];

	/**
	 * Positions courantes dans les panneaux textes des vues des tickets des différentes instances de Monix.
	 */
	private static int[] panPosition = new int[NBINSTANCES];
	
	//
	// Manipulation des widgets des vue stock de Monix.
	//
	// (TODO)
	
	/**
	 * Configure Jemmy pour les tests et lancements de l'application à tester.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		// Fixe les timeouts de Jemmy (http://wiki.netbeans.org/Jemmy_Operators_Environment#Timeouts),
		// ici : 3s pour l'affichage d'une frame ou une attente de changement d'état (waitText par exemple).
		final Integer timeout = 3000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);
		
		// Démarrage des instances de Monix nécessaires aux tests,
		// si celles-ci ne sont pas déjà lancées.
		try {
			MonixTestMulti.application = new ClassReference("monix.Monix");
			MonixTestMulti.parametres = new String[1];
			MonixTestMulti.parametres[0] = "-b"; //"-b" en mode bouchonné, "" en mode collaboration avec Morix;
			
			lanceInstancesMonix();
		}
		catch (ClassNotFoundException e) {
			Assert.fail("Problème d'accès à la classe invoquée : " + e.getMessage());
			throw e;
		}
	}
	
	
	/**
	 * Implantation pédagogique de temporisation en 
	 * fin de test pour laisser un temps d'observation.
	 *
	 * <p>Code exécuté après les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@After
	public void tearDown() throws Exception 
	{
		// 5 secondes d'observation par suspension du thread (objectif pédagogique).
		final Long timeout = Long.valueOf(5000); 
		Thread.sleep(timeout);
	}
	
	/**
	 * Lancement de toutes les instances de Monix nécessaires aux test.
	 * 
	 * @throws Exception toute exception.
	 */
	private static void lanceInstancesMonix() throws Exception
	{
		for (int index = 0; index < NBINSTANCES; index++) {
			
			// Lance une instance de Monix. 
			lanceInstance(index);
			
			// 10 secondes d'observation par suspension du thread (objectif pédagogique)
			// (pour prendre le temps de déplacer les fenêtres à l'écran).
			final Long timeout = Long.valueOf(10000); 
			Thread.sleep(timeout);
		}
	}
	
	/**
	 * Lancement d'une instance de Monix.
	 * 
	 * @param index l'index de l'instance de Monix concernée.
	 * 
	 * @throws Exception toute exception.
	 */
	private static void lanceInstance(int index) throws Exception
	{
		try {
			// Lancement d'une application.
			MonixTestMulti.application.startApplication(MonixTestMulti.parametres);
			
			// Initialisation de la position dans le panel du ticket client.
			MonixTestMulti.panPosition[index] = 0;
		}
		catch (InvocationTargetException e) {
			Assert.fail("Problème d'invocation de l'application : " + e.getMessage());
			throw e;
		} 
		catch (NoSuchMethodException e) {
			Assert.fail("Problème d'accès à la méthode invoquée : " + e.getMessage());
			throw e;
		}
		// Récupération de l'interface.
		recuperationInterface(index);
	}
	
	/**
	 * Récupération de l'interface d'une instance de Monix.
	 * 
	 * <p>
	 * Cette méthode initialise les attributs de la classe de test pour ce
	 * qui concerne les fenêtres et les widgets de leurs interfaces.</p>
	 * <p>
	 * Elle vérifie au passage la bonne construction de ces fenêtres, 
	 * avec titre adéquat, et les widgets attendus pour leurs interfaces.</p>
	 * 
	 * @param index l'index de l'instance de Monix dont on doit récupérer l'interface.
	 */
	private static void recuperationInterface(int index) 
	{
		// Récupération de l'interface de la vue caisse.
		recuperationVueCaisse(index);
		
		// Récupération de l'interface de la vue client.
		recuperationVueClient(index);
		
		// Récupération de l'interface de la vue stock.
		// (TODO)
	}
	
	/**
	 * Récupération de la vue caisse d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne la récupération de la fenêtre de la vue caisse, 
	 * avec titre adéquat, et des widgets attendus pour cette vue.</p>
	 * 
	 * @param index l'index de l'instance de Monix dont on doit récupérer la vue caisse.
	 */
	private static void recuperationVueCaisse(int index)
	{
		// Index pour la récupération des widgets.
		Integer iW = 0;
		
		// Récupération de la fenêtre de la vue de la caisse.
		fenetreCaisse[index] = new JFrameOperator(Monix.MESSAGES.getString("CAISSE_FENETRE_TITRE"), index);
		Assert.assertNotNull("La fenêtre de la vue caisse n'est pas accessible.", fenetreCaisse[index]);
	
		// Récupération du champ de saisie d'un identifiant produit.
		texteId[index] = new JTextFieldOperator(fenetreCaisse[index], iW++);
		Assert.assertNotNull("Le champ de saisie des indentifiants produits n'est pas accessible.", texteId[index]);
		
		// Récupération du champ de saisie de la quantité de produit.
		texteQuantite[index] = new JTextFieldOperator(fenetreCaisse[index], iW++);
		Assert.assertNotNull(
				"Le champ de saisie de la quantité de produits n'est pas accessible.",
				texteQuantite[index]);
		
		// Récupération du champ de libellé d'un produit.
		texteLibelle[index] = new JTextFieldOperator(fenetreCaisse[index], iW++);
		Assert.assertNotNull("Le champ du libellé du produits n'est pas accessible.", texteLibelle[index]);
		
		// Récupération du champ de prix d'un produit.
		textePrix[index] = new JTextFieldOperator(fenetreCaisse[index], iW++);
		Assert.assertNotNull("Le champ du prix d'un produit n'est pas accessible.", textePrix[index]);
		
		// Récupération du champ d'information d'un achat.
		texteInfo[index] = new JTextFieldOperator(fenetreCaisse[index], iW++);
		Assert.assertNotNull("Le champ d'information d'un achat n'est pas accessible.", texteInfo[index]);
		
		// Récupération du champ de prix total de la vente.
		texteTotal[index] = new JTextFieldOperator(fenetreCaisse[index], iW);
		Assert.assertNotNull("Le champ de prix total de la vente n'est pas eccessible.", texteTotal[index]);
		
		// Ré-initialisation de l'index des widgets pour la récupération des boutons.
		iW = 0;
		
		// Récupération du bouton d'ajout d'un produit à la vente.
		boutonAjouter[index] = new JButtonOperator(fenetreCaisse[index], iW++);
		Assert.assertNotNull("Le bouton d'ajout d'un produit de la vente n'est pas accessible.", boutonAjouter[index]);
		
		// Récupération du bouton d'annulation d'un produit à la vente.
		boutonAnnuler[index] = new JButtonOperator(fenetreCaisse[index], iW++);
		Assert.assertNotNull(
				"Le bouton d'annulation d'un produit de la vente n'est pas accessible.", 
				boutonAnnuler[index]);
		
		// Récupération du bouton de clôture de la vente.
		boutonFinVente[index] = new JButtonOperator(fenetreCaisse[index], iW);
		Assert.assertNotNull("Le bouton de clôture de la vente n'est pas accessible.", boutonFinVente[index]);
	}

	/**
	 * Récupération de la vue client d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne la récupération de la fenêtre de la vue client, 
	 * avec titre adéquat, et des widgets attendus pour cette vue.</p>
	 * 
	 * @param index l'index de l'instance de Monix dont on doit récupérer la vue client.
	 */
	private static void recuperationVueClient(int index)
	{
		// Récupération de la fenêtre de la vue client.
		fenetreClient[index] = new JFrameOperator(Monix.MESSAGES.getString("CLIENT_FENETRE_TITRE"), index);
		Assert.assertNotNull("La fenêtre de la vue client n'est pas accessible.", fenetreClient[index]);
		
		// Récupération du champ d'information du prix total de la vente.
		texteTotalClient[index] = new JTextFieldOperator(fenetreClient[index], 0);
		Assert.assertNotNull(
				"Le champ d'information du prix total client n'est pas accessible.", 
				texteTotalClient[index]);

		// Récupération du panneau texte de la vue (ticket).
		texteTicket[index] = new JTextPaneOperator(fenetreClient[index], 0);
		Assert.assertNotNull("Le panneau texte de la vue (ticket) n'est pas accessible.", texteTicket[index]);
		
		// Récupération du panneau de défilement du ticket de la vue.
		defilementTicket[index] = new JScrollPaneOperator(fenetreClient[index], 0);
		Assert.assertNotNull("Le panneau de défilement du ticket n'est pas accessible.", defilementTicket[index]);	
	}
	

	/**
	 * Test d'achats de produit par la première instance de Monix.
	 * 
	 * @throws InterruptedException  pour la temporisation par suspension du thread.
	 */
	@Test
	public void testAchatProduits() throws InterruptedException
	{
		// 1,5 seconde d'observation par suspension du thread 
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500); 

		texteId[0].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
				
		texteId[0].typeText("11A");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
					
		texteQuantite[0].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteQuantite[0].typeText("1");
		// Force la perte du focus du champ texteQuantite pour que celui-ci 
		// formatte la valeur saisie (ce champ est un JFormattedTextField).
		texteId[0].clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		boutonAjouter[0].clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteId[0].clearText();
		texteId[0].typeText("22A");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteQuantite[0].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteQuantite[0].typeText("2");
		// Force la perte du focus du champ texteQuantite pour que celui-ci 
		// formatte la valeur saisie (ce champ est un JFormattedTextField).
		texteId[0].clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		boutonAjouter[0].clickMouse();
	
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		boutonFinVente[0].clickMouse();
		
		// (TODO assertions)
	}
	

	/**
	 * Test d'annulation d'achats de produits par la deuxième instance de Monix.
	 * 
	 * @throws InterruptedException  pour la temporisation par suspension du thread.
	 */
	@Test
	public void testAnnulationProduits() throws InterruptedException
	{
		// 1,5 seconde d'observation par suspension du thread 
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500); 


		texteId[1].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteId[1].typeText("11A");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteQuantite[1].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteQuantite[1].typeText("1");
		// Force la perte du focus du champ texteQuantite pour que celui-ci 
		// formatte la valeur saisie (ce champ est un JFormattedTextField).
		texteId[1].clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		boutonAnnuler[1].clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteId[1].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteId[1].typeText("22A");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteQuantite[1].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		texteQuantite[1].typeText("2");
		// Force la perte du focus du champ texteQuantite pour que celui-ci 
		// formatte la valeur saisie (ce champ est un JFormattedTextField).
		texteId[1].clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		boutonAnnuler[1].clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		boutonFinVente[1].clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// (TODO assertions)
	}
}

