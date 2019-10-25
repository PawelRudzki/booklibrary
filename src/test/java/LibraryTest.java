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
        library.addBook(booksContainer.getLibraryBook());
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
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());

        //then
        assertEquals(5, library.getLibraryWarehouse().size());
    }

    @Test
    public void removeBook() {
//given
        BooksContainer booksContainer = new BooksContainer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.removeBook(library.getLibraryWarehouse().get(4));
        library.removeBook(library.getLibraryWarehouse().get(3));

        //then
        assertEquals(3, library.getLibraryWarehouse().size());

    }

    @Test(expected = IllegalStateException.class)
    public void removeBookExeption() {
        //given
        BooksContainer booksContainer = new BooksContainer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.removeBook(booksContainer.getLibraryBook());


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

        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
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

        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
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
        Library library = booksContainer.getLibraryWithBooksAndCustomers(1000, 200);
        booksContainer.simulateUsageOfTheLibrary(library);

        //when
        library.createBooksRaport("ABR");
        library.createBooksRaport("KBR");
        library.createCustomerRaport("ACR");
        library.createCustomerRaport("NBCR");

        //then

    }



    @Test
    public void addLibraryBooksFromXMlTest() throws ParserConfigurationException, SAXException, IOException {

        //given
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addLibraryBooksFromXMl("writeBooksInput");


        //then
//        for(LibraryBook libraryBook : library.getLibraryWarehouse()){
//            System.out.println(libraryBook);
//        }

        assertEquals(4, library.getLibraryWarehouse().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addLibraryBooksFromXMlEmptyListTest() throws ParserConfigurationException, SAXException, IOException {

        //given
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addLibraryBooksFromXMl("emptyFile");

        //then

    }

    @Test
    public void dateDifference() {
    }

}