import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Date;


public class CopyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void copy_instantiatesCorrectly_true() {
    Copy newCopy = new Copy(1);
    assertTrue(newCopy instanceof Copy);
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Copy.all().size(), 0);
  }

  @Test
  public void getPatrons_returnsAllCopies_ArrayList() {
    Copy myCopy = new Copy(1);
    myCopy.save();
    Patron myPatron = new Patron("Jimmy");
    myPatron.save();

    myPatron.addCheckout(myCopy);
    List<Patron> savedPatrons = myCopy.getPatrons();
    assertEquals(savedPatrons.size(), 1);
  }

  @Test
  public void findByDueDate_returnsCopiesByDueDate() {
    Copy newCopy1 = new Copy(1);
    Patron testPatron = new Patron("Jimmy");
    testPatron.save();
    newCopy1.save();
    testPatron.addCheckout(newCopy1);
    assertTrue((newCopy1.getDueDate()).equals("2016-03-13"));
  }

  @Test
  public void addCheckout_returnsCheckOutDate() {
    Copy newCopy = new Copy(1);
    Patron newPatron = new Patron("Jimmy");
    newCopy.save();
    newPatron.save();
    newPatron.addCheckout(newCopy);
    assertEquals(newCopy.getCheckoutDate(), "2016-03-02");
  }

}
