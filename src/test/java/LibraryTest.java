import org.junit.Test;

import java.util.ArrayList;

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
    public void removeBook(){
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
    @Test (expected = IllegalStateException.class)
    public void removeBookException(){
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
        BooksContainer  booksContainer = new BooksContainer();
        Customer customer = booksContainer.getCustomer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        //when
    }
    @Test
    public void removeCustomer(){
        //given
        BooksContainer  booksContainer = new BooksContainer();
        Customer customer = booksContainer.getCustomer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addCustomer(booksContainer.getCustomer());
        library.removeCustomer(library.getCustomerList().get(0));

        //then
        assertEquals(0, library.getCustomerList().size());

    }
    @Test (expected = IllegalStateException.class)
    public void removeCustomerException() {
        //given
        BooksContainer  booksContainer = new BooksContainer();
        Customer customer = booksContainer.getCustomer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());

        //when
        library.addCustomer(booksContainer.getCustomer());
        library.removeCustomer(booksContainer.getCustomer());

        //then
        assertEquals(0, library.getCustomerList().size());

    }



    @Test
    public void dateDifference() {
    }
}