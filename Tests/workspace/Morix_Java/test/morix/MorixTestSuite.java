package morix;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import morix.bdd.BDDTestSuite;
import morix.serveur.ServeurTestSuite;
import morix.stock.StockTestSuite;

/**
 * Suite de tests unitaires JUnit 4 pour le programme Morix.
 *
 * @version 4.0
 * @author Matthias Brun
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
		{ 
			StockTestSuite.class,
			BDDTestSuite.class,
			ServeurTestSuite.class
		}
	)
public class MorixTestSuite
{ 
	/* empty class */
} 

