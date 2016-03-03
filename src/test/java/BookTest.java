import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;


public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void book_instantiatesCorrectly_true() {
    Book newBook = new Book("Lord Of The Rings Trilogy");
    assertTrue(newBook instanceof Book);
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfBookTitlesAreTheSame() {
    Book newBookOne = new Book("Lord Of The Rings Trilogy");
    Book newBookTwo = new Book("Lord Of The Rings Trilogy");
    assertTrue(newBookOne.equals(newBookTwo));
  }

  @Test
  public void save_assignsIdToObject() {
    Book myBook = new Book("Lord Of The Rings Trilogy");
    myBook.save();
    Book savedBook = Book.all().get(0);
    assertEquals(myBook.getId(), savedBook.getId());
  }

  @Test
  public void find_findBookInDatabase_true() {
    Book myBook = new Book("Lord Of The Rings Trilogy");
    myBook.save();
    Book savedBook = Book.find(myBook.getId());
    assertTrue(myBook.equals(savedBook));
  }

  @Test
  public void update_updatesAllBookProperties() {
    Book newBook = new Book("Lord Of The Rings Trilogy");
    newBook.save();
    newBook.setTitle("This Thing Of Darkness");
    newBook.update();
    assertEquals("This Thing Of Darkness", newBook.getTitle());
  }

  @Test
  public void updateCopy_updatesNumberOfBooks() {
    Book newBook = new Book("Lord Of The Rings Trilogy");
    newBook.save();
    newBook.setCopies(5);
    newBook.updateCheckout();
    assertEquals(5, newBook.getCopy());
  }

  @Test
  public void delete_removesBookFromTheDatabase() {
    Book newBook = new Book("Being and Nothingness");
    newBook.save();
    newBook.delete();
    assertEquals(Book.all().size(), 0);
  }

  @Test
  public void getAuthors_returnsAllAuthors_ArrayList() {
    Book myBook = new Book("Moby Dick");
    myBook.save();

    Author myAuthor = new Author("Herman Melville");
    myAuthor.save();

    myBook.addAuthor(myAuthor);
    List savedAuthors = myBook.getAuthors();
    assertEquals(savedAuthors.size(), 1);
  }

}
