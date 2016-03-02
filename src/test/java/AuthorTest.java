import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class AuthorTest {


  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void book_instantiatesCorrectly_true() {
    Author newAuthor = new Author("J.R.R. Tolkien");
    assertTrue(newAuthor instanceof Author);
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Author.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfAuthorNamesAreTheSame() {
    Author newAuthorOne = new Author("Harry Thompson");
    Author newAuthorTwo = new Author("Harry Thompson");
    assertTrue(newAuthorOne.equals(newAuthorTwo));
  }

  @Test
  public void getBooks_returnsAllBooks_ArrayList() {
    Book myBook = new Book("Moby Dick");
    myBook.save();

    Author myAuthor = new Author("Herman Melville");
    myAuthor.save();

    myAuthor.addBook(myBook);
    List savedBooks = myAuthor.getBooks();
    assertEquals(savedBooks.size(), 1);
  }

  // @Test
  // public void findByAuthor_returnsBooksByAuthor() {
  //   Book newBook1 = new Book("J.R.R. Tolkien", "Fellowship of the Ring");
  //   Book newBook2 = new Book("J.R.R. Tolkien", "The Two Towers");
  //   Book newBook3 = new Book("J.R.R. Tolkien", "Return of the King");
  //   newBook1.save();
  //   newBook2.save();
  //   newBook3.save();
  //   List<Book> bookList = Book.findByAuthor("J.R.R. Tolkien");
  //   assertTrue(bookList.contains(newBook1));
  //   assertTrue(bookList.contains(newBook2));
  //   assertTrue(bookList.contains(newBook3));
  // }

}
