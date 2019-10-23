import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        customer.giveBackBook(library, customer.getBooksBorrowedList().get(0));

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
        customer.giveBackBook(library, customer.getBooksBorrowedList().get(0));
        customer.borrowBook(library, library.getLibraryWarehouse().get(2));


        //then
        assertEquals(100, (int) customer.getAccountBalance());

    }

    @Test
    public void createRaportOfBooksKeptTooLong() throws ParseException, IOException, XMLStreamException {


        //given
        BooksContainer booksContainer = new BooksContainer();

        Customer customer = booksContainer.getCustomer();
        Customer customer2 = booksContainer.getCustomer();
        Customer customer3 = booksContainer.getCustomer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library.addCustomer(customer);
        library.addCustomer(customer2);
        library.addCustomer(customer3);
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());

        customer.borrowBook(library, library.getLibraryWarehouse().get(0));
        customer.borrowBook(library, library.getLibraryWarehouse().get(1));
        customer.borrowBook(library, library.getLibraryWarehouse().get(2));
        customer.borrowBook(library, library.getLibraryWarehouse().get(3));
        customer2.borrowBook(library, library.getLibraryWarehouse().get(4));
        customer2.borrowBook(library, library.getLibraryWarehouse().get(5));
        customer2.borrowBook(library, library.getLibraryWarehouse().get(6));
        customer2.borrowBook(library, library.getLibraryWarehouse().get(7));
        customer3.borrowBook(library, library.getLibraryWarehouse().get(8));
        customer3.borrowBook(library, library.getLibraryWarehouse().get(9));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        library.getLibraryWarehouse().get(0).setBorrowDate(simpleDateFormat.parse("2018-01-02"));
        library.getLibraryWarehouse().get(1).setBorrowDate(simpleDateFormat.parse("2019-02-22"));
        library.getLibraryWarehouse().get(2).setBorrowDate(simpleDateFormat.parse("2018-03-21"));
        library.getLibraryWarehouse().get(3).setBorrowDate(simpleDateFormat.parse("2018-05-22"));
        library.getLibraryWarehouse().get(4).setBorrowDate(simpleDateFormat.parse("2018-06-25"));
        library.getLibraryWarehouse().get(5).setBorrowDate(simpleDateFormat.parse("2018-08-15"));
        library.getLibraryWarehouse().get(6).setBorrowDate(simpleDateFormat.parse("2019-10-14"));
        library.getLibraryWarehouse().get(7).setBorrowDate(simpleDateFormat.parse("2019-10-15"));
        library.getLibraryWarehouse().get(8).setBorrowDate(simpleDateFormat.parse("2018-10-12"));
        library.getLibraryWarehouse().get(9).setBorrowDate(simpleDateFormat.parse("2019-10-20"));


        //when
        library.createRaportOfBooksKeptTooLong();

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