package felix.controleur;

import java.io.IOException;

import felix.Felix;
import felix.communication.Connexion;
import felix.vue.VueChat;

/**
 * Classe de contrôleur du chat (architecture MVC). 
 * 
 * Cette classe gère l'instanciation de la connexion au composant Camix,
 * ainsi que l'instanciation des vues et leurs affichages.
 * 
 * @version 3.0
 * @author Matthias Brun 
 */
public class ControleurFelix
{
	/**
	 * Connexion du chat (connexion à un composant Camix).
	 */
	private Connexion connexion;
	
	/**
	 * Accesseur à la connexion du chat.
	 * 
	 * @return la connexion à Camix du composant Felix.
	 */
	public Connexion donneConnexion()
	{
		return this.connexion;
	}
	
	/**
	 * Vue chat (permettant d'échanger des messages avec d'autres utilisateurs du chat).
	 */
	private VueChat vueChat;
	
	/**
	 * Constructeur du contrôleur de chat. 
	 */
	public ControleurFelix()
	{
		try {
			this.connecteCamix();
			this.vueChat = new VueChat(this);
			this.vueChat.affiche();
			this.vueChat.active();
		} 
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	/**
	 * Mise en place d'une connexion avec un serveur Camix.
	 * 
	 * @throws IOException erreur d'entrée/sortie.
	 */
	private void connecteCamix() throws IOException
	{
		this.connexion = new Connexion(
			Felix.CONFIGURATION.getString("ADRESSE_CHAT"),
			Integer.parseInt(Felix.CONFIGURATION.getString("PORT_CHAT"))
		);
	}
	
}
