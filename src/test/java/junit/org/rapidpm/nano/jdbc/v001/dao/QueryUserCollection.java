package junit.org.rapidpm.nano.jdbc.v001.dao;

import junit.org.rapidpm.nano.jdbc.v001.User;
import org.rapidpm.frp.model.Result;
import org.rapidpm.nano.jdbc.query.collection.QueryCollectionOfType;

import java.sql.ResultSet;

import static junit.org.rapidpm.nano.jdbc.v001.dao.UserDAO.toUser;

public interface QueryUserCollection extends QueryCollectionOfType<User> {
  @Override
  default Result<User> getElement(ResultSet resultSet) {
    return toUser().apply(resultSet);
  }
}
