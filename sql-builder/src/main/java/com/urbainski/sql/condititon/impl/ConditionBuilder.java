package com.urbainski.sql.condititon.impl;

import java.util.List;

import com.urbainski.sql.builder.SelectBuilder;
import com.urbainski.sql.condititon.Condition;
import com.urbainski.sql.db.types.ConditionDBTypes;
import com.urbainski.sql.db.types.ConstainsDBTypes;
import com.urbainski.sql.util.Assert;

/**
 * Classe para gerar conditions para os sql.
 * 
 * @author Cristian Urbainski <cristianurbainskips@gmail.com>
 * @since 20/09/2014
 * @version 1.0
 *
 */
public final class ConditionBuilder {
	
	/**
	 * Construtor privado para que a classe
	 * não possa ser instanciada.
	 */
	private ConditionBuilder() {
		
	}

	/**
	 * Método para construir condições para as queries.
	 * 
	 * @param entityClass - classe de entidade
	 * @param type - tipo da condição
	 * @param fieldName - nome do campo
	 * @param value - valor do campo
	 * 
	 * @return {@link Condition}
	 */
	public static Condition newCondition(Class<?> entityClass, 
			ConditionDBTypes type, String fieldName, Object... value) {
		return newCondition(entityClass, "", type, fieldName, value);
	}
	
	/**
	 * Método para construir condições para as queries.
	 * 
	 * @param entityClass - classe de entidade
	 * @param tableFromAlias - alias do from
	 * @param type - tipo da condição
	 * @param fieldName - nome do campo
	 * @param value - valor do campo
	 * 
	 * @return {@link Condition}
	 */
	public static Condition newCondition(Class<?> entityClass, String tableFromAlias,
			ConditionDBTypes type, String fieldName, Object... value) {
		
		Assert.parameterNotNull(type, "Tipo da condição deve ser informada");
		Assert.parameterNotNullAndNotEmpty(fieldName, "Nome do campo deve ser informada");
		Assert.parameterNotNull(value, "Argumento 'value' deve ser informado");
		
		if (ConditionDBTypes.EQUALS.equals(type) 
				|| ConditionDBTypes.DIFFERENT.equals(type)
				|| ConditionDBTypes.IN.equals(type)
				|| ConditionDBTypes.NOT_IN.equals(type)) {
			
			if (ConditionDBTypes.IN.equals(type)
				|| ConditionDBTypes.NOT_IN.equals(type)) {

				if (!(value[0] instanceof List<?>)) {
					throw new IllegalArgumentException(
							"Parâmetro 'value' para as condições 'IN' e 'NOT IN' dever ser do tipo List");
				}
			}
			return new SimpleCondition(
					entityClass, tableFromAlias, type, fieldName, value[0]);
		} else if (ConditionDBTypes.BETWEEN.equals(type)) {
			if (value.length < 2) {
				throw new IllegalArgumentException("Para condição de 'between' é esperado dos values");
			}
			return new BetweenCondition(
					entityClass, tableFromAlias, fieldName, value[0], value[1]);
		} else if (ConditionDBTypes.AND.equals(type)
				|| ConditionDBTypes.OR.equals(type)) {
			throw new IllegalArgumentException(
					"Para condições do tipo 'AND' e 'OR' chame o método 'newCondition(ConditionDBTypes,Conditition)'");
		}
		return null;
	}
	
	/**
	 * Método para construir condições para as querys.
	 * 
	 * @param entityClass - classe de entidade
	 * @param tableFromAlias - alias do from
	 * @param containsType - tipo da condição contains
	 * @param type - tipo da condição
	 * @param fieldName - nome do campo
	 * @param value - valor do campo
	 * 
	 * @return {@link Condition}
	 */
	public static Condition newCondition(Class<?> entityClass, String tableFromAlias,
			ConstainsDBTypes containsType, ConditionDBTypes type, 
			String fieldName, Object value) {
		
		if (ConditionDBTypes.LIKE.equals(type)
				|| ConditionDBTypes.ILIKE.equals(type)) {
			return new ConstainsCondition(containsType, tableFromAlias, entityClass, type, fieldName, value);
		} else {
			throw new IllegalArgumentException(
					"Este método deve ser usado apenas condições 'LIKE' e 'ILIKE''");
		}
	}
	
	/**
	 * Método que construi condições.
	 * 
	 * @param type - tipo da condição
	 * @param conditions - demais condições
	 * 
	 * @return {@link Condition}
	 */
	public static Condition newCondition(
			ConditionDBTypes type, Condition... conditions) {
		
		if (ConditionDBTypes.AND.equals(type)
				|| ConditionDBTypes.OR.equals(type)) {
			return new BooleanCondition(type, conditions);
		} else {
			throw new IllegalArgumentException(
					"Este método deve receber um 'ConditionDBTypes' do tipo 'AND' ou 'OR'");
		}
	}
	
	/**
	 * Método para criar condições para o join.
	 * 
	 * @param entityFrom - classe do from
	 * @param fromAlias - alias da classe do from
	 * @param joinedClass - classe do join
	 * @param joinedAlias - alias da classe do join
	 * @param conditionType - tipo de condição
	 * @param prop1 - propriedade 1
	 * @param prop2 - propriedade 2
	 * 
	 * @return {@link Condition}
	 */
	public static JoinCondition newJoinCondition(
			Class<?> entityFrom, String fromAlias,
			Class<?> joinedClass, String joinedAlias,
			ConditionDBTypes conditionType, String prop1, String prop2) {
		
		return new JoinCondition(entityFrom, fromAlias, joinedClass, 
				joinedAlias, conditionType, prop1, prop2);
	}
	
	/**
	 * Método para construir um {@link SubselectCondition}.
	 * 
	 * @param entityClass - classe de entidade
	 * @param aliasTable - alias da tabela
	 * @param fieldName - nome do campo
	 * @param conditionType - tipo da condição
	 * @param subselect - query do subselect
	 * 
	 * @return {@link SubselectCondition}
	 */
	public static SubselectCondition newSubselectCondition(
			Class<?> entityClass, String aliasTable, String fieldName, 
			ConditionDBTypes conditionType, SelectBuilder subselect) {

		return new SubselectCondition(
				entityClass, aliasTable, fieldName, conditionType, subselect);
	}
	
	/**
	 * Método para atualizar o alias da entidade na consulta.
	 * 
	 * @param condition - condição que tera o alias atualizado
	 * @param entity - entidade que teve o alias mudado
	 * @param newAlias - novo alias da entidade
	 */
	public static void updateAliasForCondition(Condition condition, Class<?> entity, String newAlias) {
		
		if (condition instanceof SimpleCondition
				|| condition instanceof BetweenCondition
				|| condition instanceof ConstainsCondition
				|| condition instanceof SubselectCondition) {
			
			final SimpleCondition simpleCondition = ((SimpleCondition) condition);
			if (entity.equals(simpleCondition.entityClass)) {
				simpleCondition.aliasTable(newAlias);
			}
		} else if (condition instanceof BooleanCondition) {
			((BooleanCondition) condition).updateFromAlias(entity, newAlias);
		}
	}
	
}