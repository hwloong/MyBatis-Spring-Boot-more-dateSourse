package tk.mybatis.springboot.druid;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import tk.mybatis.springboot.moreDataSource.DruidConfiguration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author liuzh
 * @since 2017/2/5.
 */
@Configuration
@EnableConfigurationProperties(DruidProperties1.class)
@MapperScan(basePackages = "tk.mybatis.springboot.mapper1", sqlSessionFactoryRef = "druidSqlSessionFactory2")
public class DruidAutoConfiguration1 {

    @Autowired
    private DruidProperties1 properties;

    @Bean(name="dataSource2")
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
