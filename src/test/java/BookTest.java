import org.junit.*;
import static org.junit.Assert.*;


public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void book_instantiatesCorrectly_true() {
    Book newBook = new Book("J.R. Tolkien", "Lord Of The Rings Trilogy");
    assertTrue(newBook instanceof Book);
  }

  @Test
    public void all_emptyAtFirst() {
      assertEquals(Book.all().size(), 0);
    }
}
