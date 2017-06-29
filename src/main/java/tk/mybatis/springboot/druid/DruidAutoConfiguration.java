package tk.mybatis.springboot.druid;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author liuzh
 * @since 2017/2/5.
 */
@Configuration
@EnableConfigurationProperties(DruidProperties.class)
@MapperScan(basePackages = "tk.mybatis.springboot.mapper", sqlSessionFactoryRef = "druidSqlSessionFactory1")
public class DruidAutoConfiguration {

    @Autowired
    private DruidProperties properties;

    @Bean(name="dataSource1")
    @Primary
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
    
     @Bean(name = "druidTransactionManager1")
	 @Primary
	 public DataSourceTransactionManager adsTransactionManager(@Qualifier("dataSource1") DataSource dataSource) {
       return new DataSourceTransactionManager(dataSource);
	 }

	 @Bean(name = "druidSqlSessionFactory1")
	 @Primary
	 public SqlSessionFactory adsSqlSessionFactory(@Qualifier("dataSource1") DataSource dataSource) throws Exception {
       final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
       sessionFactory.setDataSource(dataSource);
       return sessionFactory.getObject();
	 }
}
