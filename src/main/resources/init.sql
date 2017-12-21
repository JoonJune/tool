create table db_info
(
    id int auto_increment
        primary key,
    db_ip varchar(30) null,
    db_port int(10) null,
    db_driver varchar(50) null,
    db_user varchar(50) null,
    db_password varchar(50) null,
    memo varchar(100) null
)
;
create table diff_table_info
(
    id int(20) auto_increment
        primary key,
    tb_schema varchar(50) null,
    first_db_id int null,
    second_db_id int null,
    memo varchar(100) null
)
;
create table index_info
(
    ID int(20) auto_increment
        primary key,
    ENV char(10) null,
    TABLE_SCHEMA varchar(50) null,
    TABLE_NAME varchar(100) null,
    COLUMN_NAME varchar(100) null,
    INDEX_NAME varchar(200) null
)
;
create table table_info
(
    ID int(20) auto_increment
        primary key,
    ENV char(10) null,
    TABLE_SCHEMA varchar(50) null,
    TABLE_NAME varchar(100) null,
    TABLE_COMMENT varchar(500) null,
    COLUMN_NAME varchar(100) null,
    COLUMN_TYPE varchar(20) null,
    COLUMN_DEFAULT varchar(100) null,
    COLUMN_COMMENT varchar(500) null,
    COLLATION_NAME varchar(50) null,
    ORDINAL_POSITION int null,
    IS_NULLABLE char(10) null,
    COLUMN_KEY varchar(50) null,
    EXTRA varchar(100) null
)
;
create index TABLE_INFO_IDX_OF_ENV on table_info (ENV);
create index TABLE_INFO_IDX_OF_COLUMN_NAME on table_info (COLUMN_NAME);
create index TABLE_INFO_IDX_OF_TABLE_NAME on table_info (TABLE_NAME);

CREATE PROCEDURE `db_diff`()
BEGIN
# 新增表存入临时表中
drop table if exists temp_table;
create temporary table temp_table
select T1.TABLE_NAME AS TABLE_NAME
  from (
select TABLE_SCHEMA, TABLE_NAME, max(ORDINAL_POSITION) as ORDINAL_POSITION from table_info where ENV = 'SRC' group by TABLE_SCHEMA, TABLE_NAME
  ) T1
  LEFT JOIN (
select TABLE_SCHEMA, TABLE_NAME from table_info where ENV = 'DES' group by TABLE_SCHEMA, TABLE_NAME
      ) T2 ON T1.TABLE_NAME = T2.TABLE_NAME
 WHERE T2.TABLE_NAME IS NULL;

DROP TABLE IF EXISTS temp_table1;
CREATE TEMPORARY TABLE temp_table1 select * from temp_table;
DROP TABLE IF EXISTS temp_table2;
CREATE TEMPORARY TABLE temp_table2 select * from temp_table;
DROP TABLE IF EXISTS temp_table3;
CREATE TEMPORARY TABLE temp_table3 select * from temp_table;
DROP TABLE IF EXISTS temp_table4;
CREATE TEMPORARY TABLE temp_table4 select * from temp_table;
DROP TABLE IF EXISTS temp_table4;
CREATE TEMPORARY TABLE temp_table4 select * from temp_table;
DROP TABLE IF EXISTS temp_table5;
CREATE TEMPORARY TABLE temp_table5 select * from temp_table;
DROP TABLE IF EXISTS temp_table6;
CREATE TEMPORARY TABLE temp_table6 select * from temp_table;

(
# 新增字段(排除新增表)
SELECT CONCAT(
                'ALTER TABLE '
              , T1.TABLE_SCHEMA, '.'
              , T1.TABLE_NAME, ' '
              , 'ADD '
              , T1.COLUMN_NAME, ' '
              , T1.COLUMN_TYPE, ' '
              , CASE
                  WHEN T1.COLUMN_DEFAULT != 'NULL'
                  THEN (
                        CASE
                          WHEN INSTR(UPPER(T1.COLUMN_TYPE), 'INT') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'DECIMAL') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'FLOAT') > 0
                          THEN CONCAT('DEFAULT ', T1.COLUMN_DEFAULT, ' ')
                          WHEN INSTR(UPPER(T1.COLUMN_TYPE), 'CHAR') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'ENUM') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'TEXT') > 0
                          THEN CONCAT('DEFAULT ''', T1.COLUMN_DEFAULT, ''' ')
                          WHEN INSTR(UPPER(T1.COLUMN_TYPE), 'DATE') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'TIME') > 0
                          THEN CONCAT('DEFAULT ', T1.COLUMN_DEFAULT, ' ')
                        END
                       )
                  ELSE ''
                END
              , CASE WHEN T1.EXTRA != 'NULL' THEN CONCAT(T1.EXTRA, ' ') ELSE '' END
              , CASE
                  WHEN T1.COLUMN_COMMENT != 'NULL'
                  THEN CONCAT('COMMENT ''', REPLACE(REPLACE(T1.COLUMN_COMMENT, CHAR(10), ''), '''', ''''''), ''' ')
                  ELSE ''
                END
              , 'AFTER '
              , (SELECT T4.COLUMN_NAME FROM TABLE_INFO T4 WHERE T4.ENV = 'SRC' AND T4.TABLE_NAME = T1.TABLE_NAME AND T4.ORDINAL_POSITION = T1.ORDINAL_POSITION - 1)
              , ';'
             ) AS INSERT_SQL
  FROM TABLE_INFO      T1
  LEFT JOIN TABLE_INFO T2 ON T1.TABLE_NAME = T2.TABLE_NAME AND T1.COLUMN_NAME = T2.COLUMN_NAME AND T2.ENV = 'DES'
 WHERE T1.ENV = 'SRC'
   AND T2.ID IS NULL
   AND T1.TABLE_NAME NOT IN (SELECT TABLE_NAME FROM temp_table)
)
UNION ALL
(
# 更新字段(排除新增表)
SELECT CONCAT(
                'ALTER TABLE '
              , T1.TABLE_SCHEMA, '.'
              , T1.TABLE_NAME, ' '
              , 'MODIFY '
              , T1.COLUMN_NAME, ' '
              , T1.COLUMN_TYPE, ' '
              , CASE
                  WHEN T1.COLUMN_DEFAULT != 'NULL'
                  THEN (
                        CASE
                          WHEN INSTR(UPPER(T1.COLUMN_TYPE), 'INT') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'DECIMAL') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'FLOAT') > 0
                          THEN CONCAT('DEFAULT ', T1.COLUMN_DEFAULT, ' ')
                          WHEN INSTR(UPPER(T1.COLUMN_TYPE), 'CHAR') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'ENUM') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'TEXT') > 0
                          THEN CONCAT('DEFAULT ''', T1.COLUMN_DEFAULT, ''' ')
                          WHEN INSTR(UPPER(T1.COLUMN_TYPE), 'DATE') > 0
                            OR INSTR(UPPER(T1.COLUMN_TYPE), 'TIME') > 0
                          THEN CONCAT('DEFAULT ', T1.COLUMN_DEFAULT, ' ')
                        END
                       )
                  ELSE ''
                END
              , CASE WHEN T1.EXTRA != 'NULL' THEN CONCAT(T1.EXTRA, ' ') ELSE '' END
              , CASE
                  WHEN T1.COLUMN_COMMENT != 'NULL'
                  THEN CONCAT('COMMENT ''', REPLACE(REPLACE(T1.COLUMN_COMMENT, CHAR(10), ''), '''', ''''''), ''' ')
                  ELSE ''
                END
              , 'AFTER '
              , (SELECT T4.COLUMN_NAME FROM TABLE_INFO T4 WHERE T4.ENV = 'SRC' AND T4.TABLE_NAME = T1.TABLE_NAME AND T4.ORDINAL_POSITION = T1.ORDINAL_POSITION - 1)
              , ';'
             ) AS MODIFY_SQL

  FROM TABLE_INFO T1
  LEFT JOIN TABLE_INFO T2 ON T1.TABLE_NAME = T2.TABLE_NAME AND T1.COLUMN_NAME = T2.COLUMN_NAME AND T2.ENV = 'DES'
 WHERE T1.ENV = 'SRC'
   AND (
           T1.COLUMN_NAME    != T2.COLUMN_NAME
        OR T1.COLUMN_TYPE    != T2.COLUMN_TYPE
        OR T1.COLUMN_DEFAULT != T2.COLUMN_DEFAULT
        OR T1.COLUMN_COMMENT != T2.COLUMN_COMMENT
        OR T1.COLLATION_NAME != T2.COLLATION_NAME
        OR T1.IS_NULLABLE    != T2.IS_NULLABLE
        OR T1.COLUMN_KEY     != T2.COLUMN_KEY
        OR T1.EXTRA          != T2.EXTRA
       )
   AND T1.TABLE_NAME NOT IN (SELECT TABLE_NAME FROM temp_table1)
)
UNION ALL
(
# 新增索引(排除新增表)
SELECT CONCAT('CREATE INDEX ', T1.INDEX_NAME, ' ON ', T1.TABLE_NAME, '(', T1.COLUMN_NAME, ');') AS INDEX_SQL
  FROM index_info T1
  LEFT JOIN index_info T2 ON T1.COLUMN_NAME = T2.COLUMN_NAME AND T2.ENV = 'DES'
 WHERE T1.ENV = 'SRC'
   AND T2.ID IS NULL
   AND T1.TABLE_NAME NOT IN (SELECT TABLE_NAME FROM temp_table2)
)

UNION ALL
# 新增表的表结构
(
SELECT T1.TABLE_SQL
  FROM
(
(
	SELECT TABLE_NAME, CONCAT('CREATE TABLE ', TABLE_SCHEMA, '.', TABLE_NAME, ' (') AS TABLE_SQL
	  FROM table_info
	 WHERE TABLE_NAME IN (SELECT TABLE_NAME FROM temp_table3)
	 GROUP BY TABLE_NAME
)
union all
(
	SELECT TABLE_NAME
		   , CONCAT(
 		             '  '
			         , COLUMN_NAME, ' '
			         , COLUMN_TYPE, ' '
			         , IF(COLUMN_KEY = 'PRI', EXTRA, ''), ' '
			         , CASE
					         WHEN COLUMN_DEFAULT != 'NULL'
					         THEN (
								         CASE
									         WHEN INSTR(UPPER(COLUMN_TYPE), 'INT') > 0
										         OR INSTR(UPPER(COLUMN_TYPE), 'DECIMAL') > 0
										         OR INSTR(UPPER(COLUMN_TYPE), 'FLOAT') > 0
									         THEN CONCAT('DEFAULT ', COLUMN_DEFAULT, ' ')
									         WHEN INSTR(UPPER(COLUMN_TYPE), 'CHAR') > 0
										         OR INSTR(UPPER(COLUMN_TYPE), 'ENUM') > 0
										         OR INSTR(UPPER(COLUMN_TYPE), 'TEXT') > 0
									         THEN CONCAT('DEFAULT ''', COLUMN_DEFAULT, ''' ')
									         WHEN INSTR(UPPER(COLUMN_TYPE), 'DATE') > 0
										         OR INSTR(UPPER(COLUMN_TYPE), 'TIME') > 0
									         THEN CONCAT('DEFAULT ', COLUMN_DEFAULT, ' ')
								         END
							          )
					         ELSE ''
				         END
			         , CASE WHEN EXTRA != 'NULL' AND COLUMN_KEY != 'PRI' THEN CONCAT(EXTRA, ' ') ELSE '' END
			         , CASE
					         WHEN COLUMN_COMMENT != 'NULL'
					         THEN CONCAT('COMMENT ''', REPLACE(REPLACE(COLUMN_COMMENT, CHAR(10), ''), '''', ''''''), ''' ')
					         ELSE ''
				         END
	             , IF(COLUMN_KEY = 'PRI', 'primary key', '')
							 , IF(ORDINAL_POSITION = (SELECT MAX(ORDINAL_POSITION) FROM table_info where ENV = 'SRC' and table_name = t1.TABLE_NAME), '', ',')
		          ) AS TABLE_SQL
  FROM table_info T1
 WHERE TABLE_NAME IN (SELECT TABLE_NAME FROM temp_table4)
	 AND ENV = 'SRC'
	)
union all
(
	SELECT TABLE_NAME, CONCAT(') ', IF(TABLE_COMMENT != 'NULL', CONCAT('COMMENT ''', TABLE_COMMENT, ''''), ''), ';') AS TABLE_SQL
	  FROM table_info
  WHERE TABLE_NAME IN (SELECT TABLE_NAME FROM temp_table5)
	GROUP BY TABLE_NAME
)
ORDER BY TABLE_NAME
) T1
)



UNION ALL
(
# 新增表的索引
SELECT CONCAT('CREATE INDEX ', T1.INDEX_NAME, ' ON ', T1.TABLE_NAME, '(', T1.COLUMN_NAME, ');') AS INDEX_SQL
  FROM index_info T1
 WHERE T1.ENV = 'SRC'
   AND T1.TABLE_NAME IN (SELECT TABLE_NAME FROM temp_table6)
);

DROP TABLE IF EXISTS temp_table;
DROP TABLE IF EXISTS temp_table1;
DROP TABLE IF EXISTS temp_table2;
DROP TABLE IF EXISTS temp_table3;
DROP TABLE IF EXISTS temp_table4;
DROP TABLE IF EXISTS temp_table5;
DROP TABLE IF EXISTS temp_table6;
END
