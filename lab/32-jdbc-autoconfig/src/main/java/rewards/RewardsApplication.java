package rewards;

import config.RewardsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

// --------------------------------------------

// TODO-13 (Optional) : Follow the instruction in the lab document.
//           The section titled "Build and Run using Command Line tools".

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Import({RewardsConfig.class})
public class RewardsApplication {
	static final String SQL = "SELECT count(*) FROM T_ACCOUNT";

	final Logger logger
			= LoggerFactory.getLogger(RewardsApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RewardsApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate) {

		Long numberOfAccounts = jdbcTemplate.queryForObject(SQL, Long.class);

		return args -> logger.info("Hello, there are {} accounts", numberOfAccounts);
	}

	@Bean
	CommandLineRunner commandLineRunnerReward(RewardsRecipientProperties properties) {
		return args -> logger.info("Reward name is {}", properties.getName());
	}

}
