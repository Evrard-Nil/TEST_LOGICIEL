package camix.service;

import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import camix.communication.ProtocoleChat;

@RunWith(EasyMockRunner.class)
public class ServiceChatTestEasyMock {

	/**
	 * Client utilisé pour les tests
	 */
	@Mock
	private ClientChat clientMock;

	/**
	 * Crée, avec EasyMock, un simulacre de client nécessaire aux tests.
	 *
	 * <p>
	 * Code exécuté avant les tests.
	 * </p>
	 *
	 * @throws Exception
	 *             toute exception.
	 *
	 * @see org.easymock.EasyMock
	 *
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * Non implanté.
	 *
	 * <p>
	 * Code exécuté après les tests.
	 * </p>
	 *
	 * @throws Exception
	 *             toute exception.
	 *
	 */
	@After
	public void tearDown() throws Exception {
		/* rien faire */
	}

	/**
	 * Teste l'exécution de la méthode informeDepartClient de ServiceChat
	 * 
	 * <p>
	 * Méthode concernée : public void informeDepartClient(ClientChat c)
	 * </p>
	 */
	@Test(timeout = 2000)
	public void test_informeDepartClient() {

		String surnom = "Test Surnom";

		String message = String.format(ProtocoleChat.MESSAGE_DEPART_CHAT, surnom);

		ServiceChat service = null;
		try {
			service = new ServiceChat("test");
		} catch (IOException e) {
			e.printStackTrace();
		}

		EasyMock.expect(this.clientMock.donneSurnom()).andReturn(surnom).times(1);
		clientMock.envoieContacts(message);

		EasyMock.replay(this.clientMock);

		// On ajoute deux fois le client pour simuler le fait d'avoir déjà le client
		// dans notre canal
		service.informeDepartClient(clientMock);

		EasyMock.verify(this.clientMock);
	}

}
