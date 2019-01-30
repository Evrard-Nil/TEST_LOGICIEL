package monix.modele.vente;

/**
 * Classe d'exception d'achat impossible lors d'une vente.
 *
 * @version 3.0
 * @author Matthias Brun
 *
 * @see java.lang.Exception
 * @see monix.modele.vente.Vente
 *
 */
public class AchatImpossibleException extends Exception
{
	/**
	 * UID auto-généré.
	 */
	private static final long serialVersionUID = -7646443321560363449L;

	/**
	 * Constructeur d'exception d'achat impossible.
	 *
	 */
	public AchatImpossibleException()
	{
		super();
	}

}
