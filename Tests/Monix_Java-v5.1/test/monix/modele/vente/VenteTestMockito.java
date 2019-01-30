package monix.modele.vente;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner; // Mockito >= 2.2.28 et JUnit >= 4.4
//import org.mockito.runners.MockitoJUnitRunner; // Mockito < 2.2.28

import monix.modele.stock.Produit;
import monix.modele.stock.Stock;

/**
 * Classe de tests unitaires JUnit 4 de la classe Vente (version simple, pour exemple).
 *
 * <p>Utilisation d'un mock (simulacre) de stock (construit avec Mockito).</p>
 *
 * @version 4.0
 * @author Matthias Brun
 *
 * @see monix.modele.vente.Vente
 * @see org.mockito.Mockito
 * @see org.mockito.runners.MockitoJUnitRunner
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class VenteTestMockito 
{
	/**
	 * Stock utilisé pour les tests.
	 */
	@Mock
	private Stock stockMock;

	/**
	 * Crée, avec EasyMock, un simulacre de stock nécessaire aux tests.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 * @see org.mockito.Mockito
	 *
	 */
	@Before
	public void setUp() throws Exception 
	{
		/* utilisation alternative de @Mock */
		// this.stockMock = Mockito.mock(Stock.class);
	}

	/**
	 * Non implanté.
	 *
	 * <p>Code exécuté après les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@After
	public void tearDown() throws Exception 
	{
		/* rien faire */
	}

	/**
	 * Teste l'ajout d'un produit dans une vente suite à son achat.
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteAchatProduit(Integer quantite)
	 * </p>
	 */
	@Test
	public void testAjouteAchatProduit_ajout()
	{
		final String message = "Vente achat produit : ";
	
		final String id = "111111"; // id quelconque
		final Produit produit = new Produit(id, "Libelle L", Double.valueOf(10), 1);

		final Integer quantite = 3;

		final Vente vente = new Vente(this.stockMock);

		Mockito.when(
			this.stockMock.donneProduit(id)
		).thenReturn(
			produit
		);

		try {
			vente.ajouteAchatProduit(id, quantite);

			this.assertTestModifAchatProduit(vente, produit, id, quantite, message);
		} 
		catch (AchatImpossibleException e) {
			Assert.fail(message + "catch AchatImpossibleException");
		}

		// Vérification des sollicitations faites au mock.
		Mockito.verify(this.stockMock).donneProduit(id);
		Mockito.verifyNoMoreInteractions(this.stockMock);
	}

	/**
	 * Teste l'incrément d'un produit dans une vente suite à son achat.
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteAchatProduit(Integer quantite)
	 * </p>
	 */
	@Test
	public void testAjouteAchatProduit_increment()
	{
		final String message = "Vente incrément produit : ";
	
		final String id = "111111"; // id quelconque
		final Produit produit = new Produit(id, "Libelle L", Double.valueOf(10), 1);

		final Integer quantite = 3, increment = 2, quantiteAttendue = 5;

		final Vente vente = new Vente(this.stockMock);

		Mockito.when(
			this.stockMock.donneProduit(id)
		).thenReturn(
			produit
		);

		try {
			vente.ajouteAchatProduit(id, quantite);
			vente.ajouteAchatProduit(id, increment);

			this.assertTestModifAchatProduit(vente, produit, id, quantiteAttendue, message);
		} 
		catch (AchatImpossibleException e) {
			Assert.fail(message + "catch AchatImpossibleException");
		}

		// Vérification des sollicitations faites au mock.
		Mockito.verify(this.stockMock, Mockito.times(2)).donneProduit(id);
		Mockito.verifyNoMoreInteractions(this.stockMock);
	}

	/**
	 * Teste la levée de l'exception AchatImpossibleException 
	 * lors de l'ajout d'un achat de produit absent du stock.
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteAchatProduit(Integer quantite)
	 * </p>
	 * 
	 * @throws AchatImpossibleException 
	 * @see monix.modele.vente.AchatImpossibleException
	 */
	@Test(expected = AchatImpossibleException.class)
	public void testAjouteAchatProduit_exception_AchatImpossibleException() throws AchatImpossibleException
	{
		final String id = "999999"; // id quelconque
		final Integer quantite = 3;

		final Vente vente = new Vente(this.stockMock);

		Mockito.when(
			this.stockMock.donneProduit(id)
		).thenReturn(
			null
		);

		vente.ajouteAchatProduit(id, quantite);
		
		// Vérification des sollicitations faites au mock.
		Mockito.verify(this.stockMock).donneProduit(id);
		Mockito.verifyNoMoreInteractions(this.stockMock);
	}

	/**
	 * Teste la clôture d'une vente.
	 * 
	 * <p>
	 * Méthode concernée : public void clotureVente()
	 * </p>
	 */
	@Test
	public void testClotureVente()
	{
		final String message = "Clôture vente : ";

		/* Quantité de produit i ajouté à la vente
		 * = i + coeficientQuantite (éviter quantité = 0 !).
		 */
		final Integer coeficientQuantite = 1;
		
		final Integer nbProduits = 10;
		final Produit[] produit = new Produit[nbProduits];

		final Vente vente = new Vente(this.stockMock);

		// Création des différents produits pour la vente.
		for (Integer i = 0; i < nbProduits; i++) {
			produit[i] = new Produit(i.toString(), "Libelle " + i, Double.valueOf(i), i);
		}

		// Séquence d'appels du mock pour les ajouts de produits.
		for (Integer i = 0; i < nbProduits; i++) {
			Mockito.when(
				this.stockMock.donneProduit(i.toString())
			).thenReturn(
				produit[i]
			);
		}
		// Séquence d'appels du mock pour la clôture de la vente.
		for (Integer i = 0; i < nbProduits; i++) {
			final Integer quantite = i + coeficientQuantite;
			Mockito.doNothing().when(this.stockMock).changeQuantiteProduit(produit[i], -quantite);
		}

		try {
			// Ajout des différents produits à la vente.
			for (Integer i = 0; i < nbProduits; i++) {
				final Integer quantite = i + coeficientQuantite;
				vente.ajouteAchatProduit(i.toString(), quantite);
				this.assertTestModifAchatProduit(vente, produit[i], i.toString(), quantite, message);
			}

			// Clôture de la vente.
			vente.clotureVente();

			Assert.assertEquals(message + "achats non vide", vente.donneAchats().size(), 0);
			Assert.assertEquals(message + "prix non nul", vente.donnePrix(), Double.valueOf(0));
		} 
		catch (AchatImpossibleException e) {
			Assert.fail(message + "catch AchatImpossibleException");
		}

		// Vérification des sollicitations faites au mock.
		// (a) Séquence d'appels du mock pour les ajouts de produits.
		for (Integer i = 0; i < nbProduits; i++) {
			Mockito.verify(this.stockMock).donneProduit(i.toString());
		}
		// (b) Séquence d'appels du mock pour la clôture de la vente.
		for (Integer i = 0; i < nbProduits; i++) {
			final Integer quantite = i + coeficientQuantite;
			Mockito.verify(this.stockMock).changeQuantiteProduit(produit[i], -quantite);
		}
		Mockito.verifyNoMoreInteractions(this.stockMock);
	}


	/**
	 * Assertions pour une modification des achats d'une vente.
	 * 
	 * @param vente la vente concernée.
	 * @param produit le produit modifié (ajouté ou annulé) dans la liste des achats.
	 * @param id l'identifiant du produit modifié.
	 * @param quantite la quantité de produit modifié.
	 * @param message le début du message à afficher si une assertion n'est pas vérifiée.
	 * 
	 */
	private void assertTestModifAchatProduit(Vente vente, Produit produit, String id, Integer quantite, String message)
	{
		Assert.assertNotNull(
			message + "achats null", 
			vente.donneAchats()
		);

		Assert.assertNotNull(
			message + "produit absent", 
			vente.donneAchats().get(id)
		);

		Assert.assertEquals(
			message + "produit non conforme",
			produit, 
			vente.donneAchats().get(id).donneProduit()
		);

		Assert.assertEquals(
			message + "quantité non conforme", 
			quantite, 
			vente.donneAchats().get(id).donneQuantite()
		);
	}

}
