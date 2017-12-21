package tool.core.config.datasource.local;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import tool.core.config.datasource.DSConstants;
import tool.core.config.datasource.SqlSessionFactoryUtils;

@Configuration
@MapperScan(basePackages = LocalhostDataSource.PACKAGE, sqlSessionTemplateRef  = LocalhostDataSource.SQL_SESSION_TEMPLATE)
public class LocalhostDataSource {
	
	static final String DB_NAME = "local";

	static final String DB_NAME_PREFIX = DB_NAME;
	static final String PACKAGE = DSConstants.PACKAGE_PREFIX + DB_NAME;
	static final String PREFIX = DSConstants.DATA_SOURCE_CONFIG_PREFIX + DB_NAME;
	
	static final String DATA_SOURCE = DB_NAME_PREFIX + DSConstants.DATA_SOURCE;
	static final String TRANSACTION_MANAGER = DB_NAME_PREFIX + DSConstants.TRANSACTION_MANAGER;
	static final String SQL_SESSION_FACTORY = DB_NAME_PREFIX + DSConstants.SQL_SESSION_FACTORY;
	static final String SQL_SESSION_TEMPLATE = DB_NAME_PREFIX + DSConstants.SQL_SESSION_TEMPLATE;
	
    static final String MAPPER_LOCATION = DSConstants.MAPPER_LOCATION_PREFIX + DB_NAME + "/*.xml";
    
    @Bean(name = DATA_SOURCE)
    @ConfigurationProperties(prefix = PREFIX)
    public DataSource build() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = TRANSACTION_MANAGER)
    public DataSourceTransactionManager buildDatasourceTransactionManager(@Qualifier(DATA_SOURCE) DataSource dataSource) {
    	return new DataSourceTransactionManager(dataSource);
    }
    
    @Bean(name = SQL_SESSION_FACTORY)
    public SqlSessionFactory buildSqlSessionFactory(@Qualifier(DATA_SOURCE) DataSource dataSource) throws Exception {
    	return SqlSessionFactoryUtils.getSqlSessionFactory(dataSource, MAPPER_LOCATION);
    }

    @Bean(name = SQL_SESSION_TEMPLATE)
    public SqlSessionTemplate buildSqlSessionTemplate(@Qualifier(SQL_SESSION_FACTORY) SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}