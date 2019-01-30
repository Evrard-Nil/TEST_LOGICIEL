package monix.modele.vente;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import monix.modele.stock.Produit;

/**
 * Classe de tests unitaires JUnit 4 de la classe Achat.
 *
 * @version 4.0
 * @author Matthias Brun
 *
 * @see monix.modele.vente.Achat
 *
 */

public class AchatTest
{
	/**
	 * Produit utile pour les tests.
	 */
	private Produit produit;

	/**
	 * Crée le produit nécessaire aux tests.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@Before
	public void setUp() throws Exception
	{
		this.produit  = new Produit("A", "Libelle A", Double.valueOf(2), 1);
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
		/* ne rien faire */
	}

	/**
	 * Teste l'ajout d'une quantité de produit.
	 * 
	 * <p>
	 * Méthode concernée : public void incrementeQuantite(Integer quantite)
	 * </p>
	 *
	 */
	@Test
	public void testIncrementeQuantite()
	{
		// Données de test.
		final Integer origine = 1, ajout = 2, resultatAttendu = 3;

		// Instance d'un object de la classe testée.
		final Achat achat = new Achat(this.produit, origine);
		
		// Appel de la méthode testée.
		achat.incrementeQuantite(ajout);

		// Validation des effets de bord.
		Assert.assertEquals("Incrémente quantité produit", (int) resultatAttendu, (int) achat.donneQuantite());
	}

	/**
	 * Teste le retrait d'une quantité de produit.
	 * 
	 * <p>
	 * Méthode concernée : public void decrementeQuantite(Integer quantite)
	 * </p>
	 *
	 */
	@Test
	public void testDecrementeQuantite()
	{
		// Données de test.
		final Integer origine = 3, retrait = 2, resultatAttendu = 1;

		// Instance d'un object de la classe testée.
		final Achat achat = new Achat(this.produit, origine);
		
		// Appel de la méthode testée.
		achat.decrementeQuantite(retrait);

		// Validation des effets de bord.
		Assert.assertEquals("Décrémente quantité produit", (int) resultatAttendu, (int) achat.donneQuantite());
	}

}
