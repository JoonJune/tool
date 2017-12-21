package tool.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;

public class TableInfoDto {
	
	private String env;
	private String tableSchema;
	private String tableName;
	private String tableComment;
	private String columnName;
	private String columnType;
	private String columnDefault;
	private String columnComment;
	private String collationName;
	private String isNullable;
	private String columnKey;
	private String extra;
	private String ordinalPosition;
	
	private String indexName;
	
	private String insertSql;
	
	public TableInfoDto() {
		
	}
	public String getTableName() {
		return this.tableName;
	}
	
	public TableInfoDto(ResultSet rs) {
		this.env             = setValue(rs, "ENV");
		this.tableSchema     = setValue(rs, "TABLE_SCHEMA");
		this.tableName       = setValue(rs, "TABLE_NAME");
		this.tableComment    = setValue(rs, "TABLE_COMMENT");
		this.columnName      = setValue(rs, "COLUMN_NAME");
		this.columnType      = setValue(rs, "COLUMN_TYPE");
		this.columnComment   = setValue(rs, "COLUMN_COMMENT");
		this.collationName   = setValue(rs, "COLLATION_NAME");
		this.isNullable      = setValue(rs, "IS_NULLABLE");
		this.columnDefault   = setValue(rs, "COLUMN_DEFAULT");
		this.columnKey       = setValue(rs, "COLUMN_KEY");
		this.extra           = setValue(rs, "EXTRA");
		this.indexName       = setValue(rs, "INDEX_NAME");
		this.ordinalPosition = setValue(rs, "ORDINAL_POSITION");
	}
	
	private String setValue(ResultSet rs, String string) {
		try {
			return rs.getString(string);
		} catch (SQLException e) {
		}
		return null;
	}
	private String setValue(String sql, String string, String value) {
		if (StringUtils.isNotEmpty(value)) {
			sql = sql.replaceFirst(string, value.replaceAll("'", "''"));
		}
		return sql;
	}
	
	public String getInsertSql(String sql) {
		sql = setValue(sql, "\\{env\\}", this.env);
		sql = setValue(sql, "\\{tableSchema\\}", this.tableSchema);
		sql = setValue(sql, "\\{tableName\\}", this.tableName);
		sql = setValue(sql, "\\{tableComment\\}", this.tableComment);
		sql = setValue(sql, "\\{columnName\\}", this.columnName);
		sql = setValue(sql, "\\{columnType\\}", this.columnType);
		sql = setValue(sql, "\\{columnComment\\}", this.columnComment);
		sql = setValue(sql, "\\{collationName\\}", this.collationName);
		sql = setValue(sql, "\\{isNullable\\}", this.isNullable);
		sql = setValue(sql, "\\{columnDefault\\}", this.columnDefault);
		sql = setValue(sql, "\\{columnKey\\}", this.columnKey);
		sql = setValue(sql, "\\{extra\\}", this.extra);
		sql = setValue(sql, "\\{indexName\\}", this.indexName);
		sql = setValue(sql, "\\{ordinalPosition\\}", this.ordinalPosition);
		return sql;
	}
	public String getInsertSql() {
		return insertSql;
	}
	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}
	
}
