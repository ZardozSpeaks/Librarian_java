import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.util.Date;



public class Patron {
  private String name;
  private int id;

  public Patron(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object otherPatron){
    if (!(otherPatron instanceof Patron)) {
      return false;
    } else {
      Patron newPatron = (Patron) otherPatron;
      return this.getName().equals(newPatron.getName());
    }
  }

  //GETTER//
  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  //SETTER
  public void setName(String newName) {
    this.name = newName;
  }

  //CREATE//
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patrons (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  //READ//
  public static List<Patron> all(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patrons";
      return con.createQuery(sql)
        .executeAndFetch(Patron.class);
    }
  }

  public List<Copy> getCopies() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT copies.* FROM patrons JOIN checkouts ON (checkouts.patron_id = patrons.id) JOIN copies ON (copies.id = checkouts.copy_id) WHERE patron_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Copy.class);
      }
  }


  //UPDATE//
  public void addCheckout(Copy copy) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO checkouts (patron_id, copy_id, checkout_date) VALUES (:patron_id, :copy_id, :checkout_date);";
      con.createQuery(sql)
        .addParameter("patron_id", this.getId())
        .addParameter("copy_id", copy.getId())
        .addParameter("checkout_date", copy.getCheckoutDate())
        .executeUpdate();
    }
  }
}
