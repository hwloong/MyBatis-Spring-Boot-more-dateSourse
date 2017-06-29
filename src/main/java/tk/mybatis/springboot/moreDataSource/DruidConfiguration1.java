package tk.mybatis.springboot.moreDataSource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/*@Configuration
@MapperScan(basePackages = DruidConfiguration1.PACKAGE, sqlSessionFactoryRef = "druidSqlSessionFactory")*/
public class DruidConfiguration1 {
	
	 static final String PACKAGE = "tk.mybatis.springboot.mapper";
	 
	 @Autowired
	 private DruidProperties2 properties;

	 @Bean(name = "dataSource2")
	 public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        if (properties.getInitialSize() > 0) {
            dataSource.setInitialSize(properties.getInitialSize());
        }
        if (properties.getMinIdle() > 0) {
            dataSource.setMinIdle(properties.getMinIdle());
        }
        if (properties.getMaxActive() > 0) {
            dataSource.setMaxActive(properties.getMaxActive());
        }
        dataSource.setTestOnBorrow(properties.isTestOnBorrow());
        try {
            dataSource.init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
	 }
	 
	 @Bean(name = "druidTransactionManager2")
	    public DataSourceTransactionManager adsTransactionManager(@Qualifier("dataSource2") DataSource dataSource) {
	        return new DataSourceTransactionManager(dataSource);
	    }

	    @Bean(name = "druidSqlSessionFactory2")
	    public SqlSessionFactory adsSqlSessionFactory(@Qualifier("dataSource2") DataSource dataSource) throws Exception {
	        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource);
	        return sessionFactory.getObject();
	    }
}
