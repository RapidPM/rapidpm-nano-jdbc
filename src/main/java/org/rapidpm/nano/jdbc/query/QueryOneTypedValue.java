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

import org.rapidpm.frp.model.Result;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryOneTypedValue<T> extends QueryOneValue<T> {


  interface QueryOneInteger extends QueryOneTypedValue<Integer> {
    default Result<Integer> getFirstElement(final ResultSet resultSet) throws SQLException {
      return Result.ofNullable(resultSet.getInt(1));
    }
  }

  interface QueryOneFloat extends QueryOneTypedValue<Float> {
    default Result<Float> getFirstElement(final ResultSet resultSet) throws SQLException {
      return Result.ofNullable(resultSet.getFloat(1));
    }
  }

  interface QueryOneString extends QueryOneTypedValue<String> {
    default Result<String> getFirstElement(final ResultSet resultSet) throws SQLException {
      return Result.ofNullable(resultSet.getString(1));
    }
  }


}
