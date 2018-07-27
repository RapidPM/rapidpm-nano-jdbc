/**
 * Copyright © 2013 Sven Ruppert (sven.ruppert@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package junit.org.rapidpm.nano.jdbc.v001;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import junit.org.rapidpm.nano.jdbc.v001.dao.UserDAO;
import org.junit.jupiter.api.Test;
import org.rapidpm.frp.model.Result;
import org.rapidpm.nano.jdbc.JDBCConnectionPool;

public class UserDAOTest002 extends UserDAOBaseTest {

  @Test
  public void test001() throws Exception {
    final Result<JDBCConnectionPool> connectionPoolOptional = pools().getPool(poolname());
    final JDBCConnectionPool         connectionPool         = connectionPoolOptional.get();

    final UserDAO userDAO = new UserDAO()
        .workOnPool(connectionPool);

    final Result<User> user = userDAO.readUser(1001);

    assertNotNull(user);
    assertTrue(user.isPresent());
    assertEquals("Marge" , user.get().getFirstname());
  }
}
