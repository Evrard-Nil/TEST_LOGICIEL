package monix.vue;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;

import monix.Monix;
import monix.controleur.ControleurVente;
import monix.modele.stock.Produit;
import monix.modele.stock.Stock;

/**
 * Classe de tests unitaires JUnit 4 de la classe VueCaisse.
 *
 * <p>Utilisation d'un mock de stock (construit avec EasyMock).</p>
 * <p>Utilisation d'un mock de contrôleur (construit avec EasyMock) pour le MVC.</p>
 * <p>Utilisation de Jemmy 2 pour la manipulation de l'IHM.</p>
 *
 * @version 4.0
 * @author Matthias Brun
 *
 * @see monix.vue.VueCaisse
 * @see org.easymock.EasyMock
 * @see org.netbeans.jemmy.JemmyProperties
 *
 */
public class VueCaisseTest
{
	/**
	 * Vue Caisse nécessaire aux tests.
	 */
	private VueCaisse vueCaisse;
	
	/**
	 * Mock d'un contrôleur nécessaire aux tests.
	 */
	private ControleurVente controleurMock;
	
	/**
	 * Mock d'un stock nécessaire aux tests.
	 */
	private Stock stockMock;

	/**
	 * La fenêtre de la vue.
	 */
	private JFrameOperator fenetre;
	
	/**
	 * Les boutons de la vue.
	 */
	private JButtonOperator boutonAnnuler, boutonAjouter, boutonFinVente;

	/**
	 * Les champs texte de la vue.
	 */
	private JTextFieldOperator texteId, texteQuantite, texteLibelle, textePrix, texteInfo, texteTotal;


	/**
	 * Fixe les propriétés de Jemmy pour les tests.
	 * Crée un mock de contrôleur et un mock de stock.
	 * Crée et affiche la vue nécessaire aux tests.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 * 
	 * @see org.netbeans.jemmy.JemmyProperties
	 *
	 */
	@Before
	public void setUp() throws Exception 
	{
		// Fixe les timeouts de Jemmy (http://wiki.netbeans.org/Jemmy_Operators_Environment#Timeouts),
		// ici : 3s pour l'affichage d'une frame.
		final Integer timeout = 3000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);
		
		// Création d'un mock de contrôleur.
		this.controleurMock = EasyMock.createMock(ControleurVente.class);
		Assert.assertNotNull(this.controleurMock);
		
		// Création d'un mock de stock.
		this.stockMock = EasyMock.createMock(Stock.class);
		Assert.assertNotNull(this.stockMock);
		
		// Création de la vue nécessaire aux tests.
		// La vue s'appuie sur le mock de contrôleur.
		this.vueCaisse = new VueCaisse(this.controleurMock);
		Assert.assertNotNull(this.vueCaisse);
		
		// Affichage de la vue et récupération de cette vue.
		this.vueCaisse.affiche();
		this.recuperationVue();
	}

	/**
	 * Fermeture de la vue caisse.
	 *
	 * <p>Code exécuté après les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@After
	public void tearDown() throws Exception 
	{
		// Pour avoir le temps d'observer les manipulations sur la vue (objectif pédagogique),
		// ici : 2 secondes.
		final Long timeout = Long.valueOf(2000); 
		Thread.sleep(timeout);
		
		if (this.vueCaisse != null) {
			this.vueCaisse.ferme();
		}
	}

	/**
	 * Récupération de la vue caisse.
	 * 
	 * <p>Cette méthode concerne la récupération de la fenêtre, avec titre adéquat, 
	 * et des widgets attendus pour cette vue.</p>
	 */
	private void recuperationVue()
	{
		// Index pour la récupération des widgets.
		Integer index = 0;
		
		// Récupération de la fenêtre de la vue de la caisse.
		this.fenetre = new JFrameOperator(Monix.MESSAGES.getString("CAISSE_FENETRE_TITRE"));
		Assert.assertNotNull("La fenêtre de la vue caisse n'est pas accessible.", this.fenetre);
	
		// Récupération du champ de saisie d'un identifiant produit.
		this.texteId = new JTextFieldOperator(this.fenetre, index++);
		Assert.assertNotNull("Le champ de saisie des indentifiants produits n'est pas accessible.", this.texteId);
		
		// Récupération du champ de saisie de la quantité de produit.
		this.texteQuantite = new JTextFieldOperator(this.fenetre, index++);
		Assert.assertNotNull("Le champ de saisie de la quantité de produits n'est pas accessible.", this.texteQuantite);
		
		// Récupération du champ de libellé d'un produit.
		this.texteLibelle = new JTextFieldOperator(this.fenetre, index++);
		Assert.assertNotNull("Le champ du libellé du produits n'est pas accessible.", this.texteLibelle);
		
		// Récupération du champ de prix d'un produit.
		this.textePrix = new JTextFieldOperator(this.fenetre, index++);
		Assert.assertNotNull("Le champ du prix d'un produit n'est pas accessible.", this.textePrix);
		
		// Récupération du champ d'information d'un achat.
		this.texteInfo = new JTextFieldOperator(this.fenetre, index++);
		Assert.assertNotNull("Le champ d'information d'un achat n'est pas accessible.", this.texteInfo);
		
		// Récupération du champ de prix total de la vente.
		this.texteTotal = new JTextFieldOperator(this.fenetre, index);
		Assert.assertNotNull("Le champ de prix total de la vente n'est pas eccessible.", this.texteTotal);
		
		// Ré-initialisation de l'index pour la récupération des boutons.
		index = 0;
		
		// Récupération du bouton d'ajout d'un produit à la vente.
		this.boutonAjouter = new JButtonOperator(this.fenetre, index++);
		Assert.assertNotNull("Le bouton d'ajout d'un produit de la vente n'est pas accessible.", this.boutonAjouter);
		
		// Récupération du bouton d'annulation d'un produit à la vente.
		this.boutonAnnuler = new JButtonOperator(this.fenetre, index++);
		Assert.assertNotNull("Le bouton d'annulation d'un produit de la vente n'est pas accessible.", this.boutonAnnuler);
		
		// Récupération du bouton de clôture de la vente.
		this.boutonFinVente = new JButtonOperator(this.fenetre, index);
		Assert.assertNotNull("Le bouton de clôture de la vente n'est pas accessible.", this.boutonFinVente);
	}
	
	/**
	 * Test l'initialisation des différents champs de la vue.
	 * 
	 * <p>Méthodes concernées : 
	 * <ul>
	 * <li> private void construireControles()
	 * <li> private void initialiseIdQuantiteLibellePrix()
	 * </ul>
	 * </p>
	 */
	@Test
	public void testInitialiseVue()
	{
		/*
		 * Données de test.
		 */
		final String idProduitAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_ID_PRODUIT");
		final String quantiteAttendue = Monix.MESSAGES.getString("CAISSE_TEXTE_QUANTITE");
		final String libelleAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_LIBELLE");
		final String prixAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX");
		final String infoAttendue = Monix.MESSAGES.getString("CAISSE_TEXTE_INFO");
		final String totalAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX_TOTAL");
		
		final String boutonAjouterAttendu = Monix.MESSAGES.getString("CAISSE_BOUTON_AJOUTER");
		final String boutonAnnulerAttendu = Monix.MESSAGES.getString("CAISSE_BOUTON_ANNULER");
		final String boutonFinVenteAttendu = Monix.MESSAGES.getString("CAISSE_BOUTON_FIN_VENTE");
		
		/*
		 * Configuration et chargement du mock du contrôleur.
		 */

		// Le mock du contrôleur sera sollicité lors de la perte du focus du champ
		// de saisie d'un identifiant lors de la fermeture de la fenêtre.
		// Le contrôleur retourne alors le mock du stock.
		EasyMock.expect(this.controleurMock.donneStock()).andReturn(this.stockMock);
		EasyMock.replay(this.controleurMock);
		
		/*
		 * Exécution du test.
		 */
		try {
			// Récupération des valeurs des champs de la vue.
			final String idProduitActuel = this.texteId.getText();
			final String quantiteActuelle = this.texteQuantite.getText();
			final String libelleActuel = this.texteLibelle.getText();
			final String prixActuel = this.textePrix.getText();
			final String infoActuelle = this.texteInfo.getText();
			final String totalActuel = this.texteTotal.getText();
			
			// Récupération les libellés des boutons de la vue.
			final String boutonAjouterActuel = this.boutonAjouter.getText();
			final String boutonAnnulerActuel = this.boutonAnnuler.getText();
			final String boutonFinVenteActuel = this.boutonFinVente.getText();

			// Assertions.
			Assert.assertEquals("Id produit par défaut invalide.", idProduitAttendu, idProduitActuel);
			Assert.assertEquals("Quantité de produit par défaut invalide.", quantiteAttendue, quantiteActuelle);
			Assert.assertEquals("Libellé par défaut invalide.", libelleAttendu, libelleActuel);
			Assert.assertEquals("Prix d'un produit par défaut invalide.", prixAttendu, prixActuel);
			Assert.assertEquals("Information d'un achat par défaut invalide.", infoAttendue, infoActuelle);
			Assert.assertEquals("Prix total de la vente par défaut invalide.", totalAttendu, totalActuel);
			Assert.assertEquals("Libellé du bouton ajouter invalide.", boutonAjouterAttendu, boutonAjouterActuel);
			Assert.assertEquals("Libellé du bouton annuler invalide.", boutonAnnulerAttendu, boutonAnnulerActuel);
			Assert.assertEquals(
					"Libellé du bouton de clôture de vente invalide.", 
					boutonFinVenteAttendu, boutonFinVenteActuel);
		}
		catch (Exception e) {
			Assert.fail("Manipulation de la vue caisse invalide." + e.getMessage());
		}
		
		/* Attention : la vérification des sollicitations faite au mock n'a pas de sens ici, 
		 * puisque celui-ci sera sollicité lors de la fermeture de la fenêtre.
		 */
	}
	
	
	/**
	 * Test de la réaction suite à la perte du focus du champ de saisie d'un identifiant de produit.
	 * 
	 * <p>Test après saisie d'un identifiant de produit <b>non valide</b>.</p>
	 * 
	 * <p>Méthode concernée : public void focusLost(FocusEvent ev)</p>
	 */
	@Test
	public void testFocusLost_produitNonValide()
	{
		/*
		 * Données de test.
		 */
		final String idProduit = "id de produit invalide (quelconque)";
		final String libelleAttendu = "Produit inconnu";
		final String prixAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX");

		/*
		 * Configuration et chargement des mocks du contrôleur et du stock.
		 */

		// Le mock du contrôleur est sollicité pour connaître le stock,
		// celui-ci retourne alors le mock du stock.
		EasyMock.expect(this.controleurMock.donneStock()).andReturn(this.stockMock);
		EasyMock.replay(this.controleurMock);
		
		// Le mock du stock est sollicité pour récupérer le produit correspondant à l'identifiant saisi,
		// celui-ci retourne null (car le produit n'est pas censé être dans le stock pour ce test).
		EasyMock.expect(this.stockMock.donneProduit(idProduit)).andReturn(null);
		EasyMock.replay(this.stockMock);
		
		/*
		 * Exécution du test.
		 */
		try {
			// Saisie de la référence du produit.
			this.texteId.clearText();
			this.texteId.typeText(idProduit);
			
			// Forcer la perte de focus du champ de saisie de l'identifiant d'un produit
			// en donnant le focus au champ de saisie de la quantité de produit.
			this.texteQuantite.clickMouse();
		}
		catch (Exception e) {
			Assert.fail("Manipulation de la vue caisse invalide." + e.getMessage());
		}
		
		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */	
		// Récupération de la valeur du champ de libellé du produit et du champ du prix du produit.
		final String libelleActuel = this.texteLibelle.getText();
		final String prixActuel = this.textePrix.getText();

		// Assertions.
		Assert.assertEquals("Libellé pour un produit non-existant invalide.", libelleAttendu, libelleActuel);
		Assert.assertEquals("Prix d'un produit non-existant invalide.", prixAttendu, prixActuel);
			
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du libellé.
			this.texteLibelle.waitText(libelleAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Libellé pour un produit non-existant invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.textePrix.waitText(prixAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix d'un produit non-existant invalide.");
		}
		*/
		
		/* 
		 * Vérification des sollicitations faites aux mocks.
		 */
		EasyMock.verify(this.controleurMock);
		EasyMock.verify(this.stockMock);
	}
	
	/**
	 * Test de la réaction suite à la perte du focus du champ de saisie d'un identifiant de produit.
	 * 
	 * <p>Test après saisie d'un identifiant de produit <b>valide</b>.</p>
	 * 
	 * <p>Méthode concernée : public void focusLost(FocusEvent ev)</p>
	 */
	@Test
	public void testFocusLost_produitValide()
	{
		/*
		 * Données de test.
		 */
		final String idProduit = "id de produit valide (quelconque)";
		final String libelleProduit = "produit de test";
		final Integer quantiteProduit = 20;
		final Double prixProduit = 10.0;
		
		final Produit produit = new Produit(idProduit, libelleProduit, prixProduit, quantiteProduit);
		
		final String libelleAttendu = libelleProduit;
		final String prixAttendu = String.format("%1$.2f €", prixProduit);
		
		/*
		 * Configuration et chargement des mocks du contrôleur et du stock.
		 */

		// Le mock du contrôleur est sollicité pour connaître le stock,
		// celui-ci retourne alors le mock du stock.
		EasyMock.expect(this.controleurMock.donneStock()).andReturn(this.stockMock);
		EasyMock.replay(this.controleurMock);
		
		// Le mock du stock est sollicité pour récupérer le produit correspondant à l'identifiant saisi,
		// celui-ci retourne null (car le produit n'est pas censé être dans le stock pour ce test).
		EasyMock.expect(this.stockMock.donneProduit(idProduit)).andReturn(produit);
		EasyMock.replay(this.stockMock);
		
		/*
		 * Exécution du test.
		 */
		try {
			// Saisie de la référence du produit.
			this.texteId.clearText();
			this.texteId.typeText(idProduit);
			
			// Forcer la perte de focus du champ de saisie de l'identifiant d'un produit
			// en donnant le focus au champ de saisie de la quantité de produit.
			this.texteQuantite.clickMouse();
		}
		catch (Exception e) {
			Assert.fail("Manipulation de la vue caisse invalide." + e.getMessage());
		}
		
		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */	
		
		// Récupération de la valeur du champ de libellé du produit et du champ du prix du produit.
		final String libelleActuel = this.texteLibelle.getText();
		final String prixActuel = this.textePrix.getText();

		// Assertions.
		Assert.assertEquals("Libellé pour un produit existant invalide.", libelleAttendu, libelleActuel);
		Assert.assertEquals("Prix d'un produit existant invalide.", prixAttendu, prixActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */	
		/*
		try {
			// Attente du message du libellé.
			this.texteLibelle.waitText(libelleAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Libellé pour un produit existant invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.textePrix.waitText(prixAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix d'un produit existant invalide.");
		}
		*/
		
		/*
		 * Vérification des sollicitations faites aux mocks. 
		 */
		EasyMock.verify(this.controleurMock);
		EasyMock.verify(this.stockMock);
	}

}
