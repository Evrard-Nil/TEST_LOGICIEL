package felix.vue;

import javax.swing.JFrame;

/**
 * Classe de création d'une fenêtre.
 *
 * @version 3.0
 * @author Matthias Brun 
 *
 * @see javax.swing.JFrame
 *
 */
public class Fenetre extends JFrame
{
	/**
	 * UID auto-généré.
	 */
	private static final long serialVersionUID = -7335860982827533031L;

	/**
	 * Constructeur d'une fenêtre.
	 *
	 * @param largeur largeur de la fenêtre. 
	 * @param hauteur hauteur de la fenêtre.
	 * @param titre libellé de la fenêtre affiché dans la barre de titre.
	 *
	 */
	public Fenetre(Integer largeur, Integer hauteur, String titre)
	{
		super();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(largeur, hauteur);

		setTitle(titre);

		setResizable(true);
	}
}
