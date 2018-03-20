package it.com.foresightautomotive.repository;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static it.com.foresightautomotive.common.EnvironmentVariables.MONGO_HOST;

@Configuration()
@EnableMongoRepositories("com.foresightautomotive.repository")
public class PortfolioRepositoryContext {

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(new MongoClient(MONGO_HOST), "stock-exchange-it");
    }
}
