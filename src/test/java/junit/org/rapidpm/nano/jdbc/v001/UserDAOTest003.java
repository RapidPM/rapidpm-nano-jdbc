package junit.org.rapidpm.nano.jdbc.v001;

import junit.org.rapidpm.nano.jdbc.v001.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.rapidpm.frp.model.Result;
import org.rapidpm.nano.jdbc.JDBCConnectionPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserDAOTest003 extends UserDAOBaseTest {

  @Test
  public void test001() throws Exception {
    final Result<JDBCConnectionPool> connectionPoolOptional = pools().getPool(poolname());
    final JDBCConnectionPool         connectionPool         = connectionPoolOptional.get();

    final UserDAO userDAO = new UserDAO()
        .workOnPool(connectionPool);

    userDAO
        .readAllUsers().
        ifFailed(Assertions::fail)
        .ifAbsent(() -> Assertions.fail("no data found"))
        .ifPresent(result -> {
          assertFalse(result.isEmpty());
          assertEquals(5, result.size());
        })
    .get()
    .forEach(e -> logger().info(e.toString()));


  }
}
