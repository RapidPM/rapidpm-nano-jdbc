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
package junit.org.rapidpm.nano.jdbc.v001.dao;


import junit.org.rapidpm.nano.jdbc.v001.User;
import org.rapidpm.frp.functions.CheckedFunction;
import org.rapidpm.frp.model.Result;
import org.rapidpm.nano.jdbc.JDBCConnectionPool;
import org.rapidpm.nano.jdbc.query.QueryOneTypedValue;
import org.rapidpm.nano.jdbc.update.Update;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Optional;


public class UserDAO {

  private JDBCConnectionPool connectionPool;

  public UserDAO workOnPool(final JDBCConnectionPool connectionPool) {
    this.connectionPool = connectionPool;
    return this;
  }


  public void writeUser(final User user) {
    ((Update) () -> createInsert(user)).update(connectionPool);
  }

  private String createInsert(final User user) {
    final String sql = "INSERT INTO CUSTOMER " +
                       "( CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL ) " +
                       " VALUES " +
                       "( " +
                       user.getCustomerID() + ", " +
                       "'" + user.getFirstname() + "', " +
                       "'" + user.getLastname() + "' , " +
                       "'" + user.getEmail() + "' " +
                       ")";
    return sql;
  }

  public Result<Collection<User>> readAllUsers() {
    return ((QueryUserCollection) this::getAllUser).execute(connectionPool);
  }

  public Result<User> readUser(int customerID) {
    return ((QueryUser) () -> getUserByCustomerID(customerID)).execute(connectionPool);
  }

  public static CheckedFunction<ResultSet, User> toUser() {
    return (resultSet) -> new User(
        resultSet.getInt("CUSTOMER_ID"),
        resultSet.getString("FIRSTNAME"),
        resultSet.getString("LASTNAME"),
        resultSet.getString("EMAIL")
    );
  }

  private String getAllUser() {
    return "SELECT CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL FROM CUSTOMER ";
  }

  private String getUserByCustomerID(int customerID) {
    return "SELECT CUSTOMER_ID, FIRSTNAME, LASTNAME, EMAIL " +
           " FROM CUSTOMER " +
           " WHERE CUSTOMER_ID = " + customerID + "";
  }

  public Result<String> readMailAddress(int customerID) {
    return ((QueryOneTypedValue.QueryOneString) () -> getMailByCustomerID(customerID)).execute(connectionPool);
  }

  private String getMailByCustomerID(int customerID) {
    return "SELECT EMAIL FROM CUSTOMER WHERE CUSTOMER_ID = " + customerID + "";
  }

}
