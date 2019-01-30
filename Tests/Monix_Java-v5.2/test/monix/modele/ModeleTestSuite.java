package monix.modele;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import monix.modele.vente.VenteTestSuite;

/**
 * Suite de tests unitaires JUnit 4 pour le package monix.modele.
 *
 * @version 4.0
 * @author Matthias Brun
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
		{ 
			VenteTestSuite.class
		}
	)
public class ModeleTestSuite
{ 
	/* empty class */
} 
