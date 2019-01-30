package monix.modele.vente;

import java.util.Arrays;
import java.util.Collection;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import monix.modele.stock.Produit;

/**
 * Classe de tests unitaires JUnit 4 de la classe Achat.
 * Utilisation de paramètres pour jouer plusieurs données de test.
 * 
 * @version 4.0
 * @author Matthias Brun
 *
 * @see monix.modele.vente.Achat
 *
 */
@RunWith(Parameterized.class)
public class AchatTestParameterized
{
	/**
	 * Produit utile pour les tests.
	 */
	private Produit produit;

	/**
	 * Paramètres pour le nombre de produits à l'origine dans le stock et modifiés dans le stock.
	 */
	private Integer origine, modif;
	
	/**
	 * Paramètres pour les résultats attendus.
	 */
	private Integer incrementAttendu, decrementAttendu;
	
	/**
	 * Constructeur de chaque donnée de test pour la classe.
	 * 
	 * @param origine le nombre de produits considérés présents dans le stock à l'origine. 
	 * @param modif le nombre de produits considérés modifiés (par ajout ou retrait) dans le stock.
	 * @param incrementAttendu le nombre de produits considérés présents dans le stock suite à un ajout.
	 * @param decrementAttendu le nombre de produits considérés présents dans le stock suite à un retrait.
	 */
	public AchatTestParameterized(Integer origine, Integer modif, Integer incrementAttendu, Integer decrementAttendu)
	{
		this.origine = origine;
		this.modif = modif;
		this.incrementAttendu = incrementAttendu;
		this.decrementAttendu = decrementAttendu;
	}
	
	/**
	 * Création du jeu de test. 
	 * 
	 * @return l'ensemble des données de test. 
	 */
	@Parameters(name = "dt[{index}] : {0}, {1}, {2}, {3}")
	public static Collection<Object[]> jt()
	{
		final Object[][] data = new Object[][] {
			{1, 0, 1, 1},
			{1, 1, 2, 0},
			{2, 1, 3, 1},
			{1, 2, 3, -1}
		};
		return Arrays.asList(data);
	}
	
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
	 */
	@Test
	public void testIncrementeQuantite()
	{
		final Achat achat = new Achat(this.produit, this.origine);
		
		achat.incrementeQuantite(this.modif);

		Assert.assertEquals("Incrémente quantité produit", (int) (this.incrementAttendu), (int) achat.donneQuantite());
	}

	/**
	 * Teste le retrait d'une quantité de produit.
	 * 
	 * <p>
	 * Méthode concernée : public void decrementeQuantite(Integer quantite)
	 * </p>
	 */
	@Test
	public void testDecrementeQuantite()
	{
		final Achat achat = new Achat(this.produit, this.origine);
		
		achat.decrementeQuantite(this.modif);

		Assert.assertEquals("Décrémente quantité produit", (int) (this.decrementAttendu), (int) achat.donneQuantite());
	}

}
