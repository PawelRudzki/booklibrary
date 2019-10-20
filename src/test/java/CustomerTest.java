import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void borrowBook() {
        //given
        BooksContainer booksContainer = new BooksContainer();

        Customer customer = booksContainer.getCustomer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library.addBook(booksContainer.getLibraryBook());

        //when
        customer.borrowBook(library, library.getLibraryWarehouse().get(0));

        //then
        assertEquals(customer, library.getLibraryWarehouse().get(0).getBorrowedBy());
    }


    @Test
    public void giveBackBook() {

        //given
        BooksContainer booksContainer = new BooksContainer();

        Customer customer = booksContainer.getCustomer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library.addBook(booksContainer.getLibraryBook());
        customer.borrowBook(library, library.getLibraryWarehouse().get(0));

        //when

        customer.giveBackBook(library, library.getLibraryWarehouse().get(0));

        //then
        assertEquals(null, library.getLibraryWarehouse().get(0).getBorrowedBy());
        assertEquals(1, customer.getBooksBorrowedList().size());
    }

}