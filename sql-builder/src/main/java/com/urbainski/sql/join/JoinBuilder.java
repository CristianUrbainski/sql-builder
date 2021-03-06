package com.urbainski.sql.join;

import com.urbainski.sql.db.types.JoinDBType;

/**
 * Classe para gerar os join do queries.
 * 
 * @author Cristian Urbainski <cristianurbainskips@gmail.com>
 * @since 20/09/2014
 * @version 1.0
 *
 */
public class JoinBuilder {

	/**
	 * Método que constrio um join.
	 * 
	 * @param clazzFrom - entidade que esta saindo
	 * @param clazzJoined - entidade que está sendo unida
	 * @param fromAlias - alias do from
	 * @param joinedAlias - alias da entidade unida
	 * @param property - propriedade por onde será unido
	 * @param joinType - tipo do join
	 * 
	 * @return {@link Join}
	 */
	public static Join newJoin(Class<?> clazzFrom, Class<?> clazzJoined,
			String fromAlias, String joinedAlias, String property, JoinDBType joinType) {
		return new Join(clazzFrom, clazzJoined, fromAlias, joinedAlias, property, joinType);
	}
	
	/**
	 * Método que constrio um join.
	 * 
	 * @param clazzFrom - entidade que esta saindo
	 * @param clazzJoined - entidade que está sendo unida
	 * @param fromAlias - alias do from
	 * @param joinedAlias - alias da entidade unida
	 * @param property - propriedade por onde será unido
	 * 
	 * @return {@link Join}
	 */
	public static Join newJoin(Class<?> clazzFrom, Class<?> clazzJoined,
			String fromAlias, String joinedAlias, String property) {
		return new Join(clazzFrom, clazzJoined, fromAlias, joinedAlias, property);
	}
	
	/**
	 * Método usada para constuir join de uma classe de entidade para o seu super tipo.
	 * 
	 * @param clazzFrom - entidade que esta saindo
	 * @param fromAlias - alias do from
	 * 
	 * @return {@link Join}
	 */
	public static Join newJoin(Class<?> clazzFrom, String fromAlias) {
		return new Join(clazzFrom, clazzFrom.getSuperclass(), fromAlias, "", null);
	}
	
}