import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomerTest {

    @Test
    public void borrowBook() {
        //given
        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        BooksContainer booksContainer = new BooksContainer();
        Customer customer = booksContainer.getCustomer(library);
        library.addCustomer(customer);

        library.addBook(booksContainer.getLibraryBook(library));

        //when
        customer.borrowBook(library, library.getLibraryWarehouse().get(0));

        //then
        assertEquals(customer, library.getLibraryWarehouse().get(0).getBorrowedBy());
    }


    @Test
    public void giveBackBook() {

        //given
        BooksContainer booksContainer = new BooksContainer();
        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        Customer customer = booksContainer.getCustomer(library);
        library.addCustomer(customer);
        library.addBook(booksContainer.getLibraryBook(library));
        customer.borrowBook(library, library.getLibraryWarehouse().get(0));

        //when

        customer.returnBook(library, library.getLibraryWarehouse().get(0));

        //then
        assertEquals(null, library.getLibraryWarehouse().get(0).getBorrowedBy());
        assertEquals(1, customer.getBooksBorrowedList().size());
    }

}