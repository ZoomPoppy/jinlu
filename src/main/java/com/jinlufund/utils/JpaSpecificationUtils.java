package com.jinlufund.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class JpaSpecificationUtils {

	private static class SearchCondition {

		public enum Operator {
			EQ, LIKE, GT, LT, GTE, LTE, IN
		}

		public enum Type {
			STRING, INTEGER, DECIMAL, BOOLEAN, DATE
		}

		public String fieldName;
		public Operator operator;
		public Object value;

		public SearchCondition(String fieldName, Operator operator, Object value) {
			this.fieldName = fieldName;
			this.operator = operator;
			this.value = value;
		}
		
	}

	/**
	 * 创建用于动态查询的条件组合
	 * 
	 * @param searchParams
	 *            查询条件的Map，key是"{fieldName}_{operator}_{type}"的形式，value是用于比较的值，
	 *            只有String和List<String>两种类型
	 * @param entityClazz
	 *            查询实体的类名
	 * @return 生成的spring-data-jpa用于动态查询的Specification类实例
	 */
	public static <T> Specification<T> createSpecification(final Map<String, Object> searchParams,
			final Class<T> entityClazz) {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				// 1.把查询参数转换为SearchCondition列表
				List<SearchCondition> conditions = parseSearchCondition(searchParams);
				
				// 2.把SearchCondition列表转换为Predicate列表
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (SearchCondition condition : conditions) {
					Predicate predicate = generatePredicateWithSearchCondition(condition, root, builder);
					predicates.add(predicate);
				}
				
				// 3.组合Predicate
				if (!predicates.isEmpty()) {
					return builder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
				return builder.conjunction();
			}
		};
	}

	// 把查询参数的Map转换成内置SearchCondition对象的列表，输入Map的值只能是String或者List<String>类型
	@SuppressWarnings("unchecked")
	private static List<SearchCondition> parseSearchCondition(Map<String, Object> searchParams) {
		List<SearchCondition> conditions = new ArrayList<JpaSpecificationUtils.SearchCondition>();
		for (Entry<String, Object> entry : searchParams.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			String[] names = key.split("_");
			if (names.length != 3) {
				throw new IllegalArgumentException(key + " is not a valid search key name");
			}
			String fieldName = names[0];
			SearchCondition.Operator operator = SearchCondition.Operator.valueOf(names[1]);
			SearchCondition.Type type = SearchCondition.Type.valueOf(names[2]);
			
			// 对值进行类型转换
			Object parsed = null;
			if (value instanceof String) {
				String str = (String) value;
				parsed = parseObjectFromStringWithType(str, type);
			} else if (value instanceof List) {
				List<String> strList = (List<String>) value;
				List<Object> objList = new ArrayList<Object>();
				for (String str : strList) {
					Object obj = parseObjectFromStringWithType(str, type);
					objList.add(obj);
				}
				parsed = objList;
			}

			SearchCondition condition = new SearchCondition(fieldName, operator, parsed);
			conditions.add(condition);
		}
		return conditions;
	}
	
	private static Object parseObjectFromStringWithType(String valueString, SearchCondition.Type type) {
		Object valueParsed = null;
		if (type.equals(SearchCondition.Type.STRING)) {
			valueParsed = valueString;
		} else if (type.equals(SearchCondition.Type.INTEGER)) {
			valueParsed = Long.valueOf(valueString);
		} else if (type.equals(SearchCondition.Type.DECIMAL)) {
			valueParsed = new BigDecimal(valueString);
		} else if (type.equals(SearchCondition.Type.BOOLEAN)) {
			valueParsed = Boolean.valueOf(valueString);
		} else if (type.equals(SearchCondition.Type.DATE)) {
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");
			valueParsed = DateTime.parse(valueString, format).toDate();
		}
		return valueParsed;
	}
	
	// 用内置SearchCondition生成JPA用于查询的Predicate
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Predicate generatePredicateWithSearchCondition(SearchCondition condition, Root root, CriteriaBuilder builder) {
		Predicate predicate = null;
		
		String fieldName = condition.fieldName;
		SearchCondition.Operator operator = condition.operator;
		Object value = condition.value;
		
		// 把fieldName转换成expression
		String[] names = fieldName.split("\\.");
		Path expression = root.get(names[0]);
		for (int i = 1; i < names.length; i++) {
			expression = expression.get(names[i]);
		}
		
		// 生成predicate
		if (operator.equals(SearchCondition.Operator.EQ)) {
			predicate = builder.equal(expression, value);
		} else if (operator.equals(SearchCondition.Operator.LIKE)) {
			predicate = builder.like(expression, "%" + value + "%");
		} else if (operator.equals(SearchCondition.Operator.GT)) {
			predicate = builder.greaterThan(expression, (Comparable) value);
		} else if (operator.equals(SearchCondition.Operator.LT)) {
			predicate = builder.lessThan(expression, (Comparable) value);
		} else if (operator.equals(SearchCondition.Operator.GTE)) {
			predicate = builder.greaterThanOrEqualTo(expression, (Comparable) value);
		} else if (operator.equals(SearchCondition.Operator.LTE)) {
			predicate = builder.lessThanOrEqualTo(expression, (Comparable) value);
		} else if (operator.equals(SearchCondition.Operator.IN)) {
			if (!(value instanceof List)) {
				value = new ArrayList<Object>().add(value);
			}
			predicate = expression.in(value);
		}
		return predicate;
	}
	
	public static void main(String[] args) {
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("name_LIKE_STRING", "cao");
		searchParams.put("birthday_GT_DATETIME", "1986-07-28 00:00:00");
		searchParams.put("money_EQ_DECIMAL", "100.00");
		searchParams.put("status_IN_INTEGER", Arrays.asList("1", "2"));
		List<SearchCondition> conditions = parseSearchCondition(searchParams);
		System.out.println(conditions);
	}

}
