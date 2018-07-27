/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.nano.jdbc.query;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.frp.model.Result;
import org.rapidpm.nano.jdbc.BasicOperation;
import org.rapidpm.nano.jdbc.JDBCConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public interface QueryOneValue<T> extends BasicOperation {

  default Result<T> execute(JDBCConnectionPool connectionPool) {
    final HikariDataSource dataSource = connectionPool.getDataSource();
    try (final Connection connection = dataSource.getConnection();
         final Statement statement = connection.createStatement();
         final ResultSet resultSet = statement.executeQuery(createSQL())) {
      final boolean next = resultSet.next();
      if (next) {
        Result<T> firstElement = getFirstElement(resultSet);
        return (resultSet.next())
               ? Result.failure("too many values are selected with query")
               : firstElement;
      } else {
        return Result.failure("no value present..");
      }
    } catch (final SQLException e) {
      e.printStackTrace();
      return Result.failure(e.getMessage());
    }
  }

  Result<T> getFirstElement(final ResultSet resultSet) throws SQLException;

}
