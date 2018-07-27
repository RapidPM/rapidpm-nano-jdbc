package org.rapidpm.nano.jdbc.query.collection;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import org.rapidpm.nano.jdbc.BasicOperation;
import org.rapidpm.nano.jdbc.JDBCConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public interface QueryCollectionOfType<T> extends BasicOperation, HasLogger {

  default Result<Collection<T>> execute(JDBCConnectionPool connectionPool) {
    final HikariDataSource dataSource = connectionPool.getDataSource();
    try (final Connection connection = dataSource.getConnection();
         final Statement statement = connection.createStatement();
         final ResultSet resultSet = statement.executeQuery(createSQL())) {

      final Collection<T> result = createCollection();
      while (resultSet.next()) {
        getElement(resultSet)
            .ifPresentOrElse(result::add,
                             failed -> logger().warning("resultSet to User failed " + failed)
            );
      }
      return Result.ofNullable(result);
    } catch (final SQLException e) {
      e.printStackTrace();
      return Result.failure("exception during sql execution " + e.getMessage());
    }
  }

  default Collection<T> createCollection() {
    return new ArrayList<>();
  }

  Result<T> getElement(ResultSet resultSet);

}
