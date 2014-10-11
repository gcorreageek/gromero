package pe.com.ransa.portal.intranet.util;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import pe.com.ransa.portal.intranet.dto.Bodega;

public class UtilesConexxion {
	private SqlSessionFactory sqlSessionFactory = null;
	public  UtilesConexxion(){ 
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("development", transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		configuration.addMapper(Bodega.class);
		setSqlSessionFactory(new SqlSessionFactoryBuilder().build(configuration));
	}  
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	private final static BasicDataSource dataSource;
	static {
		dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/registrationtest");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setMaxActive(100);
		dataSource.setMaxWait(10000);
		dataSource.setMaxIdle(10);
	}
	
}
