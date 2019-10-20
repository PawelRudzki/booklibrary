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
    public void addCustomer() {
    }

    @Test
    public void dateDifference() {
    }
}