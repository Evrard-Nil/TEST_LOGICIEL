package felix.vue;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
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
	 * Panneaux de la vue:
	 * - Panneau saisie d'IP
	 * - Panneau saisie de PORT
	 * - Panneau border
	 * - panneau bouton de connexion
	 */
	private JPanel ipPanel, portPanel, borderPane, connectPane;
	
	/**
	 * Labels de la vu:
	 * - Label ip
	 * - Label port
	 * - Label d'infos
	 */
	private JLabel ipLabel, portLabel, infosLabel;
	
	/**
	 * Champs de saisie de la vue
	 * - IP
	 * - Port
	 */
	private JTextField ipTextField, portTextfield;
	
	/**
	 * Bouton de connexion
	 * 
	 */
	private JButton connectionButton;
	
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
		this.contenu.setLayout(new BorderLayout());
		
		this.borderPane = new JPanel(new FlowLayout());
		Integer borderLenght = Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MARGE"));
		this.borderPane.setBorder(BorderFactory.createEmptyBorder(borderLenght,borderLenght, borderLenght, borderLenght));
		
		this.ipPanel = new JPanel();
		this.ipLabel = new JLabel("IP: ");
		this.ipPanel.add(ipLabel);
		this.borderPane.add(this.ipPanel);
		
		this.portPanel = new JPanel();
		this.portLabel=new JLabel("PORT: ");
		this.portPanel.add(portLabel);
		this.borderPane.add(this.portPanel);

		this.borderPane.add(new JSeparator());
		this.connectPane=new JPanel();
		this.contenu.add(this.borderPane);
		
		this.fenetre.setResizable(false);
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

		this.ipTextField = new JTextField(Felix.CONFIGURATION.getString("ADRESSE_CHAT"), 
				Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TAILLE_SAISIE_IP")));
		this.ipTextField.setEditable(true);
		this.ipTextField.requestFocus();
		this.ipPanel.add(this.ipTextField);
		

		this.portTextfield = new JFormattedTextField(Integer.parseInt(Felix.CONFIGURATION.getString("PORT_CHAT")));
		this.portTextfield.setColumns(
				Integer.parseInt(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_TAILLE_SAISIE_PORT")));
		this.portTextfield.setEditable(true);
		this.portPanel.add(this.portTextfield);

		this.infosLabel = new JLabel(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_DEFAUT"));
		this.infosLabel.setPreferredSize(new Dimension(largeur - mLargeur, hMessage));
		this.borderPane.add(this.infosLabel);
		this.borderPane.add(new JSeparator());
	
		this.connectionButton = new JButton(Felix.CONFIGURATION.getString("FENETRE_CONNEXION_BOUTON_CONNECTER"));
		this.connectionButton.addActionListener(this);
		this.connectPane.add(this.connectionButton);
		this.borderPane.add(this.connectPane);
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
			if (ev.getSource() == this.connectionButton) {
				
				this.infosLabel.setText(String.format(
					Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION"), 
					this.ipTextField.getText().trim(), 
					((Number) (((JFormattedTextField) this.portTextfield).getValue())).toString()));
							
				this.connectionButton.setEnabled(false);
				
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
					this.ipTextField.getText().trim(), ((Number) ((JFormattedTextField) this.portTextfield).getValue()).intValue());
		} catch (IOException | InterruptedException e) {
			afficheConnexionImpossible();
		} finally {
			this.connectionButton.setEnabled(true);
		}
	}
	
	/**
	 * Affichage du message de connexion impossible.
	 * 
	 */
	private void afficheConnexionImpossible()
	{
		this.infosLabel.setText(String.format(
				Felix.CONFIGURATION.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"), 
					this.ipTextField.getText().trim(), 
					((Number) ((JFormattedTextField) this.portTextfield).getValue()).toString()));
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
