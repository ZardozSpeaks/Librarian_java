import java.util.List;
import org.sql2o.*;


public class Book {
  private String author;
  private String title;
  private int id;

  public Book (String title) {
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

  public String getTitle() {
    return title;
  }

  public int getId() {
    return id;
  }

  //SETTER METHODS//


  public void setTitle(String newTitle) {
    this.title = newTitle;
  }

  //CREATE//
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title) VALUES (:title)";
      this.id = (int) con.createQuery(sql, true)
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

  public static Book find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT *FROM books WHERE id=:id";
      Book book = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Book.class);
      return book;
    }
  }

  public List<Author> getAuthors() {
    try(Connection con = DB.sql2o.open()){
       String sql = "SELECT authors.* FROM books JOIN books_authors ON (books.id = books_authors.book_id) JOIN authors ON (books_authors.author_id = authors.id) WHERE book_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Author.class);
      }
  }

  public static List<Book> findByTitle(String title) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM books WHERE title=:title";
      return con.createQuery(sql)
        .addParameter("title", title)
        .executeAndFetch(Book.class);
    }
  }

  //UPDATE//

  public void update() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE books SET title =:title WHERE id=:id";
        con.createQuery(sql)
        .addParameter("title", this.title)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (author_id, book_id) VALUES (:author_id, :book_id);";
      con.createQuery(sql)
        .addParameter("author_id", author.getId())
        .addParameter("book_id", this.getId())
        .executeUpdate();
    }
  }

  //DELETE//

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM books WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

}
