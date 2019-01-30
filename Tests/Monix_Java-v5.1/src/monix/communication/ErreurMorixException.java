package monix.communication;

/**
 * Classe d'exception d'une erreur sur le serveur Morix.
 *
 * @version 3.0
 * @author Matthias Brun
 *
 * @see java.lang.Exception
 *
 */
public class ErreurMorixException extends Exception
{
	/**
	 * Version UID auto-générée.
	 */
	private static final long serialVersionUID = 5297915910833859543L;

	/**
	 * Constructeur d'exception d'une erreur sur Morix.
	 *
	 * @param message le message qui accompagne l'erreur.
	 */
	public ErreurMorixException(String message)
	{
		super(message);
	}

}
