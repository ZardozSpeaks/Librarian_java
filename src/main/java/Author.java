import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.util.Date;



public class Author {
  private String name;
  private int id;

  public Author(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object otherAuthor){
    if (!(otherAuthor instanceof Author)) {
      return false;
    } else {
      Author newAuthor = (Author) otherAuthor;
      return this.getName().equals(newAuthor.getName());
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
      String sql = "INSERT INTO authors (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  //READ//
  public static List<Author> all(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM authors";
      return con.createQuery(sql)
        .executeAndFetch(Author.class);
    }
  }

  public List<Book> getBooks() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT books.* FROM authors JOIN books_authors ON (books_authors.author_id = authors.id) JOIN books ON (books.id = books_authors.book_id) WHERE author_id=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Book.class);
      }
  }

  //UPDATE//
  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books_authors (author_id, book_id) VALUES (:author_id, :book_id);";
      con.createQuery(sql)
        .addParameter("author_id", this.getId())
        .addParameter("book_id", book.getId())
        .executeUpdate();
    }
  }

}
