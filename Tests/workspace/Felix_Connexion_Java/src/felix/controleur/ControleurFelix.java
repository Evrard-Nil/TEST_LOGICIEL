package felix.controleur;

import java.io.IOException;

import felix.communication.Connexion;
import felix.vue.VueChat;
import felix.vue.VueConnexion;

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
	 * Vue connexion (permettant d'initier une connexion avec le chat).
	 */
	private VueConnexion vueConnexion;
	
	/**
	 * Vue chat (permettant d'échanger des messages avec d'autres utilisateurs du chat).
	 */
	private VueChat vueChat;
	
	/**
	 * Constructeur du contrôleur de chat. 
	 */
	public ControleurFelix()
	{
		this.vueConnexion = new VueConnexion(this);
		this.vueConnexion.affiche();
	}
	
	/**
	 * Mise en place d'une connexion avec un serveur Camix.
	 * 
	 * @param adresse l'adresse du serveur du chat.
	 * @param port le port de connexion au chat.
	 * 
	 * @throws IOException erreur d'entrée/sortie.
	 */
	public void connecteCamix(String adresse, Integer port) throws IOException
	{
		try {
			this.connexion = new Connexion(adresse, port);
			basculeVueChat();
			
		} catch (IOException e) {
			System.err.println(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Bascule de la vue connexion à la vue chat.
	 */
	private void basculeVueChat()
	{
		this.vueChat = new VueChat(this);
		this.vueConnexion.ferme();
		this.vueChat.affiche();
		this.vueChat.active();
	}
	
	
}
