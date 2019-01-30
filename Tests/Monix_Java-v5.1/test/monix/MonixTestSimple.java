package monix;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JScrollPaneOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;


/**
 * Classe de tests de l'application Monix.
 * 
 * <p>
 * Ces tests s'appuient sur Jemmy 2 pour automatiser la manipulation de l'IHM.</p>
 * 
 * <p>
 * Ces tests lancent une instance de Monix avec le paramètre "-b" (utilisation d'un bouchon de stock).
 * Ces tests peuvent être exécutés avec un stock Morix si le paramètre "-b" n'est pas passé au 
 * lancement des instances de Monix. Dans ce cas, cela nécessite qu'une instance de serveur Morix soit lancée.</p>
 *
 * @version 5.2
 * @author Matthias Brun
 *
 */
public class MonixTestSimple
{
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
	 * Opérateur de JFrame utile pour les tests 
	 * (pour la manipulation de la fenêtre (ou vue) de la caisse de l'instance de Monix).
	 */
	private JFrameOperator fenetreCaisse;
	
	
	/**
	 * Opérateur de JButton utile pour les tests
	 * (pour la manipulation du bouton "annuler" de la vue de la caisse de l'instance de Monix).
	 */
	private JButtonOperator boutonAnnuler;
	
	/**
	 * Opérateurs de JButton utile pour les tests
	 * (pour la manipulation du bouton "ajouter" de la vue de la caisse de l'instance de Monix).
	 */
	private JButtonOperator boutonAjouter;
	
	/**
	 * Opérateurs de JButton utile pour les tests
	 * (pour la manipulation du bouton "fin de vente" de la vue de la caisse de l'instance de Monix).
	 */
	private JButtonOperator boutonFinVente;

	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "id" de l'instance de Monix).
	 */
	private JTextFieldOperator texteId;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "quantité" de l'instance de Monix).
	 */
	private JTextFieldOperator texteQuantite;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "libellé" de l'instance de Monix).
	 */
	private JTextFieldOperator texteLibelle;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "prix" de l'instance de Monix).
	 */
	private JTextFieldOperator textePrix;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "info" de l'instance de Monix).
	 */
	private JTextFieldOperator texteInfo;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "total" de l'instance de Monix).
	 */
	private JTextFieldOperator texteTotal;

	//
	// Manipulation des widgets des vue client de Monix.
	//
	
	/**
	 * Opérateur de JFrame utile pour les tests 
	 * (pour la manipulation de la fenêtre (ou vue) client de l'instance de Monix).
	 */
	private JFrameOperator fenetreClient;
	
	/**
	 * Les champs texte de la vue.
	 */
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "prix total" de l'instance de Monix).
	 */
	private JTextFieldOperator texteTotalClient;

	/**
	 * Opérateur de TextPane utile pour les tests
	 * (pour la manipulation du panneau texte de la vue du ticket de l'instance de Monix).
	 */
	private JTextPaneOperator texteTicket;

	/**
	 * Opérateur de JScrollPane utile pour les tests
	 * (pour la manipulation du panneau de défilement du ticket de l'instance de Monix).
	 */
	private JScrollPaneOperator defilementTicket;

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
	@Before
	public void setUp() throws Exception
	{
		// Fixe les timeouts de Jemmy (http://wiki.netbeans.org/Jemmy_Operators_Environment#Timeouts),
		// ici : 3s pour l'affichage d'une frame ou une attente de changement d'état (waitText par exemple).
		final Integer timeout = 3000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);
		
		// Démarrage de l'instance de Monix nécessaire aux tests.
		try {
			MonixTestSimple.application = new ClassReference("monix.Monix");
			MonixTestSimple.parametres = new String[1];
			MonixTestSimple.parametres[0] = "-b"; //"-b" en mode bouchonné, "" en mode collaboration avec Morix;
			
			this.lanceInstance();
			
			// 10 secondes d'observation par suspension du thread (objectif pédagogique)
			// (pour prendre le temps de déplacer les fenêtres à l'écran).
			final Long timeoutObs = Long.valueOf(10000); 
			Thread.sleep(timeoutObs);
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
	 * Lancement d'une instance de Monix.
	 * 
	 * @throws Exception toute exception.
	 */
	private void lanceInstance() throws Exception
	{
		try {
			// Lancement d'une application.
			MonixTestSimple.application.startApplication(MonixTestSimple.parametres);
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
		this.recuperationInterface();
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
	 */
	private void recuperationInterface() 
	{
		// Récupération de l'interface de la vue caisse.
		this.recuperationVueCaisse();
		
		// Récupération de l'interface de la vue client.
		this.recuperationVueClient();
		
		// Récupération de l'interface de la vue stock.
		// (TODO)
	}
	
	/**
	 * Récupération de la vue caisse d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne la récupération de la fenêtre de la vue caisse, 
	 * avec titre adéquat, et des widgets attendus pour cette vue.</p>
	 *
	 */
	private void recuperationVueCaisse()
	{
		// Index pour la récupération des widgets.
		Integer iW = 0;
		
		// Récupération de la fenêtre de la vue de la caisse.
		this.fenetreCaisse = new JFrameOperator(Monix.MESSAGES.getString("CAISSE_FENETRE_TITRE"));
		Assert.assertNotNull("La fenêtre de la vue caisse n'est pas accessible.", this.fenetreCaisse);
	
		// Récupération du champ de saisie d'un identifiant produit.
		this.texteId = new JTextFieldOperator(this.fenetreCaisse, iW++);
		Assert.assertNotNull("Le champ de saisie des indentifiants produits n'est pas accessible.", this.texteId);
		
		// Récupération du champ de saisie de la quantité de produit.
		this.texteQuantite = new JTextFieldOperator(this.fenetreCaisse, iW++);
		Assert.assertNotNull(
				"Le champ de saisie de la quantité de produits n'est pas accessible.",
				this.texteQuantite);
		
		// Récupération du champ de libellé d'un produit.
		this.texteLibelle = new JTextFieldOperator(this.fenetreCaisse, iW++);
		Assert.assertNotNull("Le champ du libellé du produits n'est pas accessible.", this.texteLibelle);
		
		// Récupération du champ de prix d'un produit.
		this.textePrix = new JTextFieldOperator(this.fenetreCaisse, iW++);
		Assert.assertNotNull("Le champ du prix d'un produit n'est pas accessible.", this.textePrix);
		
		// Récupération du champ d'information d'un achat.
		this.texteInfo = new JTextFieldOperator(this.fenetreCaisse, iW++);
		Assert.assertNotNull("Le champ d'information d'un achat n'est pas accessible.", this.texteInfo);
		
		// Récupération du champ de prix total de la vente.
		this.texteTotal = new JTextFieldOperator(this.fenetreCaisse, iW);
		Assert.assertNotNull("Le champ de prix total de la vente n'est pas eccessible.", this.texteTotal);
		
		// Ré-initialisation de l'index des widgets pour la récupération des boutons.
		iW = 0;
		
		// Récupération du bouton d'ajout d'un produit à la vente.
		this.boutonAjouter = new JButtonOperator(this.fenetreCaisse, iW++);
		Assert.assertNotNull("Le bouton d'ajout d'un produit de la vente n'est pas accessible.", this.boutonAjouter);
		
		// Récupération du bouton d'annulation d'un produit à la vente.
		this.boutonAnnuler = new JButtonOperator(this.fenetreCaisse, iW++);
		Assert.assertNotNull(
				"Le bouton d'annulation d'un produit de la vente n'est pas accessible.", 
				this.boutonAnnuler);
		
		// Récupération du bouton de clôture de la vente.
		this.boutonFinVente = new JButtonOperator(this.fenetreCaisse, iW);
		Assert.assertNotNull("Le bouton de clôture de la vente n'est pas accessible.", this.boutonFinVente);
	}

	/**
	 * Récupération de la vue client d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne la récupération de la fenêtre de la vue client, 
	 * avec titre adéquat, et des widgets attendus pour cette vue.</p>
	 *
	 */
	private void recuperationVueClient()
	{
		// Récupération de la fenêtre de la vue client.
		this.fenetreClient = new JFrameOperator(Monix.MESSAGES.getString("CLIENT_FENETRE_TITRE"));
		Assert.assertNotNull("La fenêtre de la vue client n'est pas accessible.", this.fenetreClient);
		
		// Récupération du champ d'information du prix total de la vente.
		this.texteTotalClient = new JTextFieldOperator(this.fenetreClient, 0);
		Assert.assertNotNull(
				"Le champ d'information du prix total client n'est pas accessible.", 
				this.texteTotalClient);

		// Récupération du panneau texte de la vue (ticket).
		this.texteTicket = new JTextPaneOperator(this.fenetreClient, 0);
		Assert.assertNotNull("Le panneau texte de la vue (ticket) n'est pas accessible.", this.texteTicket);
		
		// Récupération du panneau de défilement du ticket de la vue.
		this.defilementTicket = new JScrollPaneOperator(this.fenetreClient, 0);
		Assert.assertNotNull("Le panneau de défilement du ticket n'est pas accessible.", this.defilementTicket);	
	}
	
	
	/**
	 * Test l'achat d'un produit.
	 * 
	 * @throws InterruptedException  pour la temporisation par suspension du thread.
	 */
	@Test
	public void testAchatProduit() throws InterruptedException
	{
		// 1,5 seconde d'observation par suspension du thread 
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500); 

		this.texteId.clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
				
		this.texteId.typeText("11A");
		
		// Forcer la perte de focus du champ de saisie de l'identifiant d'un produit
		// en donnant le focus au champ de saisie de la quantité de produit.
		this.texteQuantite.clickMouse();
		
		
		// Validation des valeurs des champs libellé prix du produit.
		final String libelleAttendu = "produit un A";
		final String prixAttendu = String.format("%1$.2f €", 10.0);
		
		try {
			// Attente du message du libellé.
			this.texteLibelle.waitText(libelleAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Libellé pour du produit (11A) invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.textePrix.waitText(prixAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Prix du produit (11A) invalide.");
		}
		
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
					
		this.texteQuantite.clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		this.texteQuantite.typeText("2");	
		// Force la perte du focus du champ texteQuantite pour que celui-ci 
		// formatte la valeur saisie (ce champ est un JFormattedTextField).
		this.texteId.clickMouse();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		this.boutonAjouter.clickMouse();
		
		
		// Validation des valeurs des champs libellé prix du produit ainsi que du ticket.
		final String infoAttendu = String.format("+ produit un A   x   2  x %1$.2f €", 10.0);
		final String totalAttendu = String.format("%1$.2f €", 20.0);
		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET") 
				+ System.getProperty("line.separator")
				+ String.format("produit un A       x 2      x    %1$.2f €", 10.0);
		final String totalClientAttendu = String.format("%1$.2f €", 20.0);
		
		try {
			// Attente du message d'information de l'achat.
			this.texteInfo.waitText(infoAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Information pour l'achat du produit (11A) invalide.");
		}
		
		try {
			// Attente du message du prix de l'achat.
			this.texteTotal.waitText(totalAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Prix total pour l'achat du produit (11A) invalide.");
		}
				
		try {
			// Attente du ticket.
			this.texteTicket.waitText(ticketAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Ticket pour l'achat du produit (11A) invalide.");
		}
		
		try {
			// Attente du message du prix total de la vente.
			this.texteTotalClient.waitText(totalClientAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Prix total pour la vente du produit (11A) invalide.");
		}
		
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		this.boutonFinVente.clickMouse();

		// (TODO assertions)
	}
}

