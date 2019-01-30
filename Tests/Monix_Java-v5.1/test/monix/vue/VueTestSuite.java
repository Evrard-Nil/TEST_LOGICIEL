package monix.vue;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Suite de tests unitaires JUnit 4 pour le package monix.vue.
 *
 * @version 4.0
 * @author Matthias Brun
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
		{ 
			VueCaisseTest.class,
			VueClientTest.class
		}
	)
public class VueTestSuite
{ 
	/* empty class */
} 

