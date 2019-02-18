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
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import felix.Felix;

/**
 * Classe de tests de l'application Felix avec un Camix lancé
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
public class FelixTestConnexionPossible {

	/**
	 * L'application à tester.
	 * 
	 * <p>
	 * L'attribut est static pour garantir qu'une seule application sera considérée
	 * pour cette classe de test quelque soit le nombre de tests effectués.
	 * </p>
	 */
	private static ClassReference application;

	/**
	 * Nombre d'instances d'application Felix impliquées dans le test.
	 */
	private static final int NBINSTANCES = 4;

	private static int instanceCourrante = 0;

	/**
	 * Les paramètres de lancement de l'application.
	 */
	private static String[] parametres;

	/**
	 * Opérateur de JFrame utile pour les tests (pour la manipulation de la fenêtre
	 * Connexion de Felix).
	 */
	private static JFrameOperator[] fenetreConnexion = new JFrameOperator[NBINSTANCES];

	/**
	 * Opérateur de JFrame utile pour les tests (pour la manipulation de la fenêtre
	 * Chat de Felix).
	 */
	private static JFrameOperator[] fenetreChat = new JFrameOperator[NBINSTANCES];

	/**
	 * Opérateur de JButton utile pour les tests (pour la manipulation du bouton
	 * "connexion" de la vue de Connexion de Felix).
	 */
	private static JButtonOperator[] boutonConnexion = new JButtonOperator[NBINSTANCES];

	/**
	 * Opérateur de JTextField utile pour les tests (pour la manipulation du champ
	 * texte "IP" de l'instance de Felix).
	 */
	private static JTextFieldOperator[] texteIP = new JTextFieldOperator[NBINSTANCES];

	/**
	 * Opérateur de JTextField utile pour les tests (pour la manipulation du champ
	 * texte "Port" de l'instance de Felix).
	 */
	private static JTextFieldOperator[] textePort = new JTextFieldOperator[NBINSTANCES];

	/**
	 * Opérateur de JTextField utile pour les tests (pour la manipulation du label
	 * "Informations" de l'instance de Felix).
	 */
	private static JLabelOperator[] labelInfos = new JLabelOperator[NBINSTANCES];

	/**
	 * Opérateur de JTextField utile pour les tests (pour la manipulation du champ
	 * texte "message" de l'instance de Felix).
	 */
	private static JTextFieldOperator[] textMessages = new JTextFieldOperator[NBINSTANCES];

	/**
	 * Opérateur de JTextPane utile pour les tests (pour la manipulation du champ
	 * d'affichage "Message" de l'instance de Felix).
	 */
	private static JTextPaneOperator[] messages = new JTextPaneOperator[NBINSTANCES];

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
		// ici : 3s pour l'affichage d'une frame ou une attente de changement d'état
		// (waitText par exemple).
		final Integer timeout = 3000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);

		// Démarrage de l'instance de Felix nécessaire aux tests.
		try {
			FelixTestConnexionPossible.application = new ClassReference("felix.Felix");
			FelixTestConnexionPossible.parametres = new String[1];
			FelixTestConnexionPossible.parametres[0] = ""; // "-b" en mode bouchonné, "" en mode collaboration avec
															// Camix;

			lanceInstancesFelix();

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
		final Long timeout = Long.valueOf(5000);
		Thread.sleep(timeout);
		instanceCourrante++;
	}

	/**
	 * Lancement d'une instance de Felix.
	 * 
	 * @throws Exception
	 *             toute exception.
	 */
	private static void lanceInstance(int i) throws Exception {
		try {
			// Lancement d'une application.
			FelixTestConnexionPossible.application.startApplication(FelixTestConnexionPossible.parametres);
		} catch (InvocationTargetException e) {

			Assert.fail("Problème d'invocation de l'application : " + e.getMessage());
			throw e;
		} catch (NoSuchMethodException e) {
			Assert.fail("Problème d'accès à la méthode invoquée : " + e.getMessage());
			throw e;
		}
		// Récupération de l'interface.
		recuperationInterface(i);
	}

	/**
	 * Lancement de toutes les instances de Felix nécessaires aux test.
	 * 
	 * @throws Exception
	 *             toute exception.
	 */
	private static void lanceInstancesFelix() throws Exception {
		for (int index = 0; index < NBINSTANCES; index++) {

			// Lance une instance de Felix.
			lanceInstance(index);

			// 10 secondes d'observation par suspension du thread (objectif pédagogique)
			// (pour prendre le temps de déplacer les fenêtres à l'écran).
			final Long timeout = Long.valueOf(10000);
			Thread.sleep(timeout);
		}
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
	private static void recuperationInterface(int i) {
		// Récupération de l'interface de la vue client.
		recuperationVueConnexion(i);
	}

	/**
	 * Récupération de la vue connexion d'une instance de Felix.
	 * 
	 * <p>
	 * Cette méthode concerne la récupération de la fenêtre de la vue connexion,
	 * avec titre adéquat, et des widgets attendus pour cette vue.
	 * </p>
	 *
	 */
	private static void recuperationVueConnexion(int i) {
		// Récupération de la fenêtre de la vue Connexion de Felix (par son nom).
		fenetreConnexion[i] = new JFrameOperator(
				new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_NAME")), i);
		Assert.assertNotNull("La fenêtre de la vue connexion n'est pas accessible.", fenetreConnexion[i]);

		// Récupération du champ de saisie d'adresse (par son nom).
		texteIP[i] = new JTextFieldOperator(fenetreConnexion[i],
				new NameComponentChooser(Felix.CONFIGURATION.getString("CHAMP_IP_NAME")));
		Assert.assertNotNull("Le champ de saisie de l'adresse IP n'est pas accessible.", texteIP[i]);

		// Récupération du champ de saisie du port (par son nom).
		textePort[i] = new JTextFieldOperator(fenetreConnexion[i],
				new NameComponentChooser(Felix.CONFIGURATION.getString("CHAMP_PORT_NAME")));
		Assert.assertNotNull("Le champ de saisie du port n'est pas accessible.", textePort[i]);

		// Récupération du label d'informations (par son nom).
		labelInfos[i] = new JLabelOperator(fenetreConnexion[i],
				new NameComponentChooser(Felix.CONFIGURATION.getString("CHAMP_INFOS_NAME")));
		Assert.assertNotNull("Le label d'information n'est pas accessible.", labelInfos[i]);

		// Récupération du bouton de connexion (par son nom).
		boutonConnexion[i] = new JButtonOperator(fenetreConnexion[i],
				new NameComponentChooser(Felix.CONFIGURATION.getString("BOUTON_CONNEXION_NAME")));
		Assert.assertNotNull("Le bouton de connexion n'est pas accessible.", boutonConnexion[i]);
	}

	/**
	 * Récupération de la vue Chat d'une instance de Felix.
	 * 
	 * <p>
	 * Cette méthode concerne la récupération de la fenêtre de la vue chat, avec
	 * titre adéquat, et des widgets attendus pour cette vue.
	 * </p>
	 *
	 */
	private static void recuperationVueChat(int i) {
		// Récupération de la fenêtre de la vue Chat de Felix (par son nom).
		fenetreChat[i] = new JFrameOperator(
				new NameComponentChooser(Felix.CONFIGURATION.getString("FENETRE_CHAT_TITRE")), i);
		Assert.assertNotNull("La fenêtre de la vue chat n'est pas accessible.", fenetreChat[i]);

		// Récupération du champ de saisie d'un message (par son nom).
		textMessages[i] = new JTextFieldOperator(fenetreChat[i],
				new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_MESSAGE_NAME")));
		Assert.assertNotNull("Le champ d'affichage des messages n'est pas accessible.", textMessages[i]);

		// Récupération du champ d'affichage des messages (par son nom).
		messages[i] = new JTextPaneOperator(fenetreChat[i],
				new NameComponentChooser(Felix.CONFIGURATION.getString("TEXT_CHAT_NAME")));
		Assert.assertNotNull("Le champ d'affichage des messages n'est pas accessible.", messages[i]);
	}

	/**
	 * Test de validation la connexion lorsque Camix est lancé, avec l'adresse et le
	 * port par défaut
	 * 
	 * @throws InterruptedException
	 *             pour la temporisation par suspension du thread.
	 */
	@Test
	public void testConnexionAvecCamix_adresseEtPortDefauts() throws InterruptedException {
		// 1,5 seconde d'observation par suspension du thread
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500);

		boutonConnexion[instanceCourrante].clickMouse();

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		final String messageAttendu = "* Taper /h pour avoir de l'aide sur les commandes du chat.\n\n";

		// Récupération de la vue Chat
		recuperationVueChat(instanceCourrante);

		Assert.assertEquals(messageAttendu, messages[instanceCourrante].getText());

	}

	/**
	 * Test de validation la connexion lorsque Camix est lancé, avec une adresse et
	 * un port valide
	 * 
	 * @throws InterruptedException
	 *             pour la temporisation par suspension du thread.
	 */
	@Test
	public void testConnexionAvecCamix_adresseEtPortValides() throws InterruptedException {
		// 1,5 seconde d'observation par suspension du thread
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500);
		textePort[instanceCourrante].clearText();
		texteIP[instanceCourrante].clearText();

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		// Validation des valeurs des champs libellé prix du produit.
		final String ipAttendu = "127.0.0.1";
		final String portAttendu = "12345";
		final String infoDefautAttendu = "Saisir l'adresse et le port du serveur chat.";

		textePort[instanceCourrante].setText(portAttendu);
		texteIP[instanceCourrante].setText(ipAttendu);
		labelInfos[instanceCourrante].setText(infoDefautAttendu);

		try {
			// Attente du message d'information.
			labelInfos[instanceCourrante].waitText(infoDefautAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Informations par defaut invalide.");
		}

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		boutonConnexion[instanceCourrante].clickMouse();

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		final String messageAttendu = "* Taper /h pour avoir de l'aide sur les commandes du chat.\n\n";

		// Récupération de la vue Chat
		recuperationVueChat(instanceCourrante);

		Assert.assertEquals(messageAttendu, messages[instanceCourrante].getText());

	}

	/**
	 * Test de validation la connexion lorsque Camix est lancé sur deux instances.
	 * 
	 * @throws InterruptedException
	 *             pour la temporisation par suspension du thread.
	 */

	@Test
	public void testConnexionAvecCamix_deuxInstances() throws InterruptedException, Exception {
		// 1,5 seconde d'observation par suspension du thread
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500);

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		boutonConnexion[2].clickMouse();

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);

		// Récupération de la vue Chat
		recuperationVueChat(2);

		final String messageAvantConnexion = messages[2].getText();

		boutonConnexion[3].clickMouse();

		final String messageAttendu = messageAvantConnexion
				+ "* Un nouvel utilisateur est dans le chat (place publique).\n\n";

		Thread.sleep(timeout);

		Assert.assertEquals(messageAttendu, messages[2].getText());
	}
}
