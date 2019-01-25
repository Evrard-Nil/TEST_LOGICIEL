package felix.vue;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import felix.Felix;
import felix.controleur.ControleurFelix;
import felix.controleur.VueFelix;

/**
 * Classe de la vue chat de Felix.
 * 
 * Cette vue permet d'échanger des messages avec d'autres utilisateurs du chat.
 * 
 * Cette vue est une vue active : elle possède une méthode d'activation qui lance 
 * un thread de réception des messages du chat.
 * 
 * @version 3.0
 * @author Matthias Brun 
 *
 */
public class VueChat extends VueFelix implements ActionListener, Runnable
{
	/**
	 * La fenêtre de la vue.
	 */
	private Fenetre	fenetre;

	/**
	 * Le conteneur de la vue.
	 */
	private Container contenu;
	
	/**
	 * Le panneau de la fenêtre pour saisir les messages.
	 */
	private JPanel panMessage;

	/**
	 * Le panneau de la fenêtre pour afficher les messages du chat.
	 */
	private JPanel panChatMessages;
	
	/**
	 * Le champs de saisi des messages.
	 */
	private JTextField texteMessage;

	/**
	 * Le panneau texte des messages du chat.
	 */
	private JTextPane texteChatMessages;

	/**
	 * Le panneau de défilement des messages du chat.
	 */
	private JScrollPane defilementChatMessages;

	/**
	 * Le style de document des messages du chat.
	 */
	private transient StyledDocument documentChatMessages;

	/**
	 * Le style des messages du chat.
	 */
	private transient Style documentChatMessagesStyle;
	
	/**
	 * Le nom du style des messages du chat.
	 */
	private static final String STYLENAME = "documentChatMessagesStyle";
	
	/**
	 * Constructeur de la vue chat.
	 * 
	 * @param controleur le contrôleur du chat auquel appartient la vue.
	 */
	public VueChat(ControleurFelix controleur) 
	{
		super(controleur);
		
		final Integer largeur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CHAT_LARGEUR"));
		final Integer hauteur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CHAT_HAUTEUR"));
		
		this.fenetre = new Fenetre(largeur, hauteur, Felix.CONFIGURATION.getString("FENETRE_CHAT_TITRE"));
		
		this.construireFenetre(largeur, hauteur);	
	}

	/**
	 * Construire les panneaux et les widgets de contrôle de la vue.
	 *
	 * @param largeur la largeur de la fenêtre.
	 * @param hauteur la hauteur de la fenêtre.
	 */
	private void construireFenetre(Integer largeur, Integer hauteur)
	{
		this.construirePanneaux();
		this.construireControles(largeur, hauteur);
	}
	
	/**
	 * Construire les panneaux de la fenêtre.
	 *
	 */
	private void construirePanneaux()
	{
		this.contenu = this.fenetre.getContentPane();
		this.contenu.setLayout(new FlowLayout());

		this.panMessage = new JPanel();
		this.contenu.add(this.panMessage);

		this.panChatMessages = new JPanel();
		this.contenu.add(this.panChatMessages);
	}

	/**
	 * Construire les widgets de contrôle de la fenêtre.
	 * 
	 * @param largeur la largeur de la fenêtre.
	 * @param hauteur la hauteur de la fenêtre.
	 *
	 */
	private void construireControles(Integer largeur, Integer hauteur)
	{
		final Integer mLargeur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CHAT_MARGE_LARGEUR"));
		final Integer mHauteur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CHAT_MARGE_HAUTEUR"));
		final Integer hMessage = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CHAT_HAUTEUR_MESSAGE"));

		/* message */
		this.texteMessage = new JTextField();
		this.texteMessage.setPreferredSize(new Dimension(largeur - mLargeur, hMessage));
		this.texteMessage.setHorizontalAlignment(JTextField.LEFT);
		this.texteMessage.setEditable(true);
		this.texteMessage.setFocusable(true);
		this.texteMessage.requestFocus();
		this.texteMessage.addActionListener(this);
		this.panMessage.add(this.texteMessage);
	
		/* chat messages */
		this.texteChatMessages = new JTextPane();
		this.texteChatMessages.setEditable(false);

		this.documentChatMessages = this.texteChatMessages.getStyledDocument();

		this.documentChatMessagesStyle = this.documentChatMessages.addStyle(STYLENAME, null);
		StyleConstants.setFontFamily(this.documentChatMessagesStyle, "Monospaced");

		this.defilementChatMessages = new JScrollPane(this.texteChatMessages);
		this.defilementChatMessages.setPreferredSize(new Dimension(largeur - mLargeur, hauteur - mHauteur));
        	this.defilementChatMessages.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.panChatMessages.add(this.defilementChatMessages);
	}

	/**
	 * Envoi d'un message au chat.
	 *
	 * @param ev un évènement d'action.
	 *
	 * @see java.awt.event.ActionListener
	 */
	public void actionPerformed(ActionEvent ev)
	{
		final String message = ev.getActionCommand();

		try {
			// Envoi du message au chat.
			this.donneControleur().donneConnexion().ecrire(message);

			// Efface le champs de saisi du message.
			this.texteMessage.setText("");
		} 
		catch (IOException ex) {
			System.err.println("Écriture du message impossible.");
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Point d'entrée du thread de réception des messages du chat.
	 */
	@Override
	public void run() 
	{
		try {
			while (true) {
				// Reception d'un message.
				final String message = this.donneControleur().donneConnexion().lire();

				// Écriture du message dans la zone des messages du chat (à la suite).
				this.documentChatMessages.insertString(
					this.documentChatMessages.getLength(),
					message + System.getProperty("line.separator"), 
					this.documentChatMessages.getStyle(STYLENAME)
				);

				// Force l'affichage du dernier message reçu.
				this.texteChatMessages.setCaretPosition(this.documentChatMessages.getLength());
			}
		}
		catch (BadLocationException ex) {
			System.err.println("Affichage des messages du chat impossible.");
			System.err.println(ex.getMessage());
		}
		catch (EOFException ex) {
			System.err.println("Connexion interrompue avec le serveur du chat.");
		}
		catch (IOException ex) {
			System.err.println("Lecture des messages du chat impossible.");
			System.err.println(ex.getMessage());
		}
		finally	{
			this.donneControleur().donneConnexion().ferme();
		}
	}
	
	/**
	 * Activation de la réception des messages du chat.
	 */
	public void active()
	{
		new Thread(this).start();
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see felix.controleur.VueFelix
	 */
	@Override
	public void affiche() 
	{
		this.fenetre.setVisible(true);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @see felix.controleur.VueFelix
	 */
	@Override
	public void ferme() 
	{
		this.fenetre.dispose();
	}

	
}
