package monix.vue;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.LinkedHashMap;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import monix.Monix;
import monix.controleur.ControleurVente;
import monix.controleur.VueVente;
import monix.modele.stock.Produit;
import monix.modele.vente.Achat;
import monix.modele.vente.VenteAchatEvenement;
import monix.modele.vente.VenteChangeEvenement;
import monix.modele.vente.VenteEcouteur;

/**
 * Classe de la vue du client lors d'une vente (architecture MVC).
 * 
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.controleur.VueVente
 * @see monix.modele.vente.VenteEcouteur
 *
 */ 
public class VueClient extends VueVente  implements VenteEcouteur
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
	private JPanel panTicket, panTotal;

	/**
	 * Les champs texte de la vue.
	 */
	private JTextField texteTotal;

	/**
	 * Les panneaux textes de la vue (ticket).
	 */
	private JTextPane texteTicket;

	/**
	 * Le panneau de défilement du ticket.
	 */
	private JScrollPane defilementTicket;

	/**
	 * Le document de style du ticket.
	 */
	private StyledDocument documentTicket;
	/** Le style du ticket. */
	private Style documentTicketStyle;


	/**
	 * Constructeur de la vue.
	 *
	 * @param controleur le contrôleur associé à la vue.
	 *
	 * @see monix.controleur.VueVente
	 */
	public VueClient(ControleurVente controleur)
	{
		super(controleur);

		this.fenetre = new Fenetre(
			Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_LARGEUR")),
			Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_HAUTEUR")),
			Monix.MESSAGES.getString("CLIENT_FENETRE_TITRE")
		);

		this.construireFenetre();	
	}

	/**
	 * Construire les panneaux et les widgets de contrôle de la vue.
	 *
	 */
	private void construireFenetre()
	{
		this.construirePanneaux();
		this.construireControles();
	}

	/**
	 * Construire les panneaux de la vue.
	 *
	 */
	private void construirePanneaux()
	{
		this.contenu = this.fenetre.getContentPane();
		this.contenu.setLayout(new FlowLayout());

		this.panTicket = new JPanel();
		this.contenu.add(this.panTicket);

		this.panTotal = new JPanel();
		this.contenu.add(this.panTotal);
	}

	/**
	 * Construire les widgets de contrôle de la vue.
	 *
	 */
	private void construireControles()
	{
		/* Ticket de caisse. */
		this.texteTicket = new JTextPane();
		this.texteTicket.setEditable(false);

		this.documentTicket = this.texteTicket.getStyledDocument();

		this.documentTicketStyle = this.documentTicket.addStyle("documentTicketStyle", null);
		StyleConstants.setFontFamily(this.documentTicketStyle, "Monospaced");

		this.afficheDocumentTicketVierge();

		this.defilementTicket = new JScrollPane(this.texteTicket);
		this.defilementTicket.setPreferredSize(
			new Dimension(
				Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_LARGEUR")) 
				- Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_MARGE_LARGEUR")), 
				Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_HAUTEUR")) 
				- Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_MARGE_HAUTEUR"))
			)
		);
        	this.defilementTicket.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.panTicket.add(this.defilementTicket);

		/* Prix total. */
		this.texteTotal = new JTextField(Monix.MESSAGES.getString("CLIENT_TEXTE_PRIX_TOTAL"));
		this.texteTotal.setPreferredSize(
			new Dimension(Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_LARGEUR"))
					- Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_MARGE_LARGEUR")), 
					Integer.parseInt(Monix.CONFIGURATION.getString("CLIENT_FENETRE_HAUTEUR_PRIX_TOTAL")))
		);
		this.texteTotal.setHorizontalAlignment(JTextField.RIGHT);
		this.texteTotal.setEditable(false);
		this.texteTotal.setFocusable(false);
		this.panTotal.add(this.texteTotal);

	}

	/**
	 * Affiche un ticket vierge.
	 *
	 */
	private void afficheDocumentTicketVierge()
	{
		try {
			this.documentTicket.remove(0, this.documentTicket.getLength());

			this.documentTicket.insertString(
				this.documentTicket.getLength(), 
				Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET"),
				this.documentTicket.getStyle("documentTicketStyle")
			);
		} 
		catch (BadLocationException e) {
			System.err.println("Insertion du texte initial du ticket impossible.");
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à l'ajout d'un achat dans le modèle de vente.
	 * Rien n'est prévu dans l'IHM actuellement (tout se fait dans venteChange).
	 * </p>
	 * 
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	@Override
	public void venteAjouteAchat(VenteAchatEvenement ev) 
	{
		// Rien à faire.
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à l'annulation d'un achat dans le modèle de vente.
	 * Rien n'est prévu dans l'IHM actuellement (tout se fait dans venteChange).
	 * </p>
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	@Override
	public void venteAnnuleAchat(VenteAchatEvenement ev)
	{
		// Rien à faire.
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à la modification d'une vente.
	 * Modification du ticket client dans l'IHM.
	 * </p>
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteChangeEvenement
	 *
	 */
	@Override
	public void venteChange(VenteChangeEvenement ev)
	{
		this.afficheDocumentTicketVierge();
		this.afficheDocumentVente(ev);
		this.afficheDocumentTotal(ev);
	}

	/** 
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à la clôture d'une vente.
	 * Afficher un ticket vierge dans l'IHM.
	 * </p>
	 * 
	 * @see monix.modele.vente.VenteEcouteur
	 */
	@Override
	public void venteCloture()
	{
		this.afficheDocumentTicketVierge();
		this.texteTotal.setText(Monix.MESSAGES.getString("CLIENT_TEXTE_PRIX_TOTAL"));
	}

	/**
	 * Affiche un ticket.
	 *
	 * @param ev un évènement de changement de vente.
	 *
	 * @see monix.modele.vente.VenteChangeEvenement
	 *
	 */
	private void afficheDocumentVente(VenteChangeEvenement ev) 
	{
		final LinkedHashMap<String, Achat> achats = ev.donneAchats();

		final Iterator<String> iter = achats.keySet().iterator();

		try {
			while (iter.hasNext()) {

				final Achat achat = achats.get(iter.next());
				final Produit produit = achat.donneProduit();
				final Integer quantite = achat.donneQuantite();

				final String info = this.formatInfo(produit.donneLibelle(), quantite, produit.donnePrix());
				
				this.documentTicket.insertString(
					this.documentTicket.getLength(), "\n" + info, this.documentTicket.getStyle("documentTicketStyle")
				);
			}
		} 
		catch (BadLocationException e) {
			System.err.println("Affichage du ticket impossible.");
		}
	}

	/**	
	 * Formatte les informations de vente pour un ticket.
	 *
	 * @param libelle le libellé du produit.
	 * @param quantite la quantité de produit acheté.
	 * @param prix le prix unitaire du produit.
	 * @return les informations de vente.
	 *
	 */
	private String formatInfo(String libelle, Integer quantite, Double prix)
	{
		final Integer libelleLargeur = Integer.parseInt(
						Monix.CONFIGURATION.getString("CLIENT_FENETRE_TICKET_LIBELLE_LARGEUR"));
		final Integer quantiteLargeur = Integer.parseInt(
						Monix.CONFIGURATION.getString("CLIENT_FENETRE_TICKET_QUANTITE_LARGEUR"));
		final Integer prixLargeur = Integer.parseInt(
						Monix.CONFIGURATION.getString("CLIENT_FENETRE_TICKET_PRIX_LARGEUR"));

		final String chaineDeTronquage = "...";
		final Integer tailleTronquage = chaineDeTronquage.length();
		
		String libelleTicket; 

		if (libelle.length() > libelleLargeur) {
			libelleTicket = libelle.substring(0, libelleLargeur - tailleTronquage) + chaineDeTronquage;
		} else {
			libelleTicket = libelle;
			libelleTicket = this.completeInfoApres(libelleTicket, libelleLargeur - libelleTicket.length());
		}

		String quantiteTicket = String.format("%1$d", quantite);
		quantiteTicket = this.completeInfoApres(quantiteTicket, quantiteLargeur - quantiteTicket.length());

		String prixTicket = String.format("%1$.2f €", prix);
		prixTicket = this.completeInfoAvant(prixTicket, prixLargeur - prixTicket.length());

		return libelleTicket + "  x " + quantiteTicket + " x " + prixTicket;
	}

	/**
	 * Indentation (après information).
	 *
	 * @param donnee la donnée à indenter.
	 * @param complete la longueur de l'indentation.
	 * @return l'indentation.
	 *
	 */
	private String completeInfoApres(String donnee, Integer complete)
	{
		String donneeComplete = donnee;
		
		for (int i = 0; i < complete; i++) {
			donneeComplete = donneeComplete.concat(" ");
		}
		return donneeComplete;
	}

	/**
	 * Indentation (avant information).
	 *
	 * @param donnee la donnée à indenter.
	 * @param complete la longueur de l'indentation.
	 * @return l'indentation.
	 *
	 */
	private String completeInfoAvant(String donnee, Integer complete)
	{
		String donneeComplete = donnee;
		
		for (int i = 0; i < complete; i++) {
			donneeComplete = " ".concat(donneeComplete);
		}
		return donneeComplete;
	}

	/**
	 * Affiche le prix total de la vente.
	 *
	 * @param ev un évènement de changement de vente.
	 *
	 * @see monix.modele.vente.VenteChangeEvenement
	 *
	 */
	private void afficheDocumentTotal(VenteChangeEvenement ev) 
	{
		this.texteTotal.setText(String.format("%1$.2f €", ev.donnePrix()));
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see monix.controleur.VueVente
	 */
	@Override
	public void affiche() 
	{
		this.fenetre.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see monix.controleur.VueVente
	 */
	@Override
	public void ferme() 
	{
		this.fenetre.dispose();
	}

}
