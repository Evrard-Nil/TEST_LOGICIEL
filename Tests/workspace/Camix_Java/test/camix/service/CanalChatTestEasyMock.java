package camix.service;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Classe de tests unitaires JUnit 4 de la classe CanalChat (version simple, pour exemple).
 *
 * <p>Utilisation d'un mock (simulacre) de ClientChat (construit avec EasyMock).</p>
 *
 * @version 4.0
 * @author Barthélémy Tek
 *
 * @see camix.service.CanalChat
 * @see org.easymock.EasyMock
 *
 */
@RunWith(EasyMockRunner.class)
public class CanalChatTestEasyMock {

	/**
	 * Client utilisé pour les tests
	 */
	@Mock
	private ClientChat clientMock;
	
	
	/**
	 * Crée, avec EasyMock, un simulacre de client nécessaire aux tests.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 * @see org.easymock.EasyMock
	 *
	 */
	@Before
	public void setUp() throws Exception 
	{

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
		/* rien faire */
	}
	
	
	/**
	 * Teste l'ajout d'un client dans un CanalChat
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteClient(ClientChat c)
	 * </p>
	 */
	@Test
	public void test_ajouteClient_clientNonPresent_v1() {
		
		String id = "123";
		
		CanalChat canal = new CanalChat("test");
		
		EasyMock.expect(this.clientMock.donneId()).andReturn(id).times(3);
	
		EasyMock.replay(this.clientMock);
		
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, (int) canal.donneNombreClients());
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(canal.estPresent(clientMock));

		
		// Vérification des sollicitations faites au mock.
		EasyMock.verify(this.clientMock);
	}
	
	/**
	 * Teste l'ajout d'un client dans un CanalChat alors qu'il est déjà présent
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteClient(ClientChat c)
	 * </p>
	 */
	@Test
	public void test_ajouteClient_clientPresent_v1() {
		
		String id = "123";
		
		CanalChat canal = new CanalChat("test");
	
		
		EasyMock.expect(this.clientMock.donneId()).andReturn(id).times(4);
	
		EasyMock.replay(this.clientMock);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canal.ajouteClient(clientMock);
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, (int) canal.donneNombreClients());
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(canal.estPresent(clientMock));
		
		// Vérification des sollicitations faites au mock.
		EasyMock.verify(this.clientMock);
	
	}
	
	/**
	 * Teste l'ajout d'un client dans un CanalChat
	 * Vérifie l'ajout en utilisant l'introspection
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteClient(ClientChat c)
	 * </p>
	 */
	@Test
	public void test_ajouteClient_clientNonPresent_v2() {
		
		String id = "123";
		
		CanalChat canal = new CanalChat("test");
		
		String clientsName = "clients";
		Field attribut;
		Hashtable<String, ClientChat> clients = null;
	
		try {
			attribut = CanalChat.class.getDeclaredField(clientsName);
			attribut.setAccessible(true);
			clients = ((Hashtable<String, ClientChat>) attribut.get(canal));
		} 
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException  e) {
			Assert.fail("Problème d'introspection");
		}
		
		EasyMock.expect(this.clientMock.donneId()).andReturn(id).times(2);
	
		EasyMock.replay(this.clientMock);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canal.ajouteClient(clientMock);

		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
		
		// Vérification des sollicitations faites au mock.
		EasyMock.verify(this.clientMock);

	}
	
	/**
	 * Teste l'ajout d'un client dans un CanalChat alors qu'il est déjà présent
	 * Vérifie l'ajout avec l'introspection
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteClient(ClientChat c)
	 * </p>
	 */
	@Test
	public void test_ajouteClient_clientPresent_v2() {
		
		String id = "123";
		
		CanalChat canal = new CanalChat("test");
		
		String clientsName = "clients";
		Field attribut;
		Hashtable<String, ClientChat> clients = null;
		
		try {
			attribut = CanalChat.class.getDeclaredField(clientsName);
			attribut.setAccessible(true);
			clients = ((Hashtable<String, ClientChat>) attribut.get(canal));
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException  e) {
			Assert.fail("Problème d'introspection");
		}
		
		EasyMock.expect(this.clientMock.donneId()).andReturn(id).times(3);
	
		EasyMock.replay(this.clientMock);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canal.ajouteClient(clientMock);
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
		
		// Vérification des sollicitations faites au mock.
		EasyMock.verify(this.clientMock);
	}
	
	/**
	 * Teste l'ajout d'un client dans un CanalChat alors qu'il est déjà présent
	 * Vérifie l'ajout avec l'introspection
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteClient(ClientChat c)
	 * </p>
	 */
	@Test
	public void test_ajouteClient_clientPresent_v2_1() {
		
		String id = "123";
		
		CanalChat canal = new CanalChat("test");
		
		String clientsName = "clients";
		Field attribut;
		Hashtable<String, ClientChat> clients = null;
		
		try {
			attribut = CanalChat.class.getDeclaredField(clientsName);
			attribut.setAccessible(true);
			clients = ((Hashtable<String, ClientChat>) attribut.get(canal));
			((Hashtable<String, ClientChat>) attribut.get(canal)).put(id, clientMock);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException  e) {
			Assert.fail("Problème d'introspection");
		}
	
		EasyMock.expect(this.clientMock.donneId()).andReturn(id).times(1);
		
		EasyMock.replay(this.clientMock);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
		
		// Vérification des sollicitations faites au mock.
		EasyMock.verify(this.clientMock);
	}
	
	/**
	 * Teste l'ajout d'un client dans un CanalChat
	 * Vérifie l'ajout en utilisant l'introspection
	 * 
	 * Utilise un mock partiel pour remplacer la methode estPresent
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteClient(ClientChat c)
	 * </p>
	 */
	@Test
	public void test_ajouteClient_clientNonPresent_v3() {
		
		String id = "123";
		
		CanalChat canalMock = EasyMock.partialMockBuilder(CanalChat.class)
				  .addMockedMethod("estPresent").withConstructor("test").createMock();
		
		String clientsName = "clients";
		Field attribut;
		Hashtable<String, ClientChat> clients = null;
		
		try {
			attribut = CanalChat.class.getDeclaredField(clientsName);
			attribut.setAccessible(true);
			clients = ((Hashtable<String, ClientChat>) attribut.get(canalMock));
			((Hashtable<String, ClientChat>) attribut.get(canalMock)).put(id, clientMock);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException  e) {
			Assert.fail("Problème d'introspection");
		}
		
	
		EasyMock.expect(
				this.clientMock.donneId()
			).andReturn(
				id
			).times(1);
		
		EasyMock.expect(
				canalMock.estPresent(this.clientMock)
			).andReturn(
				false
			);
	
		EasyMock.replay(this.clientMock);
		EasyMock.replay(canalMock);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canalMock.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
			
		// Vérification des sollicitations faites au mock.
		EasyMock.verify(this.clientMock);
		EasyMock.verify(canalMock);

	}
	
	/**
	 * Teste l'ajout d'un client dans un CanalChat alors qu'il est déjà présent
	 * Vérifie l'ajout avec l'introspection
	 * 
	 * Utilise un mock partiel pour remplacer la methode estPresent
	 * 
	 * <p>
	 * Méthode concernée : public void ajouteClient(ClientChat c)
	 * </p>
	 */
	@Test
	public void test_ajouteClient_clientPresent_v3() {
		
		String id = "123";
		
		CanalChat canalMock = EasyMock.partialMockBuilder(CanalChat.class)
				  .addMockedMethod("estPresent").withConstructor("test").createMock();
		
		String clientsName = "clients";
		Field attribut;
		Hashtable<String, ClientChat> clients = null;
		
		try {
			attribut = CanalChat.class.getDeclaredField(clientsName);
			attribut.setAccessible(true);
			clients = (Hashtable<String, ClientChat>) attribut.get(canalMock);
			((Hashtable<String, ClientChat>) attribut.get(canalMock)).put(id, clientMock);
		}
		catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException  e) {
			Assert.fail("Problème d'introspection");
		}
		
		EasyMock.expect(
				canalMock.estPresent(this.clientMock)
			).andReturn(
				true
			).times(1);
	
		EasyMock.replay(this.clientMock);
		EasyMock.replay(canalMock);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canalMock.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
			
		// Vérification des sollicitations faites au mock.
		EasyMock.verify(this.clientMock);
		EasyMock.verify(canalMock);
	}
}
