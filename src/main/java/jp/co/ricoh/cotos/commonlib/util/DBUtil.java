package jp.co.ricoh.cotos.commonlib.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.github.mustachejava.util.DecoratedCollection;

import lombok.AllArgsConstructor;

/**
 * ネイティブクエリ発行用ユーティリティー<br/>
 * クエリビルダにはMustacheを利用。ただし、SQLインジェクションを避けるためにパラメータ部分にMustacheを利用してはいけません。
 */
@AllArgsConstructor
public class DBUtil {
	EntityManager entityManager;

	/**
	 * クラスパス上のファイルからSQLを生成します。
	 *
	 * @param sqlFilePathOnClasspath
	 * @param responseClass
	 * @return
	 */
	public <T> List<T> loadFromSQLFile(String sqlFilePathOnClasspath, Class<T> responseClass) {
		return loadFromSQLFile(sqlFilePathOnClasspath, responseClass, new HashMap<>());
	}

	/**
	 * クラスパス上のファイルからSQLを生成します。
	 *
	 * @param sqlFilePathOnClasspath
	 * @param responseClass
	 * @param params
	 * @return
	 */
	public <T> List<T> loadFromSQLFile(String sqlFilePathOnClasspath, Class<T> responseClass, Map<String, Object> params) {
		Query query = createQuery(sqlFilePathOnClasspath, responseClass, params, false);
		@SuppressWarnings("unchecked")
		List<T> results = query.getResultList();
		return results;
	}

	/**
	 * クラスパス上のファイルから一件検索用SQLを生成します。
	 *
	 * @param sqlFilePathOnClasspath
	 * @param responseClass
	 * @return
	 */
	public <T> T loadSingleFromSQLFile(String sqlFilePathOnClasspath, Class<T> responseClass) {
		return loadSingleFromSQLFile(sqlFilePathOnClasspath, responseClass, new HashMap<>());
	}

	/**
	 * クラスパス上のファイルから一件検索用SQLを生成します。
	 *
	 * @param sqlFilePathOnClasspath
	 * @param responseClass
	 * @param params
	 * @return
	 */
	public <T> T loadSingleFromSQLFile(String sqlFilePathOnClasspath, Class<T> responseClass, Map<String, Object> params) {
		Query query = createQuery(sqlFilePathOnClasspath, responseClass, params, false);
		try {
			@SuppressWarnings("unchecked")
			T results = (T) query.getSingleResult();
			return results;
		} catch (NoResultException e) {
			return null;
		}
	}

	private <T> Query createQuery(String sqlFilePathOnClasspath, Class<T> responseClass, Map<String, Object> params, boolean isCount) {
		Map<String, Object> paramForMustacheIndex = params.entrySet().stream().map(e -> {
			if (e.getValue() instanceof Collection<?>) {
				return new MappedEntry<String, Object>(e.getKey(), new DecoratedCollection<>((Collection<?>) e.getValue()));
			}
			return e;
		}).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		String sql = loadSQLFromClasspath(sqlFilePathOnClasspath, paramForMustacheIndex);
		// CountするSQLを生成
		if(isCount) {
			sql = "select count(*) from (" + sql + ")";
		}
		Query query = null != responseClass ? entityManager.createNativeQuery(sql, responseClass) : entityManager.createNativeQuery(sql);
		params.entrySet().stream().filter(e -> null != e.getValue()).map(e -> {
			if ((e.getValue() instanceof Collection<?>)) {
				return openCollectionValueWithIndexedKeyEntry(e);
			} else {
				return Arrays.asList(e);
			}
		}).flatMap(l -> l.stream()).forEach(entry -> {
			try {
				query.setParameter(entry.getKey(), this.escapeLikeKeyword(entry.getKey(), entry.getValue()));
			} catch (IllegalArgumentException e) {
				// SQL内でパラメータのvalueを使用しないパターンを考慮し、そのまま処理を継続する。
			}
		});

		return query;
	}

	private Object escapeLikeKeyword(String key, Object value) {

		// 検索値がStringの場合のみエスケープ
		if (value instanceof String) {

			String likeKeywordValue = String.valueOf(value);

			// 部分一致検索の項目に対して、エスケープ処理を実施
			if (key.startsWith("likeSearch")) {
				return likeKeywordValue.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
			}
		}

		return value;
	}

	private static class MappedEntry<K, V> implements Entry<K, V> {
		private K key;
		private V value;

		public MappedEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			this.value = value;
			return value;
		}

	}

	private static List<Entry<String, Object>> openCollectionValueWithIndexedKeyEntry(final Entry<String, Object> entry) {
		List<Entry<String, Object>> returnList = new ArrayList<>();
		for (final Object o : (Iterable<?>) entry.getValue()) {
			returnList.add(new MappedEntry<String, Object>(entry.getKey() + "" + returnList.size(), o));
		}
		return returnList;
	}

	/**
	 * クラスパス上のファイルからSQLを取得します。
	 *
	 * @param sqlFilePathOnClasspath
	 * @param params
	 * @return
	 */
	public String loadSQLFromClasspath(String sqlFilePathOnClasspath, Map<String, Object> params) {
		try {
			MustacheFactory mf = new DefaultMustacheFactory();
			Mustache mustache = mf.compile(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(sqlFilePathOnClasspath), Charset.forName("UTF-8")), sqlFilePathOnClasspath);
			StringWriter out = new StringWriter();
			mustache.execute(out, params).flush();
			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * クラスパス上のファイルからSQLを取得します。
	 *
	 * @param sqlFilePathOnClasspath
	 * @return
	 */
	public String loadSQLFromClasspath(String sqlFilePathOnClasspath) {
		try {
			MustacheFactory mf = new DefaultMustacheFactory();
			Mustache mustache = mf.compile(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(sqlFilePathOnClasspath), Charset.forName("UTF-8")), sqlFilePathOnClasspath);
			StringWriter out = new StringWriter();
			mustache.execute(out, Collections.emptyMap()).flush();
			return out.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * クラスパス上のファイルから件数取得用SQLを生成します。
	 *
	 * @param sqlFilePathOnClasspath
	 * @return
	 */
	public long loadCountFromSQLFile(String sqlFilePathOnClasspath) {
		return loadCountFromSQLFile(sqlFilePathOnClasspath, Collections.emptyMap());
	}

	/**
	 * クラスパス上のファイルから件数取得用SQLを生成します。
	 *
	 * @param sqlFilePathOnClasspath
	 * @param params
	 * @return
	 */
	public long loadCountFromSQLFile(String sqlFilePathOnClasspath, Map<String, Object> params) {
		Query query = createQuery(sqlFilePathOnClasspath, null, params, true);
		return ((Number) query.getSingleResult()).longValue();
	}

	public void execute(String sqlFilePathOnClasspath, Map<String, Object> params) {
		String sql = loadSQLFromClasspath(sqlFilePathOnClasspath, params);
		executeWithSQLFile(sql, params);
	}

	public void executeWithSQLFile(String sql, Map<String, Object> params) {
		Query query = entityManager.createNativeQuery(sql);
		params.entrySet().stream().filter(entry -> null != entry.getValue()).forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));
		query.executeUpdate();
	}
}
