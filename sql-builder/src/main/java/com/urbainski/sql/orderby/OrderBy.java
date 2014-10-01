package com.urbainski.sql.orderby;

import static com.urbainski.sql.db.types.SQLSelectDBTypes.ORDER_BY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.urbainski.sql.builder.SQL;
import com.urbainski.sql.db.types.OrderByDBTypes;
import com.urbainski.sql.field.Field;
import com.urbainski.sql.field.impl.SimpleField;

/**
 * Classe que representa o orderby da query.
 * 
 * @author Cristian Urbainski <cristianurbainskips@gmail.com>
 * @since 23/09/2014
 * @version 1.0
 *
 */
public class OrderBy implements SQL {
	
	/**
	 * Lista de campos.
	 */
	protected List<Field> fields;
	
	/**
	 * Tipo do order by.
	 */
	protected OrderByDBTypes orderByType;

	public void setOrderByType(OrderByDBTypes orderByType) {
		this.orderByType = orderByType;
	}
	
	/**
	 * Construtor padrão.
	 * 
	 * @param type - tipo do order by
	 */
	public OrderBy(OrderByDBTypes type) {
		this.orderByType = type;
		this.fields = new ArrayList<Field>();
	}
	
	/**
	 * Método que adiciona um campo na consulta.
	 * 
	 * @param fields - campos
	 */
	public void addField(Field... fields) {
		this.fields.addAll(Arrays.asList(fields));
	}
	
	/**
	 * Método que adiciona um campo na consulta.
	 * 
	 * @param f - campo
	 */
	public void addField(Field f) {
		this.fields.add(f);
	}
	
	@Override
	public String buildSQL() {
		final StringBuilder sql = new StringBuilder();
		sql.append(ORDER_BY.getSQLSelectType());
		sql.append(" ");
		
		for (Field f : fields) {
			final SimpleField simpleField = (SimpleField) f;
			
			sql.append(simpleField.getTableNameOrAlias() + ".");
			sql.append(simpleField.getFieldName());
			
			if (fields.indexOf(f) < (fields.size() - 1)) {
				sql.append(", ");
			}
		}

		sql.append(" ");
		sql.append(orderByType.getOrderByType());
		return sql.toString();
	}

}