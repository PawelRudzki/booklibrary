import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class Library {

    private List<LibraryBook> libraryWarehouse;
    private List<Customer> customerList;

    public void acceptBook(LibraryBook libraryBook) {
        Optional<LibraryBook> exists = libraryWarehouse
                .stream()
                .filter(a -> a.getBookID() == libraryBook.getBookID())
                .findAny();
        if (!exists.isPresent()) {
            throw new IllegalArgumentException("This book is not from our library.");
        } else {

            libraryBook.setBorrowedBy(null);
            libraryBook.setBorrrowDate(null);
        }
    }

    public void addBook(LibraryBook newLibraryBook) {
        Optional<LibraryBook> exists = libraryWarehouse
                .stream()
                .filter(a -> a.getBookID() == newLibraryBook.getBookID())
                .findAny();
        if (!exists.isPresent()) {
            libraryWarehouse.add(newLibraryBook);
        }
    }

    public void addCustomer(Customer customer) {
        Optional<Customer> exists = customerList
                .stream()
                .filter(a -> a.getCustomerID() == customer.getCustomerID())
                .findAny();
        if (!exists.isPresent()) {
            customerList.add(customer);
        }
    }


}
