package vue;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import felix.Felix;

/**
 * Classe de tests de l'application Felix sans Camix de lancé.
 * 
 * <p>
 * Ces tests s'appuient sur Jemmy 2 pour automatiser la manipulation de l'IHM.
 * </p>
 * 
 * <p>
 * Ces tests doivet être exécutés avec un serveur Camix lancé
 *
 * @version 1.0
 * @author Barthélémy Tek
 *
 */
public class FelixTestConnexionImpossible {

	/**
	 * L'application à tester.
	 * 
	 * <p>
	 * L'attribut est static pour garantir qu'une seule application sera considérée
	 * pour cette classe de test quelque soit le nombre de tests effectués.
	 * </p>
	 */
	private static ClassReference felixApp;

	/**
	 * Les paramètres de lancement de l'application.
	 */
	private static String[] parametres;

	/**
	 * Opérateur de JFrame utile pour les tests (pour la manipulation de la fenêtre
	 * Connexion de Felix).
	 */
	private static JFrameOperator fenetreConnexion;

	/**
	 * Opérateur de JButton utile pour les tests (pour la manipulation du bouton
	 * "connexion" de la vue de Connexion de Felix).
	 */
	private static JButtonOperator boutonConnexion;

	/**
	 * Opérateur de JTextField utile pour les tests (pour la manipulation du champ
	 * texte "IP" de l'instance de Felix).
	 */
	private static JTextFieldOperator texteIP;

	/**
	 * Opérateur de JTextField utile pour les tests (pour la manipulation du champ
	 * texte "Port" de l'instance de Felix).
	 */
	private static JTextFieldOperator textePort;

	/**
	 * Opérateur de JTextField utile pour les tests (pour la manipulation du label
	 * "Informations" de l'instance de Felix).
	 */
	private static JLabelOperator labelInfos;

	/**
	 * Configure Jemmy pour les tests et lancements de l'application à tester.
	 *
	 * <p>
	 * Code exécuté avant les tests.
	 * </p>
	 *
	 * @throws Exception
	 *             toute exception.
	 *
	 */
	@BeforeClass
	public static void setUp() throws Exception {
		// Fixe les timeouts de Jemmy
		// (http://wiki.netbeans.org/Jemmy_Operators_Environment#Timeouts),
		// ici : 5s pour l'affichage d'une frame ou une attente de changement d'état
		// (waitText par exemple).
		final Integer timeout = 5000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);

		// Démarrage de l'instance de Felix nécessaire aux tests.
		try {
			FelixTestConnexionImpossible.felixApp = new ClassReference("felix.Felix");
			FelixTestConnexionImpossible.parametres = new String[1];
			FelixTestConnexionImpossible.parametres[0] = ""; // "-b" en mode bouchonné, "" en mode collaboration avec
																// Camix;

			lanceInstance();

			// 10 secondes d'observation par suspension du thread (objectif pédagogique)
			// (pour prendre le temps de déplacer les fenêtres à l'écran).
			final Long timeoutObs = Long.valueOf(10000);
			Thread.sleep(timeoutObs);
		} catch (ClassNotFoundException e) {
			Assert.fail("Problème d'accès à la classe invoquée : " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Implantation pédagogique de temporisation en fin de test pour laisser un
	 * temps d'observation.
	 *
	 * <p>
	 * Code exécuté après les tests.
	 * </p>
	 *
	 * @throws Exception
	 *             toute exception.
	 *
	 */
	@After
	public void tearDown() throws Exception {
		// 5 secondes d'observation par suspension du thread (objectif pédagogique).
		final Long timeout = Long.valueOf(3000);
		Thread.sleep(timeout);
	}

	/**
	 * Lancement d'une instance de Felix.
	 * 
	 * @throws Exception
	 *             toute exception.
	 */
	private static void lanceInstance() throws Exception {
		try {
			// Lancement d'une application.
			FelixTestConnexionImpossible.felixApp.startApplication(FelixTestConnexionImpossible.parametres);
		} catch (InvocationTargetException e) {

			Assert.fail("Problème d'invocation de l'application : " + e.getMessage());
			throw e;
		} catch (NoSuchMethodException e) {
			Assert.fail("Problème d'accès à la méthode invoquée : " + e.getMessage());
			throw e;
		}
		// Récupération de l'interface.
		recuperationInterface();
	}

	/**
	 * Récupération de l'interface d'une instance de Felix.
	 * 
	 * <p>
	 * Cette méthode initialise les attributs de la classe de test pour ce qui
	 * concerne les fenêtres et les widgets de leurs interfaces.
	 * </p>
	 * <p>
	 * Elle vérifie au passage la bonne construction de ces fenêtres, avec titre
	 * adéquat, et les widgets attendus pour leurs interfaces.
	 * </p>
	 * 
	 */
	private static void recuperationInterface() {
		// Récupération de l'interface de la vue client.
		recuperationVueConnexion();
	}

	/**
	 * Récupération de la vue connexion d'une instance de Felix.
	 * 
	 * <p>
	 * Cette méthode concerne la récupération de la fenêtre de la vue caisse, avec
	 * titre adéquat, et des widgets attendus pour cette vue.
	 * </p>
	 *
	 */
	private static void recuperationVueConnexion() {
		// Récupération de la fenêtre de la vue de connexion de Felix (par son nom).
		fenetreConnexion = new JFrameOperator(
				new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_NAME")));
		Assert.assertNotNull("La fenêtre de la vue connexion n'est pas accessible.", fenetreConnexion);

		// Récupération du champ de saisie de l'adresse (par son nom).
		texteIP = new JTextFieldOperator(fenetreConnexion,
				new NameComponentChooser(Felix.CONFIGURATION.getString("CHAMP_IP_NAME")));
		Assert.assertNotNull("Le champ de saisie de l'adresse IP n'est pas accessible.", texteIP);

		// Récupération du champ de saisie du port (par son nom).
		textePort = new JTextFieldOperator(fenetreConnexion,
				new NameComponentChooser(Felix.CONFIGURATION.getString("CHAMP_PORT_NAME")));
		Assert.assertNotNull("Le champ de saisie du port n'est pas accessible.", textePort);

		// Récupération du label d'informations (par son nom).
		labelInfos = new JLabelOperator(fenetreConnexion,
				new NameComponentChooser(Felix.CONFIGURATION.getString("CHAMP_INFOS_NAME")));
		Assert.assertNotNull("Le label d'information n'est pas accessible.", labelInfos);

		// Récupération du bouton de connexion (par son nom).
		boutonConnexion = new JButtonOperator(fenetreConnexion,
				new NameComponentChooser(Felix.CONFIGURATION.getString("BOUTON_CONNEXION_NAME")));
		Assert.assertNotNull("Le bouton de connexion n'est pas accessible.", boutonConnexion);
	}

	/**
	 * Test l'initialisation des différents champs de la vue.
	 * 
	 * <p>
	 * Méthodes concernées :
	 * <ul>
	 * <li>private void construireFenetre()
	 * <li>private void construirePanneaux()
	 * <li>private void construireControles(Integer, Integer)()
	 * </ul>
	 * </p>
	 */
	@Test
	public void testInitialiseVue() {
		/*
		 * Données de test.
		 */
		final String messageInfoDefaut = Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT");
		final String adresseDefaut = Felix.CONFIGURATION.getString("ADRESSE_CHAT");
		final String portDefaut = Felix.CONFIGURATION.getString("PORT_CHAT");
		final String titreFenetreDefaut = Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE");
		final String texteBoutonDefaut = Felix.CONFIGURATION.getString("FENETRE_CONNEXION_BOUTON_CONNECTER");

		/*
		 * Exécution du test.
		 */
		try {
			// Récupération des valeurs des champs de la vue.
			final String titreFenetreActuel = fenetreConnexion.getTitle();
			final String messageInfoActuel = labelInfos.getText();
			final String ipActuel = texteIP.getText();
			final String portActuel = textePort.getText();
			final String texteBoutonActuel = boutonConnexion.getText();

			// Assertions.
			Assert.assertEquals("Titre fenetre par défaut invalide.", titreFenetreDefaut, titreFenetreActuel);
			Assert.assertEquals("Message d'information par défaut invalide.", messageInfoDefaut, messageInfoActuel);
			Assert.assertEquals("Adresse par défaut invalide.", adresseDefaut, ipActuel);
			Assert.assertEquals("Port par défaut invalide.", portDefaut, portActuel);
			Assert.assertEquals("Texte bouton par défaut invalide.", texteBoutonDefaut, texteBoutonActuel);

		} catch (Exception e) {
			Assert.fail("Manipulation de la vue client invalide." + e.getMessage());
		}
	}

	/**
	 * Test de validation de la connexion lorsque Camix n'est pas lancé
	 * 
	 * @throws InterruptedException
	 *             pour la temporisation par suspension du thread.
	 */
	@Test
	public void testConnexionSansCamix_adresseEtPortValides() throws InterruptedException {
		// 1,5 seconde d'observation par suspension du thread
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500);
		textePort.clearText();
		texteIP.clearText();

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		// Validation des valeurs des champs libellé prix du produit.
		final String ipAttendu = "127.0.0.1";
		final String portAttendu = "12345";
		final String infoDefautAttendu = "Saisir l'adresse et le port du serveur chat.";

		textePort.setText(portAttendu);
		texteIP.setText(ipAttendu);
		labelInfos.setText(infoDefautAttendu);

		try {
			// Attente du message d'information.
			labelInfos.waitText(infoDefautAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Informations par defaut invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		boutonConnexion.clickMouse();

		// Validation des valeurs des champs libellé prix du produit ainsi que du
		// ticket.
		final String infoAttendu = "Connexion au chat @" + ipAttendu + ":" + portAttendu + " impossible";

		try {
			// Attente du message d'information de l'achat.
			labelInfos.waitText(infoAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Information d'échec de connexion invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
	}

	/**
	 * Teste de validation de la connexion à un service valide qui n'est pas Camix
	 * 
	 * @throws InterruptedException
	 *             pour la temporisation par suspension du thread.
	 */
	@Test
	public void testConnexionSansCamix_ConnexionServiceNonCamix() throws InterruptedException {
		// 1,5 seconde d'observation par suspension du thread
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500);

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		// Validation des valeurs des champs libellé prix du produit.
		final String ipAttendu = "1.1.1.1";
		final String portAttendu = "80";
		final String infoDefautAttendu = "Saisir l'adresse et le port du serveur chat.";

		textePort.setText(portAttendu);
		texteIP.setText(ipAttendu);
		labelInfos.setText(infoDefautAttendu);

		try {
			// Attente du message d'information.
			labelInfos.waitText(infoDefautAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Informations par defaut invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		boutonConnexion.clickMouse();

		// Validation des valeurs des champs libellé prix du produit ainsi que du
		// ticket.
		final String infoAttendu = "Connexion au chat @" + ipAttendu + ":" + portAttendu + " impossible";

		try {
			// Attente du message d'information de l'achat.
			labelInfos.waitText(infoAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Information d'échec de connexion invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
	}

	/**
	 * Test de validation de la connexion lorsque l'IP est valide mais pas le port
	 * 
	 * @throws InterruptedException
	 *             pour la temporisation par suspension du thread.
	 */
	@Test
	public void testConnexionSansCamix_PortInvalide() throws InterruptedException {
		// 1,5 seconde d'observation par suspension du thread
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500);

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		// Validation des valeurs des champs libellé prix du produit.
		final String ipAttendu = "127.0.0.1";
		final String portAttendu = "80";
		final String infoDefautAttendu = "Saisir l'adresse et le port du serveur chat.";

		textePort.setText(portAttendu);
		texteIP.setText(ipAttendu);
		labelInfos.setText(infoDefautAttendu);

		try {
			// Attente du message d'information.
			labelInfos.waitText(infoDefautAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Informations par defaut invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		boutonConnexion.clickMouse();

		// Validation des valeurs des champs libellé prix du produit ainsi que du
		// ticket.
		final String infoAttendu = "Connexion au chat @" + ipAttendu + ":" + portAttendu + " impossible";

		try {
			// Attente du message d'information de l'achat.
			labelInfos.waitText(infoAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Information d'échec de connexion invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
	}

	/**
	 * Test de validation de la connexion lorsque le port est valide mais pas l'IP
	 * 
	 * @throws InterruptedException
	 *             pour la temporisation par suspension du thread.
	 */
	@Test
	public void testConnexionSansCamix_IPInvalide() throws InterruptedException {
		// 1,5 seconde d'observation par suspension du thread
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500);

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		// Validation des valeurs des champs libellé prix du produit.
		final String ipAttendu = "1.1.1.1";
		final String portAttendu = "12345";
		final String infoDefautAttendu = "Saisir l'adresse et le port du serveur chat.";

		textePort.setText(portAttendu);
		texteIP.setText(ipAttendu);
		labelInfos.setText(infoDefautAttendu);

		try {
			// Attente du message d'information.
			labelInfos.waitText(infoDefautAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Informations par defaut invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		boutonConnexion.clickMouse();

		// Validation des valeurs des champs libellé prix du produit ainsi que du
		// ticket.
		final String infoAttendu = "Connexion au chat @" + ipAttendu + ":" + portAttendu + " impossible";

		try {
			// Attente du message d'information de l'achat.
			labelInfos.waitText(infoAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Information d'échec de connexion invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
	}
}
