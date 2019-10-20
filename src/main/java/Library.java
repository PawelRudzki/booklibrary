import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class Library {

    private final double oneDayPenalty = 0.80;
    private List<LibraryBook> libraryWarehouse;
    private List<Customer> customerList;

    public void lendBook(LibraryBook libraryBook){

    }
    
    public void acceptBook(LibraryBook libraryBook) {
        Optional<LibraryBook> exists = libraryWarehouse
                .stream()
                .filter(a -> a.getBookID() == libraryBook.getBookID())
                .findAny();
        if (!exists.isPresent()) {
            throw new IllegalArgumentException("This book is not from our library.");
        } else {

            long daysReturnedAfterDeadline = dateDifference(libraryBook.getBorrrowDate());
            if (daysReturnedAfterDeadline>libraryBook.getSingleBorrowingDuration()){
                libraryBook.getBorrowedBy().setAccountBalance(
                        libraryBook.getBorrowedBy().getAccountBalance() + daysReturnedAfterDeadline* oneDayPenalty);
        }
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

    public static long dateDifference(Date date) {
        Date currentDate = new Date();
        long difference = Math.abs(currentDate.getTime() - date.getTime());
        return difference / ((long) (1000 * 60 * 60 * 24));
    }


}
