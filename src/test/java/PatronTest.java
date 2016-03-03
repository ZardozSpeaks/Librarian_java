import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Date;


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
  public void getCopies_returnsAllCopies_ArrayList() {
    Copy myCopies = new Copy(1);
    myCopies.save();

    Patron myPatron = new Patron("Jimmy");
    myPatron.save();

    myPatron.addCheckout(myCopies);
    List<Copy> savedCopies = myPatron.getCopies();
    assertEquals(savedCopies.size(), 1);
  }

  @Test
  public void findByPatron_returnsBooksByPatron() {
    Copy newCopy1 = new Copy(1);
    Patron testPatron = new Patron("Jimmy");
    testPatron.save();
    newCopy1.save();
    testPatron.addCheckout(newCopy1);
    assertTrue((testPatron.getCopies()).contains(newCopy1));
  }

  @Test
  public void addCheckout_returnsCheckOutDate() {
    Copy newCopy = new Copy(1);
    Patron newPatron = new Patron("Jimmy");
    newCopy.save();
    newPatron.save();
    assertEquals(newCopy.getCheckoutDate(), "2016-03-03");
  }

}
