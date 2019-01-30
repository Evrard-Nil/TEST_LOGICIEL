package hello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de tests unitaires JUnit4 de la classe HelloJUnit.
 *
 * @version 2.0
 * @author Matthias Brun
 *
 */
public class HelloJUnitTest
{
	/**
	 * Message par défaut à afficher au monde.
	 */
	public static final String MESSAGE_DEFAUT = "Hello World !";
	
	/**
	 * Objet HelloWorld nécessaire pour les tests.
	 */
	HelloJUnit helloworld;
	
	/**
	 * Création d'un objet HelloWorld nécessaire pour les tests.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@Before
	public void setUp() throws Exception
	{
		this.helloworld = new HelloJUnit(HelloJUnitTest.MESSAGE_DEFAUT);
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
		/* rien à faire */
	}
	
	/**
	 * Test de la méthode de modification publique d'un message.
	 * 
	 * <p>Méthode concernée : public void fixeMessageModifiePublic(String modification)</p>
	 */
	@Test
	public void testFixeMessageModifiePublic()
	{
		/* Donnée de test. */
		String modification = "Hello JUnit !";
		
		/* Appel de la méthode testée. */
		this.helloworld.fixeMessageModifiePublic(modification);
		
		/* Assertion. */
		String messageAttendu = HelloJUnitTest.MESSAGE_DEFAUT 
								+ HelloJUnit.SEP_MODIFICATION 
								+ HelloJUnit.PUBLIC
								+ HelloJUnit.SEP_MODIFICATION
								+ modification;
		
		String messageActuel = this.helloworld.donneMessage();
		
		assertEquals("Message non conforme.", messageAttendu, messageActuel);
	}
	
	/**
	 * Test de la méthode de modification privée d'un message.
	 * 
	 * <p>Méthode concernée : private void fixeMessageModifie(String modification)</p>
	 */
	@Test
	public void testFixeMessageModifie()
	{
		/* Donnée de test. */
		String modification = "Hello JUnit !";
		
		/* Pour les accès à la méthode privée. */
		String methodeTestee = "fixeMessageModifie";
		Class<?>[] parametresMethodetestee = {String.class};
		
		Method methode;
		
		try {
			/* Accès à la méthode. */
			methode = HelloJUnit.class.getDeclaredMethod(methodeTestee, parametresMethodetestee);
			methode.setAccessible(true);
			methode.invoke(this.helloworld, modification);
		
			/* Assertions. */
			String messageAttendu = HelloJUnitTest.MESSAGE_DEFAUT
									+ HelloJUnit.SEP_MODIFICATION
									+ modification;
		
			String messageActuel = this.helloworld.donneMessage();
		
			assertEquals("Message non conforme.", messageAttendu, messageActuel);

		} catch (SecurityException e) {
			fail("Problème de sécurité sur la réflexion.");
		} catch (NoSuchMethodException e) {
			fail("Méthode testée non existante.");
		} catch (IllegalArgumentException e) {
			fail("Arguments de la méthode testée invalides.");
		} catch (IllegalAccessException e) {
			fail("Accès illégal à la méthode testée.");
		} catch (InvocationTargetException e) {
			fail("Problème d'invocation de la méthode testée.");
		}
	}
	
	/**
	 * Test de la méthode de création privée d'un message modifié.
	 * 
	 * <p>Méthode concernée : private String donneMessageModifie(String modification)</p>
	 */
	@Test
	public void testDonneMessageModifie()
	{
		/* Donnée de test. */
		String modification = "Hello JUnit !";
		
		/* Pour les accès à la méthode privée. */
		String methodeTestee = "donneMessageModifie";
		Class<?>[] parametresMethodetestee = {String.class};
				
		Method methode;
		
		try {
			/* Accès à la méthode. */
			methode = HelloJUnit.class.getDeclaredMethod(methodeTestee, parametresMethodetestee);
			methode.setAccessible(true);
			String messageCree = (String) methode.invoke(this.helloworld, modification);
		
			/* Assertion. */
			String messageAttendu = HelloJUnitTest.MESSAGE_DEFAUT
									+ HelloJUnit.SEP_MODIFICATION
									+ modification;

			assertEquals("Message non conforme.", messageAttendu, messageCree);

		} catch (SecurityException e) {
			fail("Problème de sécurité sur la réflexion.");
		} catch (NoSuchMethodException e) {
			fail("Méthode testée non existante.");
		} catch (IllegalArgumentException e) {
			fail("Arguments de la méthode de réflexion invalides.");
		} catch (IllegalAccessException e) {
			fail("Accès illégal à la méthode testée.");
		} catch (InvocationTargetException e) {
			fail("Problème d'invocation de la méthode testée.");
		}
	}
	
	/**
	 * Test de la méthode de modification d'un message caché.
	 * 
	 * <p>Un message caché a une visibilité privée 
	 * et n'est pas accessible en lecture par une méthode publique (accesseur).</p>
	 * 
	 * <p>Méthode concernée : public void fixeMessageCache(String message)</p>
	 * <p>Attribut concerné : private messageCache</p>
	 */
	@Test
	public void testFixeMessageCache()
	{
		/* Donnée de test. */
		String message = "hello JUnit...";
		
		/* Appel de la méthode testée. */
		this.helloworld.fixeMessageCache(message);
		
		/* Pour l'accès au message caché (privé) */
		String attributConcerne = "messageCache";
		Field attribut;
		
		try {
			attribut = HelloJUnit.class.getDeclaredField(attributConcerne);
			attribut.setAccessible(true);
			String messageActuel = (String) attribut.get(this.helloworld);
		
			/* Assertion. */
			String messageAttendu = message;
		
			assertEquals("Message non conforme.", messageAttendu, messageActuel);

		} catch (SecurityException e) {
			fail("Problème de sécurité sur la réflexion.");
		} catch (NoSuchFieldException e) {
			fail("Attribut concerné non existant.");
		} catch (IllegalArgumentException e) {
			fail("Arguments de la méthode de réflexion invalides.");
		} catch (IllegalAccessException e) {
			fail("Accès illégal à l'attribut concerné.");
		}	
	}
}
