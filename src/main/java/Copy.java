import java.util.List;
import org.sql2o.*;
import java.util.Date;
import java.util.Calendar;


public class Copy {
  private int id;
  private int book_id;
  private Date checkout_date;
  private Date due_date;
  private Boolean returned;

  public Copy(int bookId) {
    this.book_id = bookId;
    this.checkout_date = new Date();
    this.due_date = setDueDate(this.checkout_date);
    this.returned = false;
  }

  //GETTER METHODS//

  public int getId() {
    return id;
  }

  public int getBookId() {
    return book_id;
  }

  public Date getCheckoutDate() {
    return checkout_date;
  }

  public Date getDueDate() {
    return due_date;
  }

  //SETTER METHODS//


  public Date setDueDate(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.DATE, 10);
    date = cal.getTime();
    return date;
  }

  //CREATE//
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO copies (checkout_date, due_date, book_id, returned) VALUES (:checkout_date, :due_date, :book_id, :returned)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("checkout_date", this.checkout_date)
        .addParameter("due_date", this.due_date)
        .addParameter("book_id", this.book_id)
        .addParameter("returned", this.returned)
        .executeUpdate()
        .getKey();
    }
  }

  //READ//
  public static List<Copy> all(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM copies";
      return con.createQuery(sql)
        .executeAndFetch(Copy.class);
    }
  }

  public static Copy find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT *FROM copies WHERE id=:id";
      Copy copy = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Copy.class);
      return copy;
    }
  }

  public List<Patron> getPatrons() {
    try(Connection con = DB.sql2o.open()){
       String sql = "SELECT patrons.* FROM copies JOIN checkouts ON (copies.id = checkouts.copy_id) JOIN copies ON (checkouts.patron_id = patrons.id) WHERE copy_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Patron.class);
      }
  }

  public static List<Copy> findByDueDate(Date due_date) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM copies WHERE due_date=:due_date";
      return con.createQuery(sql)
        .addParameter("due_date", due_date)
        .executeAndFetch(Copy.class);
    }
  }

  //UPDATE//

  public void returnBook() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE checkout SET returned = true WHERE id = :id";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET copies = copies + 1 WHERE id = :book_id";
      con.createQuery(sql)
      .addParameter("book_id", this.book_id)
      .executeUpdate();
    }
  }

  //DELETE//

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM copies WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
