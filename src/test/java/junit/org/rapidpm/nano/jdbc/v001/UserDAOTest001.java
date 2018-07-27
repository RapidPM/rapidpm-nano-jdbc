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
package junit.org.rapidpm.nano.jdbc.v001;


import junit.org.rapidpm.nano.jdbc.v001.dao.UserDAO;
import org.junit.jupiter.api.Test;
import org.rapidpm.frp.model.Result;
import org.rapidpm.nano.jdbc.JDBCConnectionPool;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest001 extends UserDAOBaseTest {


  @Test
  public void test001() throws Exception {
    final Result<JDBCConnectionPool> connectionPoolOptional = pools().getPool(poolname());
    final JDBCConnectionPool         connectionPool         = connectionPoolOptional.get();
    final UserDAO                    userDAO                = new UserDAO().workOnPool(connectionPool);

    final User user = new User(001, "jon", "doe", "jon.d@yahooo.com");
    userDAO.writeUser(user);

    final Result<User> resultUser = userDAO.readUser(001);
    assertNotNull(resultUser);
    assertTrue(resultUser.isPresent());
    assertEquals(user.getCustomerID(), resultUser.get().getCustomerID());
    assertEquals(user.getFirstname(), resultUser.get().getFirstname());

    final User user02 = new User(002, "jane", "doe", "jane.d@yahooo.com");
    userDAO.writeUser(user02);

    final Result<String> resultMail = userDAO.readMailAddress(002);
    assertNotNull(resultMail);
    assertTrue(resultMail.isPresent());
    assertEquals("jane.d@yahooo.com", resultMail.get());
  }


}
