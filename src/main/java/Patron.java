import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;


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

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT books.* FROM patrons JOIN checkouts ON (checkouts.patron_id = patrons.id) JOIN books ON (books.id = checkouts.book_id) WHERE patron_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Book.class);
      }
  }


  //UPDATE//

  public void addCheckout(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO checkouts (patron_id, book_id) VALUES (:patron_id, :book_id);";
      con.createQuery(sql)
        .addParameter("patron_id", this.getId())
        .addParameter("book_id", book.getId())
        .executeUpdate();
    }
  }

}
