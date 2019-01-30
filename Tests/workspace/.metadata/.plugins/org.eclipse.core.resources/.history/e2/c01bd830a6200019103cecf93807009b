package felix.vue;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import felix.Felix;
import felix.controleur.ControleurFelix;
import felix.controleur.VueFelix;

/**
 * Classe de la vue connexion de Felix.
 * 
 * Cette vue permet d'initier une connexion au chat.
 * 
 * Cette vue est une vue active : elle possède une méthode de connexion
 * qui lance un thread de connexion au chat.
 *  
 * @version 4.0
 * @author Matthias Brun 
 *
 */
public class VueConnexion extends VueFelix implements ActionListener, Runnable
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
	 * Les panneaux de la vue.
	 */
	private JPanel panIPPort, panMessages, panConnecter;

	/**
	 * Les champs texte pour l'adresse IP et les messages.
	 */
	private JTextField texteIP, texteMessages;
	
	/**
	 * Le champs texte formaté du port de la vue.
	 */
	private JFormattedTextField textePort;
	
	/**
	 * Le bouton connecter de la vue.
	 */
	private JButton boutonConnecter;
	
	/**
	 * Constructeur de la vue chat.
	 * 
	 * @param controleur le contrôleur du chat auquel appartient la vue.
	 */
	public VueConnexion(ControleurFelix controleur) 
	{
		super(controleur);
		
		final Integer largeur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_LARGEUR"));
		final Integer hauteur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_HAUTEUR"));
		
		this.fenetre = new Fenetre(largeur, hauteur, Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TITRE"));
		
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

		this.panIPPort = new JPanel();
		this.contenu.add(this.panIPPort);
		
		this.panMessages = new JPanel();
		this.contenu.add(this.panMessages);

		this.panConnecter = new JPanel();
		this.contenu.add(this.panConnecter);
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
		final Integer mLargeur = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MARGE_LARGEUR"));
		final Integer hMessage = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_HAUTEUR_MESSAGE"));

		/* Saisie de l'IP. */	
		this.texteIP = new JTextField(Felix.CONFIGURATION.getString("ADRESSE_CHAT"), 
				Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TAILLE_SAISIE_IP")));
		this.texteIP.setEditable(true);
		this.texteIP.requestFocus();
		this.panIPPort.add(this.texteIP);
		
		/* Saisie du port. */
		this.textePort = new JFormattedTextField(NumberFormat.getIntegerInstance());
		this.textePort.setValue(Integer.parseInt(Felix.CONFIGURATION.getString("PORT_CHAT")));
		this.textePort.setColumns(
				Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TAILLE_SAISIE_PORT")));
		this.textePort.setEditable(true);
		this.panIPPort.add(this.textePort);

		/* Messages de connexion. */
		this.texteMessages = new JTextField(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT"));
		this.texteMessages.setPreferredSize(new Dimension(largeur - mLargeur, hMessage));
		this.texteMessages.setHorizontalAlignment(JTextField.LEFT);
		this.texteMessages.setEditable(false);
		this.texteMessages.setFocusable(false);
		this.panMessages.add(this.texteMessages);
	
		/* Bouton de connexion */
		this.boutonConnecter = new JButton(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_BOUTON_CONNECTER"));
		this.boutonConnecter.addActionListener(this);
		this.panConnecter.add(this.boutonConnecter);
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
		try {
			if (ev.getSource() == this.boutonConnecter) {
				
				this.texteMessages.setText(String.format(
					Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"), 
					this.texteIP.getText().trim(), 
					((Number) this.textePort.getValue()).toString()));
							
				this.boutonConnecter.setEnabled(false);
				
				/* Initiation de la connexion. */
				new Thread(this).start();
				
			} else {
				/* Évènement inconnu. */
				System.err.println("Réception d'un évènement inconnu sur la vue connexion.");
			}
		}
		catch (NumberFormatException exception) {
	    	/* Format invalide dans le champ du port. */
			System.err.println("Format invalide dans le champ port de la vue connexion.");
		}
	}
	
	/**
	 * Point d'entrée du thread de connexion au chat.
	 */
	@Override
	public void run() 
	{
		try {
			Thread.sleep(500);
			this.donneControleur().connecteCamix(
					this.texteIP.getText().trim(), ((Number) this.textePort.getValue()).intValue());
			
		} catch (IOException | InterruptedException e) {
			afficheConnexionImpossible();
		} finally {
			this.boutonConnecter.setEnabled(true);
		}
	}
	
	/**
	 * Affichage du message de connexion impossible.
	 * 
	 */
	private void afficheConnexionImpossible()
	{
		this.texteMessages.setText(String.format(
				Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"), 
					this.texteIP.getText().trim(), 
					((Number) this.textePort.getValue()).toString()));
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
