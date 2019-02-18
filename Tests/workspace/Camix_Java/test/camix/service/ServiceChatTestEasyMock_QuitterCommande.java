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

/**
 * Test unitaire de la méthode fermeConnexionCommande(ClientChat)
 * 
 * @author bart
 *
 */
@RunWith(EasyMockRunner.class)
public class ServiceChatTestEasyMock_QuitterCommande {

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
	 * Teste l'exécution de la méthode fermeConnexionCommande de ServiceChat
	 * 
	 * <p>
	 * Méthode concernée : public void fermeConnexionCommande(ClientChat c)
	 * </p>
	 */
	@Test(timeout = 2000)
	public void test_fermeConnexionCommande() {

		String message = String.format(ProtocoleChat.MESSAGE_SORTIE_CHAT);

		ServiceChat service = null;
		try {
			service = new ServiceChat("test");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// On mock le client, qui doit envoyer le message de sortie et fermer la
		// connexion
		EasyMock.expect(clientMock.donneId()).andReturn("123").times(1);
		clientMock.envoieMessage(message);
		clientMock.fermeConnexionCommande();

		EasyMock.replay(this.clientMock);

		// On lance la commande
		service.fermeConnexionCommande(clientMock);

		EasyMock.verify(this.clientMock);
	}

}
