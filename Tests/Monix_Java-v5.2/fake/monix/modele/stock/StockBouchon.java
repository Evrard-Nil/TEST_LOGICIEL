package monix.modele.stock;

/**
 * Classe bouchon pour la gestion d'un stock.
 * 
 * Fournit un constructeur qui initialise un substitut de stock,
 * renseigné en dur par un ensemble de produits.
 * 
 * Fournit également une méthode permettant de changer la quantité
 * de ces produit dans le stock ainsi bouchonné.
 *
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.modele.stock.Stock
 * @see monix.modele.stock.StockAbstrait
 *
 */
public class StockBouchon extends StockAbstrait
{
	/**
	 * Constructeur du stock (création des produits et ajout des produits dans le stock).
	 */
	public StockBouchon()
	{
		super();
	
		ajouteProduit(new Produit("11A", "produit un A    ", Double.valueOf(10), 1));
		ajouteProduit(new Produit("22A", "produit deux A  ", Double.valueOf(20), 2));
		ajouteProduit(new Produit("33A", "produit trois A ", Double.valueOf(30), 3));
		ajouteProduit(new Produit("44A", "produit quatre A", Double.valueOf(40), 4));
		ajouteProduit(new Produit("55A", "produit cinq A  ", Double.valueOf(50), 5));

		ajouteProduit(new Produit("11AA", "produit un A super long    ", Double.valueOf(10), 10));
		ajouteProduit(new Produit("22AA", "produit deux A super long  ", Double.valueOf(20), 20));
		ajouteProduit(new Produit("33AA", "produit trois A super long ", Double.valueOf(30), 30));
		ajouteProduit(new Produit("44AA", "produit quatre A super long", Double.valueOf(40), 40));
		ajouteProduit(new Produit("55AA", "produit cinq A super long  ", Double.valueOf(50), 50));

		ajouteProduit(new Produit("11Z", "produit un Z    ", Double.valueOf(10.50), 1));
		ajouteProduit(new Produit("22Z", "produit deux Z  ", Double.valueOf(20.50), 2));
		ajouteProduit(new Produit("33Z", "produit trois Z ", Double.valueOf(30.50), 3));
		ajouteProduit(new Produit("44Z", "produit quatre Z", Double.valueOf(40.50), 4));
		ajouteProduit(new Produit("55Z", "produit cinq Z  ", Double.valueOf(50.50), 5));

		ajouteProduit(new Produit("111Z", "produit un 10Z    ", Double.valueOf(10000.50), 110));
		ajouteProduit(new Produit("222Z", "produit deux 10Z  ", Double.valueOf(20000.50), 220));
		ajouteProduit(new Produit("333Z", "produit trois 10Z ", Double.valueOf(30000.50), 330));
		ajouteProduit(new Produit("444Z", "produit quatre 10Z", Double.valueOf(40000.50), 440));
		ajouteProduit(new Produit("555Z", "produit cinq 10Z  ", Double.valueOf(50000.50), 550));

		ajouteProduit(new Produit("111ZZ", "produit un 10ZZ super long    ", Double.valueOf(10000.50), 10000));
		ajouteProduit(new Produit("222ZZ", "produit deux 10ZZ super long  ", Double.valueOf(20000.50), 20000));
		ajouteProduit(new Produit("333ZZ", "produit trois 10ZZ super long ", Double.valueOf(30000.50), 30000));
		ajouteProduit(new Produit("444ZZ", "produit quatre 10ZZ super long", Double.valueOf(40000.50), 40000));
		ajouteProduit(new Produit("555ZZ", "produit cinq 10ZZ super long  ", Double.valueOf(50000.50), 50000));

	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see monix.modele.stock.Stock
	 */
	@Override
	public void changeQuantiteProduit(Produit produit, Integer quantite)
	{
		donneProduit(produit.donneId()).incrementeQuantite(quantite);
		signalStockChange();
	}

}
