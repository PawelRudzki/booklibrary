import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

public class LibraryTest {

    @Test
    public void acceptBook() {

        //given
        BooksContainer booksContainer = new BooksContainer();

        Customer customer = booksContainer.getCustomer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
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
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        customer.borrowBook(library, library.getLibraryWarehouse().get(0));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //when
        library.getLibraryWarehouse().get(0).setBorrrowDate(simpleDateFormat.parse("2019-01-22"));
        customer.giveBackBook(library, customer.getBooksBorrowedList().get(0));
        customer.borrowBook(library, library.getLibraryWarehouse().get(2));


        //then
        assertEquals(100, (int) customer.getAccountBalance());

    }


    @Test
    public void dateDifference() {
    }
}