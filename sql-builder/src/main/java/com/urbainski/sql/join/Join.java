package com.urbainski.sql.join;

import static com.urbainski.sql.reflection.TableReflectionReader.getJoinInformation;
import static com.urbainski.sql.reflection.TableReflectionReader.getTableName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.urbainski.sql.builder.SQL;
import com.urbainski.sql.condititon.Condition;
import com.urbainski.sql.condititon.impl.ConditionBuilder;
import com.urbainski.sql.condititon.impl.JoinCondition;
import com.urbainski.sql.db.types.ConditionDBTypes;
import com.urbainski.sql.db.types.JoinDBType;
import com.urbainski.sql.select.Select;

/**
 * Classe que representa o join nas queries.
 * 
 * @author Cristian Urbainski <cristianurbainskips@gmail.com>
 * @since 20/09/2014
 * @version 1.0
 *
 */
public class Join implements SQL {

	/**
	 * Lista de condições do join.
	 */
	protected List<Condition> conditions;
	
	/**
	 * Tipo do join.
	 */
	protected JoinDBType joinType;
	
	/**
	 * De onde o join esta sendo feito.
	 */
	protected Class<?> clazzFrom;
	
	/**
	 * Alias da classe do from.
	 */
	protected String fromAlias;
	
	/**
	 * Alias da classe que foi feito o join.
	 */
	protected String joinedAlias;
	
	/**
	 * Com quem o join esta sendo unido.
	 */
	protected Class<?> clazzJoined;
	
	/**
	 * Nome da propriedade.
	 */
	protected String property;
	
	public Class<?> getClazzFrom() {
		return clazzFrom;
	}
	
	public Class<?> getClazzJoined() {
		return clazzJoined;
	}
	
	public String getFromAlias() {
		return fromAlias;
	}
	
	public String getJoinedAlias() {
		return joinedAlias;
	}
	
	/**
	 * Construtor da classe.
	 * 
	 * @param clazzFrom - classe de onde sai o relacionamento
	 * @param clazzJoined - classe a ser unida
	 * @param fromAlias - alias da classe do from
	 * @param joinedAlias - alias da classe unida
	 * @param property - nome da propriedade
	 */
	public Join(Class<?> clazzFrom, Class<?> clazzJoined, 
			String fromAlias, String joinedAlias, String property) {
		this(clazzFrom, clazzJoined, fromAlias, joinedAlias, property, JoinDBType.INNER);
	}
	
	/**
	 * Construtor da classe.
	 * 
	 * @param clazzFrom - classe de onde sai o relacionamento
	 * @param clazzJoined - classe a ser unida
	 * @param fromAlias - alias da classe do from
	 * @param joinedAlias - alias da classe unida
	 * @param joinType - tipo do join
	 * @param property - nome da propriedade
	 */
	public Join(Class<?> clazzFrom, Class<?> clazzJoined, 
			String fromAlias, String joinedAlias, String property, JoinDBType joinType) {
		this.clazzFrom = clazzFrom;
		this.clazzJoined = clazzJoined;
		this.fromAlias = fromAlias;
		this.joinedAlias = joinedAlias;
		this.property = property;
		this.joinType = joinType;
		this.conditions = new ArrayList<Condition>();
	}
	
	/**
	 * Método para adicionar uma nova condição.
	 * 
	 * @param condition - condição
	 */
	public void addCondition(Condition condition) {
		this.conditions.add(condition);
	}
	
	/**
	 * Método para adicionar uma série de condições.
	 * 
	 * @param condition - condições 
	 */
	public void addCondition(Condition... condition) {
		this.conditions.addAll(Arrays.asList(condition));
	}
	
	/**
	 * Método que seta o alias do from.
	 * 
	 * @param fromAlias - alias da classe from
	 */
	public void fromAlias(String fromAlias) {
		this.fromAlias = fromAlias;
	}
	
	/**
	 * Método para criar um condição 'on' no join.
	 * 
	 * @param conditionType - tipo da condição
	 * @param prop1 - nome da propriedade 1 
	 * @param prop2 - nome da propriedade 2 
	 */
	public void addOn(ConditionDBTypes conditionType, String prop1, String prop2) {
		final JoinCondition joinCondition = ConditionBuilder.newJoinCondition(
				clazzFrom, fromAlias, clazzJoined, joinedAlias, conditionType, prop1, prop2);
		
		final List<Condition> newConditions = new ArrayList<Condition>();
		for (Condition c : this.conditions) {
			boolean jaAdicionouNovaCondicao = false;
			if (c instanceof JoinCondition) {
				newConditions.add(c);
			} else {
				if (jaAdicionouNovaCondicao) {
					jaAdicionouNovaCondicao = true;
					
					newConditions.add(joinCondition);
				}
				newConditions.add(c);
			}
		}
	}
	
	@Override
	public String buildSQL() {
		final StringBuilder sql = new StringBuilder();
		sql.append(joinType.getJoinType());
		sql.append(" ");
		sql.append(getTableName(clazzJoined));
		
		if (joinedAlias != null && !(joinedAlias.isEmpty())) {
			sql.append(" " + joinedAlias);
		}
			
		sql.append(" on ");
		
		if (conditions.isEmpty() || !(hasJoinCondition())) {
			JoinCondition joinCondition = null;
			if (property == null || property.isEmpty()) {
				joinCondition = getJoinInformation(clazzFrom, fromAlias, joinedAlias);
			} else {
				joinCondition = getJoinInformation(clazzFrom, fromAlias, joinedAlias, property);
			}
			sql.append(joinCondition.buildSQL());
			
			if (!(conditions.isEmpty())) {
				sql.append(" ");
				sql.append(ConditionDBTypes.AND.getConditionType());
				sql.append(" ");
			}
 		} 
		
		for (Condition c : conditions) {
			if (conditions.indexOf(c) > 0) {
				sql.append(" ");
				sql.append(ConditionDBTypes.AND.getConditionType());
				sql.append(" ");
			}

			sql.append(c.buildSQL());
		}
		
		return sql.toString();
	}

	/**
	 * Método para verificar se existe um {@link JoinCondition} na lista.
	 * 
	 * @return <code>true</code> se existir {@link JoinCondition}
	 * 	na lista caso contrário <code>false</code>
	 */
	private boolean hasJoinCondition() {
		for (Condition c : conditions) {
			if (c instanceof JoinCondition) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método responsável por criar um {@link Select} com base no join.
	 * 
	 * @return {@link Select}
	 */
	public Select builSelect() {
		Select select = new Select(clazzJoined);
		select.alias(joinedAlias);
		return select;
	}

}
