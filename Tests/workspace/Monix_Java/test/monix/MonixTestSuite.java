package monix;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import monix.modele.ModeleTestSuite;
import monix.vue.VueTestSuite;

/**
 * Suite de tests unitaires JUnit 4 pour le programme Monix.
 *
 * @version 5.2
 * @author Matthias Brun
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	ModeleTestSuite.class,
	VueTestSuite.class,
	MonixTestSimple.class,
	MonixTestMulti.class
	})
public class MonixTestSuite
{ 
	/* empty class */
} 


