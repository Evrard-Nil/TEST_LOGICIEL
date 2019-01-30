package monix.modele.vente;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import monix.modele.stock.Stock;
import monix.modele.stock.StockBouchon;

/**
 * Classe de tests unitaires JUnit 4 de la classe Vente (version simple, pour exemple).
 *
 * <p>Utilisation d'un bouchon de stock de type StockBouchon.</p>
 *
 * @version 4.0
 * @author Matthias Brun
 *
 * @see monix.modele.vente.Vente
 * @see monix.modele.stock.StockBouchon
 *
 */
public class VenteTest
{
	/**
	 * Stock utilisé pour les tests.
	 */
	private Stock stock;

	/**
	 * Crée un stock bouchon nécessaire aux tests à partir de la classe StockBouchon.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 * @see monix.modele.stock.StockBouchon
	 *
	 */
	@Before
	public void setUp() throws Exception 
	{
		this.stock = new StockBouchon();
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
	
		final String id = "11A"; // id valide, voir : modele.test.StockBouchon
		final Integer quantite = 3;

		final Vente vente = new Vente(this.stock);

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
				this.stock.donneProduit(id), 
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
	}

	/**
	 * Teste la levée de l'exception AchatImpossibleException lors de l'ajout d'un achat de produit absent du stock.
	 * Version simple (sans annotation dédiée à le gestion de l'exception).
	 * 
	 * <p>
	 * Méthode concernée : public void testAjouteAchatProduit(Integer quantite)
	 * </p>
	 *
	 * @see monix.modele.vente.AchatImpossibleException
	 */
	@Test
	public void testAjouteAchatProduit_exception_AchatImpossibleException_simple()
	{
		final String id = "999999"; // id invalide, voir : modele.test.StockBouchon
		final Integer quantite = 3;

		final Vente vente = new Vente(this.stock);

		try {	
			vente.ajouteAchatProduit(id, quantite);
			Assert.fail("Vente achat exception AchatImpossibleException");
		} 
		catch (AchatImpossibleException e) {
			// passage attendu dans ce bloc.
		}
	}

	/**
	 * Teste la levée de l'exception AchatImpossibleException lors de l'ajout d'un achat de produit absent du stock.
	 * Version avec annotation.
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
		final String id = "999999"; // id invalide, voir : modele.test.StockBouchon
		final Integer quantite = 3;

		final Vente vente = new Vente(this.stock);

		vente.ajouteAchatProduit(id, quantite);
	}

}
