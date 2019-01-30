package monix.modele.vente;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite de tests unitaires JUnit 4 pour le package monix.modele.vente.
 *
 * @version 4.0
 * @author Matthias Brun
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
		{ 
			AchatTest.class,
			AchatTestParameterized.class,
			VenteTest.class,
			VenteTestEasyMockSimple.class,
			VenteTestEasyMock.class,
			VenteTestMockitoSimple.class,
			VenteTestMockito.class
		}
	)
public class VenteTestSuite
{ 
	/* empty class */
} 

