import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;


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

  public ArrayList<Book> getBooks() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT book_id FROM books_authors WHERE author_id = :author_id";
      List<Integer> bookIds = con.createQuery(sql)
        .addParameter("author_id", this.getId())
        .executeAndFetch(Integer.class);

      ArrayList<Book> books = new ArrayList<Book>();

      for (Integer bookId : bookIds) {
        String bookQuery = "SELECT * FROM books WHERE id = :bookId";
        Book book = con.createQuery(bookQuery)
          .addParameter("bookId", bookId)
          .executeAndFetchFirst(Book.class);
        books.add(book);
      }
      return books;
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
