package monix.modele.stock;

import java.util.EventListener;

/**
 * Interface de gestion des écouteurs d'un stock (architecture MVC).
 *
 * @version 3.0
 * @author Matthias Brun
 *
 * @see java.util.EventListener
 * @see monix.modele.stock.Stock
 *
 */
public interface StockEcouteur extends EventListener 
{

	/**
	 * Notifie le changement du stock.
	 *
	 */
	public void stockChange();	

}
