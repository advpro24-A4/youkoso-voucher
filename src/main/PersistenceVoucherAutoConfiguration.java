package id.ac.ui.cs.advprog.youkosoproduct;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:application-dev.properties" })
@EnableJpaRepositories(basePackages = "id.ac.ui.cs.advprog.youkosoproduct.model.voucher", entityManagerFactoryRef = "voucherEntityManager", transactionManagerRef = "voucherTransactionManager")
// @Profile("!tc")
public class PersistenceVoucherAutoConfiguration {
    @Autowired
    private Environment env;

    public PersistenceVoucherAutoConfiguration() {
        super();
    }

    //

    @Bean("voucherEntityManager")
    public LocalContainerEntityManagerFactoryBean voucherEntityManager(
            EntityManagerFactoryBuilder entityManagerFactoryBuilder){
        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "none");
        jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.show_sql", "true");
        return entityManagerFactoryBuilder
                .dataSource(voucherDataSource())
                .packages("id.ac.ui.cs.advprog.youkosoproduct.model.payment")
                .persistenceUnit("voucher")
                .properties(jpaProperties)
                .build();
    }

    @Bean("primaryDb")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource voucherDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("voucherTransactionManager")
    public PlatformTransactionManager voucherTransactionManager(
            @Qualifier("voucherEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }

}