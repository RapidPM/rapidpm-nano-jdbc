package junit.org.rapidpm.nano.jdbc.v001;

import org.rapidpm.frp.model.Quad;

public class User extends Quad<Integer, String, String, String> {

  public User(Integer customerID, String firstname, String lastname, String email) {
    super(customerID, firstname, lastname, email);
  }

  public Integer getCustomerID() {
    return getT1();
  }

  public String getFirstname() {
    return getT2();
  }

  public String getLastname() {
    return getT3();
  }

  public String getEmail() {
    return getT4();
  }


}
