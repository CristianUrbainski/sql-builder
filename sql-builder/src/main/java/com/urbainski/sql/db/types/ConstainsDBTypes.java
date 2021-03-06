package com.urbainski.sql.db.types;

/**
 * Classe de enum para as condições de 'contains' no banco de dados.
 * 
 * @author Cristian Urbainski <cristianurbainskips@gmail.com>
 * @since 21/09/2014
 * @version 1.0 
 *
 */
public enum ConstainsDBTypes {

	/**
	 * Qualquer coisa no começo.
	 */
	IN_START,
	
	/**
	 * Em qualquer lugar da frase.
	 */
	ANY,
	
	/**
	 * No fim da frase.
	 */
	IN_FINISH;
	
}