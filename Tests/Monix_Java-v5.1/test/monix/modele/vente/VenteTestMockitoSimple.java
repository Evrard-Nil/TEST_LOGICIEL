package monix.modele.vente;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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
public class VenteTestMockitoSimple
{
	/**
	 * Stock utilisé pour les tests.
	 */
	private Stock stockMock;

	/**
	 * Crée, avec Mockito, un simulacre de stock nécessaire aux tests.
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
		this.stockMock = Mockito.mock(Stock.class);
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
	 * Méthode concernée : public void testAjouteAchatProduit(Integer quantite)
	 * </p>
	 */
	@Test
	public void testAjouteAchatProduit()
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
	
			Assert.assertNotNull(
				message + "achats null", 
				vente.donneAchats()
			);

			Assert.assertNotNull(
				message + "produit non acheté", 
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
		catch (AchatImpossibleException e) {
			Assert.fail(message + "catch AchatImpossibleException");
		}
		
		// Vérification des sollicitations faites au mock.
		Mockito.verify(this.stockMock).donneProduit(id);
		Mockito.verifyNoMoreInteractions(this.stockMock);
	}

	/**
	 * Teste la levée de l'exception AchatImpossibleException 
	 * lors de l'ajout d'un achat de produit absent du stock.
	 * 
	 * <p>
	 * Méthode concernée : public void testAjouteAchatProduit(Integer quantite)
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

}

