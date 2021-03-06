package com.urbainski.sql.db.types;

/**
 * Tipo de condições no banco de dados.
 * 
 * @author Cristian Urbainski <cristianurbainskips@gmail.com>
 * @since 20/09/2014
 * @version 1.0
 *
 */
public enum ConditionDBTypes {
	
	/**
	 * Condição &&.
	 */
	AND("and"),
	
	/**
	 * Condição ||.
	 */
	OR("or"),

	/**
	 * Condição valor in.
	 */
	IN("in"),
	
	/**
	 * Condição valor não está em.
	 */
	NOT_IN("not in"),

	/**
	 * Condição valor igual.
	 */
	EQUALS("="),
	
	/**
	 * Codição valor diferente de.
	 */
	DIFFERENT("<>"),
	
	/**
	 * Valor está entre.
	 */
	BETWEEN("between"),
	
	/**
	 * Valor do like.
	 */
	LIKE("like"),
	
	/**
	 * Valor do ilike.
	 */
	ILIKE("ilike");
	
	/**
	 * Nome.
	 */
	private String name;
	
	/**
	 * Construtor dos tipos de condições.
	 * 
	 * @param name - condição
	 */
	private ConditionDBTypes(String name) {
		this.name = name;
	}
	
	public String getConditionType() {
		return name;
	}
	
}