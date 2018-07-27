package junit.org.rapidpm.nano.jdbc.v001.dao;

import junit.org.rapidpm.nano.jdbc.v001.User;
import org.rapidpm.frp.model.Result;
import org.rapidpm.nano.jdbc.query.QueryOneValue;

import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.org.rapidpm.nano.jdbc.v001.dao.UserDAO.toUser;

public interface QueryUser extends QueryOneValue<User> {

  @Override
  default Result<User> getFirstElement(final ResultSet resultSet) throws SQLException {
    return toUser()
        .apply(resultSet);
  }
}
