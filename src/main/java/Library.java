import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class Library {

    private List<LibraryBook> libraryWarehouse;
    private List<Customer> customerList;

    public void acceptBook(Customer customer, LibraryBook libraryBook){
        
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
