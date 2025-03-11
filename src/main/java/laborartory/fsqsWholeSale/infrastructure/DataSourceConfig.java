package laborartory.fsqsWholeSale.infrastructure;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/AgriEcommerceDB")
                .username("agri_user")
                .password("Flabola1985")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}
