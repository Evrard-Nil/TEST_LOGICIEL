package monix.vue;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextField;

import monix.Monix;
import monix.controleur.ControleurVente;
import monix.controleur.VueVente;
import monix.modele.stock.Produit;
import monix.modele.vente.AchatImpossibleException;
import monix.modele.vente.VenteAchatEvenement;
import monix.modele.vente.VenteChangeEvenement;
import monix.modele.vente.VenteEcouteur;

/**
 * Classe de la vue de la caisse d'une vente (architecture MVC).
 * 
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.controleur.VueVente
 * @see monix.modele.vente.VenteEcouteur
 *
 */ 
public class VueCaisse extends VueVente implements VenteEcouteur, FocusListener, ActionListener
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
	private JPanel panIdQuantite, panLibellePrix, panAnnulerAjouter, panInfo, panTotal, panFinVente;

	/**
	 * Les boutons de la vue.
	 */
	private JButton boutonAnnuler, boutonAjouter, boutonFinVente;

	/**
	 * Les champs texte de la vue.
	 */
	private JTextField texteId, texteLibelle, textePrix, texteInfo, texteTotal;

	/**
	 * Les champs texte formaté de la vue.
	 */
	private JFormattedTextField texteQuantite;


	/**
	 * Constructeur de la vue.
	 *
	 * @param controleur le contrôleur du programme.
	 *
	 * @see monix.controleur.VueVente
	 */
	public VueCaisse(ControleurVente controleur)
	{
		super(controleur);

		this.fenetre = new Fenetre(
			Integer.parseInt(Monix.CONFIGURATION.getString("CAISSE_FENETRE_LARGEUR")),
			Integer.parseInt(Monix.CONFIGURATION.getString("CAISSE_FENETRE_HAUTEUR")),
			Monix.MESSAGES.getString("CAISSE_FENETRE_TITRE")
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

		this.panIdQuantite = new JPanel();
		this.contenu.add(this.panIdQuantite);

		this.panLibellePrix = new JPanel();
		this.contenu.add(this.panLibellePrix);

		this.panAnnulerAjouter = new JPanel();
		this.contenu.add(this.panAnnulerAjouter);

		this.panInfo = new JPanel(); 
		this.contenu.add(this.panInfo);

		this.panTotal = new JPanel(); 
		this.contenu.add(this.panTotal);

		this.panFinVente = new JPanel(); 
		this.contenu.add(this.panFinVente);
	}

	/**
	 * Construire les widgets de contrôle de la vue.
	 *
	 */
	private void construireControles()
	{
		/* Saisie de l'identifiant du produit. */	
		this.texteId = new JTextField(Monix.MESSAGES.getString("CAISSE_TEXTE_ID_PRODUIT"), 
				Integer.parseInt(Monix.CONFIGURATION.getString("CAISSE_TAILLE_SAISIE_ID_PRODUIT")));
		this.texteId.setEditable(true);
		this.texteId.requestFocus();
		this.texteId.addFocusListener(this);
		this.panIdQuantite.add(this.texteId);
		
		/* Saisie de la quantité du produit. */
		this.texteQuantite = new JFormattedTextField(NumberFormat.getIntegerInstance());
		this.texteQuantite.setValue(Integer.parseInt(Monix.MESSAGES.getString("CAISSE_TEXTE_QUANTITE")));
		this.texteQuantite.setColumns(
				Integer.parseInt(Monix.CONFIGURATION.getString("CAISSE_TAILLE_SAISIE_QUANTITE_PRODUIT")));
		this.texteQuantite.setEditable(true);
		this.panIdQuantite.add(this.texteQuantite);

		/* Affichage du libellé du produit. */
		this.texteLibelle = new JTextField(Monix.MESSAGES.getString("CAISSE_TEXTE_LIBELLE"), 
				Integer.parseInt(Monix.CONFIGURATION.getString("CAISSE_TAILLE_AFFICHAGE_LIBELLE_PRODUIT")));
		this.texteLibelle.setEditable(false);
		this.texteLibelle.setFocusable(false);
		this.panLibellePrix.add(this.texteLibelle);

		/* Affichage du prix du produit. */
		this.textePrix = new JTextField(Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX"), 
				Integer.parseInt(Monix.CONFIGURATION.getString("CAISSE_TAILLE_AFFICHAGE_PRIX_PRODUIT")));
		this.textePrix.setHorizontalAlignment(JTextField.RIGHT);
		this.textePrix.setEditable(false);
		this.textePrix.setFocusable(false);
		this.panLibellePrix.add(this.textePrix);

		/* Bouton d'ajout du produit dans la vente. */
		this.boutonAjouter = new JButton(Monix.MESSAGES.getString("CAISSE_BOUTON_AJOUTER"));
		this.boutonAjouter.addActionListener(this);
		this.panAnnulerAjouter.add(this.boutonAjouter);

		/* Bouton d'annulation du produit de la vente. */
		this.boutonAnnuler = new JButton(Monix.MESSAGES.getString("CAISSE_BOUTON_ANNULER"));
		this.boutonAnnuler.addActionListener(this);
		this.panAnnulerAjouter.add(this.boutonAnnuler);

		/* Affichage des informations sur l'achat en cours. */
		this.texteInfo = new JTextField(Monix.MESSAGES.getString("CAISSE_TEXTE_INFO"), 
				Integer.parseInt(Monix.CONFIGURATION.getString("CAISSE_TAILLE_AFFICHAGE_INFO_PRODUIT")));
		this.texteInfo.setEditable(false);
		this.texteInfo.setFocusable(false);
		this.panInfo.add(this.texteInfo);

		/* Affichage du prix total de la vente. */
		this.texteTotal = new JTextField(Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX_TOTAL"),
				Integer.parseInt(Monix.CONFIGURATION.getString("CAISSE_TAILLE_AFFICHAGE_PRIX_TOTAL")));
		this.texteTotal.setHorizontalAlignment(JTextField.RIGHT);
		this.texteTotal.setEditable(false);
		this.texteTotal.setFocusable(false);
		this.panTotal.add(this.texteTotal);
		
		/* Bouton de clôture de la vente. */
		this.boutonFinVente = new JButton(Monix.MESSAGES.getString("CAISSE_BOUTON_FIN_VENTE"));
		this.boutonFinVente.addActionListener(this);
		this.panFinVente.add(this.boutonFinVente);
	}

	/** 
	 * Initialiser les champs id, quantité, libellé et prix, et donner le focus au champs id.
	 *
	 */
	private void initialiseIdQuantiteLibellePrix()
	{
		this.texteId.setText("");
		this.texteQuantite.setValue(Integer.parseInt(Monix.MESSAGES.getString("CAISSE_TEXTE_QUANTITE")));
		this.texteLibelle.setText(Monix.MESSAGES.getString("CAISSE_TEXTE_LIBELLE"));
		this.textePrix.setText(Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX"));
		this.texteId.requestFocus();
	}


	/**
	 * Vérifier l'existence d'un produit dans le stock, à partir de son id, 
	 * à la suite de la perte du focus du champ id.
	 *
	 * @param ev un évènement de focus.
	 *
	 * @see java.awt.event.FocusListener
	 */
	@Override
	public void focusLost(FocusEvent ev)
	{
		if ((ev.getSource() == this.texteId) && !ev.isTemporary()) {
			final Produit produit = donneStock().donneProduit(this.texteId.getText().trim());

			if (produit == null) {
				/* Aucun produit ne correspond au libellé. */
				this.texteLibelle.setText(Monix.MESSAGES.getString("CAISSE_TEXTE_LIBELLE_INVALIDE"));
				this.textePrix.setText(Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX"));
			} else {
				/* Un produit correspond au libellé. */
				this.texteLibelle.setText(produit.donneLibelle().trim());
				this.textePrix.setText(String.format("%1$.2f €", produit.donnePrix()));
			}
		}
	}

	/**
	 * Gain du focus : non implémenté (car non utilisé).
	 *
	 * @param ev l'évènement considéré.
	 *
	 * @see java.awt.event.FocusListener
	 */
	@Override
	public void focusGained(FocusEvent ev)
	{
		/* ne rien faire */
	}

	/**
	 * Lance l'ajout ou l'annulation d'un produit dans une vente, ou la clôture d'une vente,
	 * suite à des actions sur des boutons de l'interface.
	 *
	 * @param ev un évènement d'action.
	 *
	 * @see java.awt.event.FocusListener
	 */
	@Override
	public void actionPerformed(ActionEvent ev)
	{
		try {
			if (ev.getSource() == this.boutonAjouter) {
				/* Ajout d'un produit. */
				donneVente().ajouteAchatProduit(
					this.texteId.getText().trim(), 
					((Number) this.texteQuantite.getValue()).intValue()
				);
			} else if (ev.getSource() == this.boutonAnnuler) {
				/* Annulation d'un produit. */
				donneVente().annuleAchatProduit(
					this.texteId.getText().trim(), 
					((Number) this.texteQuantite.getValue()).intValue()
				);
			} else if (ev.getSource() == this.boutonFinVente) {
				/* Clôture d'une vente. */
				donneVente().clotureVente();
			} else {
				/* Évènement inconnu. */
				System.err.println("Réception d'un évènement inconnu sur la caisse.");
			}
		} 
		catch (AchatImpossibleException exception) {
			/* Impossibilité d'effectuer un ajout ou une annulation de produit. */
			this.achatImpossible();
		}
		catch (NumberFormatException exception) {
	    	/* Impossibilité d'effectuer un ajout ou une annulation de produit. */
			this.achatImpossible();
		}
	}

	/**
	 * Avertir qu'une demande d'ajout ou d'annulation de produit est impossible.
	 *
	 */
	private void achatImpossible()
	{
		this.texteInfo.setText(Monix.MESSAGES.getString("CAISSE_TEXTE_INFO_ACHAT_IMPOSSIBLE"));
		this.texteId.requestFocus();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à l'ajout d'un achat dans le modèle de vente.
	 * Avertir dans l'IHM qu'un ajout a été effectué.
	 * </p>
	 * 
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	@Override
	public void venteAjouteAchat(VenteAchatEvenement ev)
	{
		this.texteInfo.setText("+ " + this.venteInfoAchat(ev));
		this.initialiseIdQuantiteLibellePrix();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à l'annulation d'un achat dans le modèle de vente.
	 * Avertir dans l'IHM qu'une annulation a été effectué.
	 * </p>
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	@Override
	public void venteAnnuleAchat(VenteAchatEvenement ev)
	{
		this.texteInfo.setText("- " + this.venteInfoAchat(ev));
		this.initialiseIdQuantiteLibellePrix();
	}

	/**
	 * Afficher des informations sur un achat.
	 *
	 * @param ev un évènement d'achat.
	 * @return les informations sur l'achat.
	 *
	 * @see monix.modele.vente.VenteAchatEvenement
	 *
	 */
	private String venteInfoAchat(VenteAchatEvenement ev)
	{
		final Produit produit = ev.donneProduit();
		final Integer quantite = ev.donneQuantite();

		return String.format("%1$s   x %2$3d  x %3$.2f €", produit.donneLibelle().trim(), quantite, produit.donnePrix());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à la modification d'une vente.
	 * Mettre à jour le prix total de la vente dans l'IHM.
	 * </p>
	 *
	 * @see monix.modele.vente.VenteEcouteur
	 * @see monix.modele.vente.VenteChangeEvenement
	 *
	 */
	@Override
	public void venteChange(VenteChangeEvenement ev)
	{
		this.texteTotal.setText(String.format("%1$.2f €", ev.donnePrix()));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * <p>
	 * Méthode appelée suite à la clôture d'une vente.
	 * Réinitialiser les champs de l'IHM.
	 * </p>
	 * 
	 * @see monix.modele.vente.VenteEcouteur
	 */
	@Override
	public void venteCloture()
	{
		this.texteTotal.setText(Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX_TOTAL"));
		this.texteInfo.setText(Monix.MESSAGES.getString("CAISSE_TEXTE_INFO"));
		this.initialiseIdQuantiteLibellePrix();
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
