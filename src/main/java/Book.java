import java.util.List;
import org.sql2o.*;

public class Book {
  private String author;
  private String title;
  private int id;

  public Book (String author, String title) {
    this.author = author;
    this.title = title;
  }

  @Override
  public boolean equals(Object otherBook){
    if (!(otherBook instanceof Book)) {
      return false;
    } else {
      Book newBook = (Book) otherBook;
      return this.getTitle().equals(newBook.getTitle());
    }
  }

  //GETTER METHODS//

  public String getAuthor() {
    return author;
  }

  public String getTitle() {
    return title;
  }

  public int getId() {
    return id;
  }

  //SETTER METHODS//

  public void setAuthor(String newAuthor) {
    this.author = newAuthor
  }

  //CREATE//

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (author, title) VALUES (:author, :title)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("author", this.author)
        .addParameter("title", this.title)
        .executeUpdate()
        .getKey();
    }
  }

  //READ//

  public static List<Book> all(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books";
      return con.createQuery(sql)
        .executeAndFetch(Book.class);
    }
  }


}
