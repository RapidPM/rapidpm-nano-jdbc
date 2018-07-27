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


import junit.org.rapidpm.nano.jdbc.HsqlBaseTest;
import junit.org.rapidpm.nano.jdbc.InMemoryHsqldbBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.rapidpm.nano.jdbc.JDBCConnectionPools;


public class UserDAOBaseTest extends HsqlBaseTest {

  final JDBCConnectionPools pools = new JDBCConnectionPools();

  @Override
  public JDBCConnectionPools pools() {
    return pools;
  }

  @Override
  public String[] createSQLInitScriptArray() {
    return new String[]{
        "CLEAR_SCHEMA.sql" , "CREATE_TABLE_EXAMPLE.sql"
    };

  }

  private InMemoryHsqldbBuilder.ServerResult serverResult;

  @BeforeEach
  public void setUp() throws Exception {
    System.out.println("poolname = " + poolname());

    serverResult = InMemoryHsqldbBuilder.newBuilder()
                                        .withDbName("testDB")
                                        .withRandomPort()
                                        .build();


    startPoolsAndConnect(poolname() , serverResult.getUrl());
    initSchema(poolname());
  }

  @AfterEach
  public void tearDown() throws Exception {
    pools().shutdownPool(poolname());

  }

  @Override
  public Class baseTestClass() {
    return UserDAOBaseTest.class;
  }

}
