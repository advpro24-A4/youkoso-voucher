package id.ac.ui.cs.advprog.youkosoproduct;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application-dev.properties" })
@EnableJpaRepositories(basePackages = "id.ac.ui.cs.advprog.youkosoproduct.model.payment", entityManagerFactoryRef = "paymentEntityManager", transactionManagerRef = "paymentTransactionManager")
public class PersistencePaymentAutoConfiguration {

    @Autowired
    private Environment env;

    public PersistencePaymentAutoConfiguration() {
        super();
    }

    @Bean("paymentEntityManager")
    public LocalContainerEntityManagerFactoryBean paymentEntityManager(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.show_sql", "true");
        return entityManagerFactoryBuilder
                .dataSource(paymentDataSource())
                .packages("id.ac.ui.cs.advprog.youkosoproduct.model.payment")
                .persistenceUnit("payment")
                .properties(jpaProperties)
                .build();
    }

    @Bean("secondDb")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource paymentDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("paymentTransactionManager")
    public PlatformTransactionManager paymentTransactionManager(@Qualifier("paymentEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}