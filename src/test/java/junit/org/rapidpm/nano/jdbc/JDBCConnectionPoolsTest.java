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
package junit.org.rapidpm.nano.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import org.hsqldb.server.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rapidpm.dependencies.core.net.PortUtils;
import org.rapidpm.nano.jdbc.JDBCConnectionPools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JDBCConnectionPool Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jul 10, 2015</pre>
 */
public class JDBCConnectionPoolsTest {


  public static final String              POOLNAME        = "testPool";
  private             Server              hsqlServer      = null;
  private             JDBCConnectionPools connectionPools = null;

  @BeforeEach
  public void before() throws Exception {
    hsqlServer = new Server();
    hsqlServer.setLogWriter(null);
    hsqlServer.setSilent(true);
    hsqlServer.setPort(new PortUtils().nextFreePortForTest());
    hsqlServer.setAddress("127.0.0.1");
    hsqlServer.setDatabaseName(0, "iva");
    hsqlServer.setDatabasePath(0, "mem:target/ivadb");
    hsqlServer.start();

    connectionPools = new JDBCConnectionPools();
  }

  @AfterEach
  public void after() throws Exception {
    hsqlServer.stop();
  }


  /**
   * Method: initPool()
   */
  @Test
  public void testInitPool() throws Exception {

    final JDBCConnectionPools connectionPools = ((this.connectionPools == null) ? new JDBCConnectionPools() : this.connectionPools)
        .addJDBCConnectionPool(POOLNAME)
        .withAutoCommit(false)
        .withJdbcURL("jdbc:hsqldb:mem:target/ivadb")
        .withUsername("sa")
        .withPasswd("")
        .withTimeout(2000)
        .done();

    connectionPools.connectPools();

    final Connection connection = connectionPools.getDataSource(POOLNAME).getConnection();
    try {
      connection.prepareStatement("drop table barcodes if exists;").execute();
      connection.prepareStatement("create table barcodes (id integer, barcode varchar(20) not null);").execute();
      connection.prepareStatement("insert into barcodes (id, barcode) values (1, '12345566');").execute();

      // query from the db
      ResultSet rs = connection.prepareStatement("select id, barcode  from barcodes;").executeQuery();
      rs.next();
      System.out.println(String.format("ID: %1d, Name: %1s", rs.getInt(1), rs.getString(2)));

    } catch (SQLException e2) {
      e2.printStackTrace();
    }


    connectionPools.shutdownPools();

  }

  @Test
  public void testGetNoneExistent() {
    HikariDataSource nope = ((this.connectionPools == null) ? new JDBCConnectionPools() : this.connectionPools).getDataSource("Nope");
    assertEquals(null, nope);
  }

} 
