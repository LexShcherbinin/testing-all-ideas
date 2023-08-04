package common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Database {

  private static final Config DATABASE_CONFIG = ConfigFactory.load("database.conf");

  public static DataSource getDataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    dataSource.setUrl(DATABASE_CONFIG.getString("url"));
    dataSource.setUsername(DATABASE_CONFIG.getString("username"));
    dataSource.setPassword(DATABASE_CONFIG.getString("password"));
    dataSource.setDriverClassName(DATABASE_CONFIG.getString("driverClassName"));

    return dataSource;
  }

}
