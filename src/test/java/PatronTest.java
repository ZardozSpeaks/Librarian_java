import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class PatronTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void book_instantiatesCorrectly_true() {
    Patron newPatron = new Patron("Jimmy");
    assertTrue(newPatron instanceof Patron);
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Patron.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfPatronNamesAreTheSame() {
    Patron newPatronOne = new Patron("Jimmy");
    Patron newPatronTwo = new Patron("Jimmy");
    assertTrue(newPatronOne.equals(newPatronTwo));
  }

  @Test
  public void getBooks_returnsAllBooks_ArrayList() {
    Book myBook = new Book("Moby Dick");
    myBook.save();

    Patron myPatron = new Patron("Jimmy");
    myPatron.save();

    myPatron.addCheckout(myBook);
    List<Book> savedBooks = myPatron.getBooks();
    assertEquals(savedBooks.size(), 1);
  }

  @Test
  public void findByPatron_returnsBooksByPatron() {
    Book newBook1 = new Book("Fellowship of the Ring");
    Book newBook2 = new Book("The Two Towers");
    Book newBook3 = new Book("Return of the King");
    Patron testPatron = new Patron("Jimmy");
    testPatron.save();
    newBook1.save();
    newBook2.save();
    newBook3.save();
    testPatron.addCheckout(newBook1);
    testPatron.addCheckout(newBook2);
    testPatron.addCheckout(newBook3);
    assertTrue((testPatron.getBooks()).contains(newBook1));
    assertTrue((testPatron.getBooks()).contains(newBook2));
    assertTrue((testPatron.getBooks()).contains(newBook3));
  }

}
