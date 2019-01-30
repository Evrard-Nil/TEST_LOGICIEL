package camix.service;

import java.lang.reflect.Field;
import java.util.Hashtable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner; // Mockito >= 2.2.28 et JUnit >= 4.4


/**
 * Classe de tests unitaires JUnit 4 de la classe Vente (version simple, pour exemple).
 *
 * <p>Utilisation d'un mock (simulacre) de stock (construit avec Mockito).</p>
 *
 * @version 4.0
 * @author Matthias Brun
 *
 * @see monix.modele.vente.Vente
 * @see org.mockito.Mockito
 * @see org.mockito.runners.MockitoJUnitRunner
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CanalChatTestMockito 
{
	/**
	 * Client utilisé pour les tests.
	 */
	@Mock
	private ClientChat clientMock;

	/**
	 * Crée, avec Mockito, un simulacre de stock nécessaire aux tests.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 * @see org.mockito.Mockito
	 *
	 */
	@Before
	public void setUp() throws Exception 
	{
		/* utilisation alternative de @Mock */
		// this.stockMock = Mockito.mock(Stock.class);
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
		
		Mockito.when(this.clientMock.donneId()).thenReturn(id);
	
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, (int) canal.donneNombreClients());
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(canal.estPresent(clientMock));

		
		// Vérification des sollicitations faites au mock.
		Mockito.verify(this.clientMock, Mockito.times(3)).donneId();
		Mockito.verifyNoMoreInteractions(this.clientMock);
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
		
		Mockito.when(this.clientMock.donneId()).thenReturn(id);
	
		canal.ajouteClient(clientMock);
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, (int) canal.donneNombreClients());
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(canal.estPresent(clientMock));

		
		// Vérification des sollicitations faites au mock.
		Mockito.verify(this.clientMock, Mockito.times(4)).donneId();
		Mockito.verifyNoMoreInteractions(this.clientMock);	
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
		
	
		Mockito.when(
				this.clientMock.donneId()
			).thenReturn(
				id
			);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
		
		// Vérification des sollicitations faites au mock.
		Mockito.verify(this.clientMock, Mockito.times(2)).donneId();
		Mockito.verifyNoMoreInteractions(this.clientMock);			

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
	
		Mockito.when(this.clientMock.donneId()).thenReturn(id);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canal.ajouteClient(clientMock);
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
		
		// 	Vérification des sollicitations faites au mock.
		Mockito.verify(this.clientMock, Mockito.times(3)).donneId();
		Mockito.verifyNoMoreInteractions(this.clientMock);
	}
	
	/**
	 * Teste l'ajout d'un client dans un CanalChat alors qu'il est déjà présent
	 * Vérifie l'ajout avec l'introspection
	 * Ajoute le client en pré-condition avec l'introspection
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
	
		Mockito.when(this.clientMock.donneId()).thenReturn(id);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canal.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
		
		// 	Vérification des sollicitations faites au mock.
		Mockito.verify(this.clientMock, Mockito.times(1)).donneId();
		Mockito.verifyNoMoreInteractions(this.clientMock);
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
		
		
	    //you can create partial mock with spy() method:
    	CanalChat canalMock = Mockito.spy(new CanalChat("test"));
		
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
	
		Mockito.when(this.clientMock.donneId()).thenReturn(id);
		Mockito.doReturn(false).when(canalMock).estPresent(clientMock);		
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canalMock.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
			
		// 	Vérification des sollicitations faites au mock.
		Mockito.verify(this.clientMock, Mockito.times(1)).donneId();
		Mockito.verify(canalMock, Mockito.times(1)).estPresent(this.clientMock);
		Mockito.verifyNoMoreInteractions(this.clientMock);
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
		
	    //you can create partial mock with spy() method:
    	CanalChat canalMock = Mockito.spy(new CanalChat("test"));	
		
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
	
		Mockito.doReturn(true).when(canalMock).estPresent(clientMock);
		
		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client dans notre canal		
		canalMock.ajouteClient(clientMock);
		
		// On vérifie qu'on a bien un seul client
		Assert.assertEquals(1, clients.size());
				
		// On vérifier que le client ajouté est bien celui que l'on a mocké
		Assert.assertTrue(clients.containsKey(id));
		Assert.assertTrue(clients.containsValue(clientMock));
		
		// 	Vérification des sollicitations faites au mock.
		Mockito.verify(canalMock, Mockito.times(1)).estPresent(this.clientMock);
		Mockito.verifyNoMoreInteractions(this.clientMock);
	}
	
	
	
	public void test_informeDepartClient(ClientChat client) {
		
		
		
		
	}
}
