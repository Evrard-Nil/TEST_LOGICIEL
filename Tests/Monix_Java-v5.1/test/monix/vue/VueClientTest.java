package monix.vue;

import java.util.LinkedHashMap;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JScrollPaneOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;

import monix.Monix;
import monix.controleur.ControleurVente;
import monix.modele.stock.Produit;
import monix.modele.stock.StockBouchon;
import monix.modele.vente.Achat;
import monix.modele.vente.AchatImpossibleException;
import monix.modele.vente.Vente;
import monix.modele.vente.VenteChangeEvenement;

/**
 * Classe de tests unitaires JUnit 4 de la classe VueClient.
 *
 * <p>Utilisation d'un bouchon de stock de type StockBouchon.</p>
 * <p>Utilisation d'un mock (simulacre) de contrôleur (construit avec EasyMock) pour le MVC.</p>
 * <p>Utilisation de Jemmy 2 pour la manipulation de l'IHM.</p>
 *
 * @version 4.0
 * @author Matthias Brun
 *
 * @see monix.vue.VueClient
 * @see monix.modele.stock.StockBouchon
 * @see org.easymock.EasyMock
 * @see org.netbeans.jemmy.JemmyProperties
 *
 */
public class VueClientTest
{
	/**
	 * Caractère de fin de ligne utilisé dans le ticket de la vue client. 
	 */
	private static final String FINDELIGNE = System.getProperty("line.separator");
	
	/**
	 * Vue Client nécessaire aux tests.
	 */
	private VueClient vueClient;
	
	/**
	 * Mock d'un contrôleur nécessaire aux tests.
	 */
	private ControleurVente controleurMock;
	
	/**
	 * Bouchon de stock nécessaire aux tests.
	 */
	private StockBouchon stockBouchon;

	/**
	 * La fenêtre de la vue.
	 */
	private JFrameOperator fenetre;
	
	/**
	 * Les champs texte de la vue.
	 */
	private JTextFieldOperator texteTotal;

	/**
	 * Les panneaux textes de la vue (ticket).
	 */
	private JTextPaneOperator texteTicket;

	/**
	 * Le panneau de défilement du ticket.
	 */
	private JScrollPaneOperator defilementTicket;
	
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
		
		// Création d'un bouchon de stock.
		this.stockBouchon = new StockBouchon();
		Assert.assertNotNull(this.stockBouchon);
		
		// Création de la vue nécessaire aux tests.
		// La vue s'appuie sur le mock de contrôleur.
		this.vueClient = new VueClient(this.controleurMock);
		Assert.assertNotNull(this.vueClient);
		
		// Affichage de la vue et récupération de cette vue.
		this.vueClient.affiche();
		this.recuperationVue();
	}

	/**
	 * Fermeture de la vue client.
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
		
		if (this.vueClient != null) {
			this.vueClient.ferme();
		}
	}
	
	/**
	 * Récupération de la vue client.
	 * 
	 * <p>Cette méthode concerne la récupération de la fenêtre, avec titre adéquat, 
	 * et des widgets attendus pour cette vue.</p>
	 */
	private void recuperationVue()
	{
		// Récupération de la fenêtre de la vue client.
		this.fenetre = new JFrameOperator(Monix.MESSAGES.getString("CLIENT_FENETRE_TITRE"));
		Assert.assertNotNull("La fenêtre de la vue client n'est pas accessible.", this.fenetre);
		
		// Récupération du champ d'information du prix total de la vente.
		this.texteTotal = new JTextFieldOperator(this.fenetre, 0);
		Assert.assertNotNull("Le champ d'information du prix total de la vente n'est pas accessible.", this.texteTotal);

		// Récupération du panneau texte de la vue (ticket).
		this.texteTicket = new JTextPaneOperator(this.fenetre, 0);
		Assert.assertNotNull("Le panneau texte de la vue (ticket) n'est pas accessible.", this.texteTicket);
		
		// Récupération du panneau de défilement du ticket de la vue.
		this.defilementTicket = new JScrollPaneOperator(this.fenetre, 0);
		Assert.assertNotNull("Le panneau de défilement du ticket n'est pas accessible.", this.defilementTicket);	
	}
	
	/**
	 * Test l'initialisation des différents champs de la vue.
	 * 
	 * <p>Méthodes concernées : 
	 * <ul>
	 * <li> private void construirePanneaux()
	 * <li> private void construireControles()
	 * <li> private void afficheDocumentTicketVierge()
	 * </ul>
	 * </p>
	 */
	@Test
	public void testInitialiseVue()
	{
		/*
		 * Données de test.
		 */
		final String prixTotalAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_PRIX_TOTAL");
		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET");
		
		/*
		 * Exécution du test.
		 */
		try {
			// Récupération des valeurs des champs de la vue.
			final String prixTotalActuel = this.texteTotal.getText();
			final String ticketActuel = this.texteTicket.getText();
			
			// Assertions.
			Assert.assertEquals("Prix total par défaut invalide.", prixTotalAttendu, prixTotalActuel);
			Assert.assertEquals("Ticket par défaut invalide.", ticketAttendu, ticketActuel);
						
		}
		catch (Exception e) {
			Assert.fail("Manipulation de la vue client invalide." + e.getMessage());
		}
	}
	
	
	/**
	 * Test l'affichage d'une vente dans la vue client suite à un changement dans la vente.
	 * 
	 * <p>
	 * Méthode basée sur une Vente et sur le StockBouchon,
	 * avec utilisation de la méthode ajouteAchatProduit de la classe Vente
	 * pour effectuer les achats (injection des DT). 
	 * </p>
	 * 
	 * Méthode concernée : public void venteChange(VenteChangeEvenement ev)
	 * 
	 */
	@Test
	public void testVenteChange_1()
	{
		// Vente utile pour le test.
		final Vente venteTest = new Vente(this.stockBouchon);
		
		/*
		 * Données de test.
		 */
		final String idProduitTest = "11A";
		final Integer quantiteProduitTest = 2;
		final Double prixProduitTest = 10.0;
		
		final String prixTotalAttendu = 
				String.format("%1$.2f €", 
						this.stockBouchon.donneProduit(idProduitTest).donnePrix() * quantiteProduitTest);
		
		// Construction du ticketAttendu.
		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET") + FINDELIGNE
				+ String.format("produit un A       x 2      x    %1$.2f €", prixProduitTest);
		
		/*
		 * Injection des données de test.
		 */
		try {
			// Acheter un produit.
			venteTest.ajouteAchatProduit(idProduitTest, quantiteProduitTest);
		} 
		catch (AchatImpossibleException e) {
			Assert.fail("Ajout d'un achat de produit dans une vente impossible.");
		}

		// Création de l'évènement de notification de l'achat du produit.
		final VenteChangeEvenement ev = new VenteChangeEvenement(venteTest.donnePrix(), venteTest.donneAchats());
		
		/*
		 * Exécution du test.
		 */
		
		// Appel de la méthode à tester.
		this.vueClient.venteChange(ev);
		
		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */
		
		// Récupération des valeurs des champs de la vue.
		final String prixTotalActuel = this.texteTotal.getText();
		final String ticketActuel = this.texteTicket.getText();

		// Assertions.
		Assert.assertEquals("Prix total invalide.", prixTotalAttendu, prixTotalActuel);
		Assert.assertEquals("Ticket invalide.", ticketAttendu, ticketActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du prix total.
			this.texteTotal.waitText(prixTotalAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix total invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.texteTicket.waitText(ticketAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Ticket invalide.");
		}
		*/
	}
	
	/**
	 * Test l'affichage d'une vente dans la vue client suite à un changement dans la vente.
	 * 
	 * <p>
	 * Méthode identique à la précédente (testVenteChange_1)
	 * avec construction explicite (et autonome) de l'évènement VenteChangeEvenement
	 * passé en paramètre de la méthode à tester (injection des DT).</p>
	 * 
	 * Méthode concernée : public void venteChange(VenteChangeEvenement ev)
	 * 
	 */
	@Test
	@Ignore
	public void testVenteChange_2()
	{
		/*
		 * Données de test en entrée.
		 */
		final String libelleProduit = "Libellé P1";
		final Double prixProduit = 10.00;
		final Integer quantiteProduitAchete = 2;
		final Double prixTotal = prixProduit * quantiteProduitAchete;
		
		/*
		 * Résultats attendus.
		 */
		// Prix total attendu.
		final String prixTotalAttendu = String.format("%1$.2f €", prixTotal);
		
		// Ticket attendu.
		final String libelleProduitComplete = "         ";	// cf. configuration CLIENT_FENETRE_TICKET_LIBELLE_LARGEUR.
		final String quantiteProduitComplete = "      ";	// cf. configuration CLIENT_FENETRE_TICKET_QUANTITE_LARGEUR.
		final String prixProduitComplete = "   ";        	// cf. configuration CLIENT_FENETRE_TICKET_PRIX_LARGEUR.

		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET") + FINDELIGNE
				+ libelleProduit + libelleProduitComplete
				+ "x " + quantiteProduitAchete + quantiteProduitComplete
				+ "x " + prixProduitComplete + String.format("%1$.2f €", prixProduit);
		
		/*
		 * Injection des données de test.
		 */
		
		// Construction du produit et de l'achat du produit.
		final Produit produit = new Produit("P1", libelleProduit, prixProduit, 1);
		final Achat achat = new Achat(produit, quantiteProduitAchete);

		//  Construction de l'ensemble des achats nécessaires aux tests.
		final LinkedHashMap<String, Achat> achats = new LinkedHashMap<String, Achat>();
		achats.put(produit.donneId(), achat);
				
		// Création de l'évènement de notification de l'achat du produit.
		final VenteChangeEvenement ev = new VenteChangeEvenement(prixTotal, achats);
				
		/*
		 * Exécution du test.
		 */
	
		// Appel de la méthode à tester.
		this.vueClient.venteChange(ev);

		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */
		
		// Récupération des valeurs des champs de la vue.
		final String prixTotalActuel = this.texteTotal.getText();
		final String ticketActuel = this.texteTicket.getText();
						
		// Assertions.
		Assert.assertEquals("Prix total invalide.", prixTotalAttendu, prixTotalActuel);
		Assert.assertEquals("Ticket invalide.", ticketAttendu, ticketActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du prix total.
			this.texteTotal.waitText(prixTotalAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix total invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.texteTicket.waitText(ticketAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Ticket invalide.");
		}
		*/
	}
	

	
	/**
	 * Test l'affichage d'une vente dans la vue client suite à un changement dans la vente.
	 * 
	 * <p>
	 * Méthode identique à la précédente (testVenteChange_2)
	 * avec mock de l'évènement VenteChangeEvenement
	 * passé en paramètre de la méthode à tester (injection des DT).</p>
	 * 
	 * Méthode concernée : public void venteChange(VenteChangeEvenement ev)
	 * 
	 */
	@Test
	@Ignore
	public void testVenteChange_3()
	{
		/*
		 * Données de test en entrée.
		 */
		final String libelleProduit = "Libellé P1";
		final Double prixProduit = 10.00;
		final Integer quantiteProduitAchete = 2;
		final Double prixTotal = prixProduit * quantiteProduitAchete;
		
		/*
		 * Résultats attendus.
		 */
		// Prix total attendu.
		final String prixTotalAttendu = String.format("%1$.2f €", prixTotal);
		
		// Ticket attendu.
		final String libelleProduitComplete = "         ";	// cf. configuration CLIENT_FENETRE_TICKET_LIBELLE_LARGEUR.
		final String quantiteProduitComplete = "      ";	// cf. configuration CLIENT_FENETRE_TICKET_QUANTITE_LARGEUR.
		final String prixProduitComplete = "   ";        	// cf. configuration CLIENT_FENETRE_TICKET_PRIX_LARGEUR.

		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET") + FINDELIGNE
				+ libelleProduit + libelleProduitComplete
				+ "x " + quantiteProduitAchete + quantiteProduitComplete
				+ "x " + prixProduitComplete + String.format("%1$.2f €", prixProduit);
	
		/*
		 * Injection des données de test.
		 */
		
		// Construction du produit et de l'achat du produit.
		final Produit produit = new Produit("P1", libelleProduit, prixProduit, 1);
		final Achat achat = new Achat(produit, quantiteProduitAchete);

		//  Construction de l'ensemble des achats nécessaires aux tests.
		final LinkedHashMap<String, Achat> achats = new LinkedHashMap<String, Achat>();
		achats.put(produit.donneId(), achat);
				
		/* Création en configuration du mock de VenteChangeEvenement. */
		final VenteChangeEvenement evMock = EasyMock.createMock(VenteChangeEvenement.class);
		
		EasyMock.expect(evMock.donneAchats()).andReturn(achats);
		EasyMock.expect(evMock.donnePrix()).andReturn(prixTotal);
		EasyMock.replay(evMock);
		
		/*
		 * Exécution du test.
		 */

		// Appel de la méthode à tester.
		this.vueClient.venteChange(evMock);

		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */
		
		// Récupération des valeurs des champs de la vue.
		final String prixTotalActuel = this.texteTotal.getText();
		final String ticketActuel = this.texteTicket.getText();
						
		// Assertions.
		Assert.assertEquals("Prix total invalide.", prixTotalAttendu, prixTotalActuel);
		Assert.assertEquals("Ticket invalide.", ticketAttendu, ticketActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du prix total.
			this.texteTotal.waitText(prixTotalAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix total invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.texteTicket.waitText(ticketAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Ticket invalide.");
		}
		*/
		
		/* 
		 * Vérification des sollicitations faites au mock.
		 */
		EasyMock.verify(evMock);
	}
	
	/**
	 * Test l'affichage d'une vente dans la vue client suite à un changement dans la vente.
	 * 
	 * <p>
	 * Méthode identique à la précédente (testVenteChange_3)
	 * avec achat de plusieurs produits.</p>
	 * 
	 * Méthode concernée : public void venteChange(VenteChangeEvenement ev)
	 * 
	 */
	@Test
	public void testVenteChange_multi()
	{
		/*
		 * Données de test en entrée.
		 */
		final int nbDT = 4;
		
		final String[] libelleProduit = {"Libellé P1", "Libellé P2", "Libellé P3", "Libellé P4"};
		final Double[] prixProduit = {10.00, 20.50, 35.10, 47.65};
		final Integer[] quantiteProduitAchete = {2, 3, 7, 9};
		final Double prixTotal = 756.05;
		
		/*
		 * Résultats attendus.
		 */
		// Prix total attendu.
		final String prixTotalAttendu = String.format("%1$.2f €", prixTotal);
		
		// Ticket attendu.
		final String libelleProduitComplete = "         ";	// cf. configuration CLIENT_FENETRE_TICKET_LIBELLE_LARGEUR.
		final String quantiteProduitComplete = "      ";	// cf. configuration CLIENT_FENETRE_TICKET_QUANTITE_LARGEUR.
		final String prixProduitComplete = "   ";        	// cf. configuration CLIENT_FENETRE_TICKET_PRIX_LARGEUR.

		String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET");
		
		for (int i = 0; i < nbDT; i++) {
			ticketAttendu += FINDELIGNE + libelleProduit[i] + libelleProduitComplete
				+ "x " + quantiteProduitAchete[i] + quantiteProduitComplete
				+ "x " + prixProduitComplete + String.format("%1$.2f €", prixProduit[i]);
		}
		
		/*
		 * Injection des données de test.
		 */
		
		// Construction des produit et de l'achat du produit et de l'ensemble des achats nécessaires aux tests.
		final LinkedHashMap<String, Achat> achats = new LinkedHashMap<String, Achat>();
		for (int i = 0; i < nbDT; i++) {
			
			final String idProduit = "P" + i;
			final Produit produit = new Produit(idProduit, libelleProduit[i], prixProduit[i], 1);
			final Achat achat = new Achat(produit, quantiteProduitAchete[i]);
			achats.put(idProduit, achat);
		}
				
		/* Création en configuration du mock de VenteChangeEvenement. */
		final VenteChangeEvenement evMock = EasyMock.createMock(VenteChangeEvenement.class);
		
		EasyMock.expect(evMock.donneAchats()).andReturn(achats);
		EasyMock.expect(evMock.donnePrix()).andReturn(prixTotal);
		EasyMock.replay(evMock);
		
		/*
		 * Exécution du test.
		 */

		// Appel de la méthode à tester.
		this.vueClient.venteChange(evMock);

		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */
		
		// Récupération des valeurs des champs de la vue.
		final String prixTotalActuel = this.texteTotal.getText();
		final String ticketActuel = this.texteTicket.getText();
						
		// Assertions.
		Assert.assertEquals("Prix total invalide.", prixTotalAttendu, prixTotalActuel);
		Assert.assertEquals("Ticket invalide.", ticketAttendu, ticketActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du prix total.
			this.texteTotal.waitText(prixTotalAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix total invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.texteTicket.waitText(ticketAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Ticket invalide.");
		}
		*/
		
		/* 
		 * Vérification des sollicitations faites au mock.
		 */
		EasyMock.verify(evMock);
	}
	
}
