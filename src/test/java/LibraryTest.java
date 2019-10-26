import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LibraryTest {

    @Test
    public void acceptBook() {

        //given
        BooksContainer booksContainer = new BooksContainer();

        Customer customer = booksContainer.getCustomer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library.addCustomer(customer);
        library.addBook(booksContainer.getLibraryBook(library));
        customer.borrowBook(library, library.getLibraryWarehouse().get(0));

        //when
        customer.returnBook(library, customer.getBooksBorrowedList().get(0));

        //then
        assertEquals(1, customer.getBooksBorrowedList().size());


    }

    @Test
    public void addBook() {


        //given
        BooksContainer booksContainer = new BooksContainer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));


        //then
        assertEquals(5, library.getLibraryWarehouse().size());
    }

    @Test
    public void removeBook() {
//given
        BooksContainer booksContainer = new BooksContainer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));

        library.removeBook(library.getLibraryWarehouse().get(4));
        library.removeBook(library.getLibraryWarehouse().get(3));

        //then
        assertEquals(4, library.getLibraryWarehouse().size());

    }

    @Test(expected = IllegalStateException.class)
    public void removeBookExeption() {
        //given
        BooksContainer booksContainer = new BooksContainer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.removeBook(booksContainer.getLibraryBook(library));

        //then
        assertEquals(3, library.getLibraryWarehouse().size());

    }


    @Test
    public void addCustomer() {

        //given
        BooksContainer booksContainer = new BooksContainer();
        Customer customer = booksContainer.getCustomer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        //when
    }

    @Test
    public void removeCustomer() {
        //given
        BooksContainer booksContainer = new BooksContainer();
        Customer customer = booksContainer.getCustomer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addCustomer(booksContainer.getCustomer());
        library.removeCustomer(library.getCustomerList().get(0));

        //then
        assertEquals(0, library.getCustomerList().size());

    }

    @Test(expected = IllegalStateException.class)
    public void removeCustomerException() {
        //given
        BooksContainer booksContainer = new BooksContainer();
        Customer customer = booksContainer.getCustomer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addCustomer(booksContainer.getCustomer());
        library.removeCustomer(booksContainer.getCustomer());

        //then
        assertEquals(0, library.getCustomerList().size());

    }

    @Test(expected = IllegalStateException.class)
    public void lendBookCustomerOverHundredDebt() {

        //given
        BooksContainer booksContainer = new BooksContainer();

        Customer customer = booksContainer.getCustomer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library.addCustomer(customer);

        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));

        customer.borrowBook(library, library.getLibraryWarehouse().get(0));
        customer.borrowBook(library, library.getLibraryWarehouse().get(1));
        customer.borrowBook(library, library.getLibraryWarehouse().get(2));


        //when

        customer.setAccountBalance(120);
        customer.borrowBook(library, library.getLibraryWarehouse().get(3));


        //then

    }

    @Test(expected = IllegalStateException.class)
    public void customerAccountOverTime() throws ParseException {

        //given
        BooksContainer booksContainer = new BooksContainer();

        Customer customer = booksContainer.getCustomer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library.addCustomer(customer);

        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));
        library.addBook(booksContainer.getLibraryBook(library));

        customer.borrowBook(library, library.getLibraryWarehouse().get(0));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //when
        library.getLibraryWarehouse().get(0).setBorrowDate(simpleDateFormat.parse("2011-01-22"));
        customer.returnBook(library, customer.getBooksBorrowedList().get(0));
        customer.borrowBook(library, library.getLibraryWarehouse().get(2));


        //then
        assertEquals(100, (int) customer.getAccountBalance());

    }

    @Test
    public void createAllRaports() throws IOException, XMLStreamException {


        //given
        BooksContainer booksContainer = new BooksContainer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library = booksContainer.getLibraryWithBooksAndCustomers(library, 1000, 200);
        booksContainer.simulateUsageOfTheLibrary(library);

        //when
        library.createLibraryTypeRaport("ABR");
        library.createLibraryTypeRaport("KBR");
        library.createLibraryTypeRaport("ACR");
        library.createLibraryTypeRaport("NBCR");

        //then

    }


    @Test
    public void loadLibraryBooksFromXMlTest() throws ParserConfigurationException, SAXException, IOException {

        //given
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.loadLibraryBooksFromXML("writeBooksInput");


        //then
        for (LibraryBook libraryBook : library.getLibraryWarehouse()) {
            System.out.println(libraryBook);
        }

        assertEquals(1000, library.getLibraryWarehouse().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addLibraryBooksFromXMlEmptyListTest() throws ParserConfigurationException, SAXException, IOException {

        //given
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.loadLibraryBooksFromXML("emptyFile");

        //then

    }

    @Test
    public void dateDifference() {
    }

}