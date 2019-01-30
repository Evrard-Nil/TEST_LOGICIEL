package hello;

/**
 * Classe HelloJUnit. 
 * 
 * <p>Support d'exemples pour l'utilisation de JUnit.</p>
 * 
 * @version 1.0
 * @author Matthias Brun
 * 
 */
public class HelloJUnit 
{
	/**
	 * Mention de l'accès publique.
	 */
	public static final String PUBLIC = "PUBLIC";
	
	/**
	 * Séparateur de modification.
	 */
	public static final String SEP_MODIFICATION = " > ";
	
	/**
	 * Le message à afficher au monde.
	 */
	private String message;
	
	/**
	 * Un message personnel (qui ne se destine pas au monde).
	 * <p>Message sans accesseur.</p>
	 */
	@SuppressWarnings("unused")
	private String messageCache;
	
	/**
	 * Accesseur au message à afficher au monde.
	 */
	public String donneMessage()
	{
		return this.message;
	}
	
	/**
	 * Constructeur de message à afficher au monde.
	 * 
	 * @param message le message à afficher au monde.
	 */
	public HelloJUnit(String message)
	{
		this.message = message;
	}
	
	/**
	 * Donner un message modifié.
	 * 
	 * @param modification la modification à suffixer au message.
	 * @return le message modifié.
	 */
	private String donneMessageModifie(String modification)
	{
		return (this.message + HelloJUnit.SEP_MODIFICATION + modification);
	}
	
	/**
	 * Fixer un message modifié (à afficher au monde).
	 * 
	 * @param modification la modification à suffixer au message.
	 */
	private void fixeMessageModifie(String modification)
	{
		this.message = donneMessageModifie(modification);
	}
	
	/**
	 * Fixer un message modifié (à afficher au monde).
	 * 
	 * <p>L'aspet publique de la modification est préfixé au message.</p>
	 * 
	 * @param modification la modification à suffixer au message.
	 */
	public void fixeMessageModifiePublic(String modification)
	{
		fixeMessageModifie(HelloJUnit.PUBLIC + HelloJUnit.SEP_MODIFICATION + modification);
	}
	
	/**
	 * Fixer un message caché.
	 * 
	 * @param message le message caché à fixer.
	 */
	public void fixeMessageCache(String message)
	{
		this.messageCache = message;
	}


}
