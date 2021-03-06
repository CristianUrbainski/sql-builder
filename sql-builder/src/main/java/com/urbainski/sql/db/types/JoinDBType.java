package com.urbainski.sql.db.types;

/**
 * Tipo de join posiveis.
 * 
 * @author Cristian Urbainski <cristianurbainskips@gmail.com>
 * @since 20/09/2014
 * @version 1.0
 *
 */
public enum JoinDBType {

	/**
	 * Join do tipo inner.
	 */
	INNER("inner join"),
	
	/**
	 * Join do tipo left.
	 */
	LEFT("left join"),
	
	/**
	 * Join do tipo rigth.
	 */
	RIGHT("right join");
	
	/**
	 * Nome.
	 */
	private String name;
	
	/**
	 * Construtor do tipo de join.
	 * 
	 * @param name - nome do join
	 */
	private JoinDBType(String name) {
		this.name = name;
	}
	
	public String getJoinType() {
		return name;
	}
	
}