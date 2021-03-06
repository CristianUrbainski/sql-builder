package com.urbainski.sql.db.types;

/**
 * Enum para os tipos de ordenação possivel no banco de dados.
 * 
 * @author Cristian Urbainski <cristianurbainskips@gmail.com>
 * @since 23/09/2014
 * @version 1.0
 *
 */
public enum OrderByDBTypes {

	/**
	 * Ordenação asc.
	 */
	ASC("asc"),
	
	/**
	 * Ordenação desc.
	 */
	DESC("desc");
	
	/**
	 * Nome do order by.
	 */
	private String name;
	
	/**
	 * Construtor do tipo de orderby.
	 * 
	 * @param name - tipo do orderby
	 */
	private OrderByDBTypes(String name) {
		this.name = name;
	}
	
	/**
	 * Pega o nome do order by.
	 * 
	 * @return nome do order by
	 */
	public String getOrderByType() {
		return this.name;
	}
	
}