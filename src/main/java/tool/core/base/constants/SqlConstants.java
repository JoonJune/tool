package tool.core.base.constants;

public class SqlConstants {
	
	public static String SELECT_INFORMATION_SCHEMA_TABLES = ""
			+ "SELECT TABLE_NAME"
			+ "  FROM INFORMATION_SCHEMA.TABLES"
			+ " WHERE TABLE_SCHEMA = '{tableName}'"
			+ " ORDER BY TABLE_NAME;";
	
	public static String TABLE_INFO_SQL = ""
			+ "SELECT '{env}'                                                          AS ENV"
			+ "     , T1.TABLE_SCHEMA                                                  AS TABLE_SCHEMA"
			+ "     , T1.TABLE_NAME                                                    AS TABLE_NAME"
			+ "     , IF(IFNULL(T2.TABLE_COMMENT, '')= '', 'NULL', T2.TABLE_COMMENT)   AS TABLE_COMMENT"
			+ "     , T1.COLUMN_NAME                                                   AS COLUMN_NAME"
			+ "     , T1.COLUMN_TYPE                                                   AS COLUMN_TYPE"
			+ "     , IFNULL(T1.COLUMN_DEFAULT, 'NULL')                                AS COLUMN_DEFAULT"
			+ "     , IF(IFNULL(T1.COLUMN_COMMENT, '')= '', 'NULL', T1.COLUMN_COMMENT) AS COLUMN_COMMENT"
			+ "     , IFNULL(T1.COLLATION_NAME, 'NULL')                                AS COLLATION_NAME"
			+ "     , T1.IS_NULLABLE                                                   AS IS_NULLABLE"
			+ "     , IF(IFNULL(T1.COLUMN_KEY, '') = '', 'NULL', T1.COLUMN_KEY)        AS COLUMN_KEY"
			+ "     , IF(IFNULL(T1.EXTRA, '') = '', 'NULL', T1.EXTRA)                  AS EXTRA"
			+ "     , T1.ORDINAL_POSITION                                              AS ORDINAL_POSITION"
			+ "  FROM information_schema.COLUMNS     T1"
			+ "  LEFT JOIN information_schema.TABLES T2 ON T1.TABLE_SCHEMA = T2.TABLE_SCHEMA"
			+ "                                        AND T1.TABLE_NAME   = T2.TABLE_NAME"
			+ " WHERE T1.TABLE_SCHEMA = '{tableSchema}';";
	public static String INDEX_INFO_SQL = ""
			+ "SELECT '{env}'         AS ENV"
			+ "     , T1.TABLE_SCHEMA AS TABLE_SCHEMA"
			+ "     , T1.TABLE_NAME   AS TABLE_NAME"
			+ "     , T1.INDEX_NAME   AS INDEX_NAME"
			+ "     , T1.COLUMN_NAME  AS COLUMN_NAME"
			+ "  FROM information_schema.STATISTICS  T1"
			+ "  LEFT JOIN information_schema.TABLES T2 ON T1.TABLE_SCHEMA = T2.TABLE_SCHEMA"
			+ "                                        AND T1.TABLE_NAME   = T2.TABLE_NAME"
			+ " WHERE T1.TABLE_SCHEMA    = '{tableSchema}'"
			+ "   AND UPPER(index_name) != 'PRIMARY';";
}
