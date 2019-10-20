import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Library {

    private final double oneDayPenalty = 0.02;
    private List<LibraryBook> libraryWarehouse;
    private List<Customer> customerList;

    public void getDebtRaport() {
        customerList
                .stream()
                .filter(a -> a.getAccountBalance() > 0)
                .forEach(b -> System.out.println(b.toString() + " debt: " + b.getAccountBalance()+"zł"));

        double debtValueTotal = customerList
                .stream()
                .mapToDouble(Customer::getAccountBalance)
                .reduce(0, (subtotal, b) -> subtotal + b);
        System.out.println("Total: " + debtValueTotal +"zł");
    }

    public void lendBook(Customer customer, LibraryBook libraryBook) {
        Optional<Customer> customerExists = customerList
                .stream()
                .filter(a -> a.getCustomerID() == customer.getCustomerID())
                .findAny();
        if (!customerExists.isPresent()) {
            throw new IllegalArgumentException("This is not our customer!");
        }
        if (customer.getAccountBalance() > 20) {
            throw new IllegalStateException("You owe us a lot of money!");
        }
        List<LibraryBook> booksBorrowedByCustomer = libraryWarehouse
                .stream()
                .filter(b -> b.getBorrowedBy() != null)
                .filter(a -> a.getBorrowedBy().getCustomerID() == customer.getCustomerID())
                .collect(Collectors.toList());
        if (booksBorrowedByCustomer.size() > 5) {
            throw new IllegalStateException("Sorry, you have too many books from us already!");
        } else {

            Optional<LibraryBook> exists = libraryWarehouse
                    .stream()
                    .filter(a -> a.getBookID() == libraryBook.getBookID())
                    .findAny();
            if (!exists.isPresent()) {
                throw new IllegalArgumentException("We don't have this book!");
            } else {
                LibraryBook bookToLend = libraryWarehouse
                        .stream()
                        .filter(a -> a.getBookID() == libraryBook.getBookID())
                        .findAny()
                        .orElse(null);
                if (bookToLend.getBorrowedBy() == null) {
                    bookToLend.setBorrowedBy(customer);
                    bookToLend.setBorrrowDate(new Date());
                    customer.getBooksBorrowedList().add(bookToLend);

                }
            }
        }


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
            if (daysReturnedAfterDeadline > libraryBook.getSingleBorrowingDuration()) {
                libraryBook.getBorrowedBy().setAccountBalance(
                        libraryBook.getBorrowedBy().getAccountBalance() + daysReturnedAfterDeadline * oneDayPenalty);
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
        } else {
            throw new IllegalStateException("We already have this book!");
        }
    }

    public void removeBook(LibraryBook newLibraryBook) {
        Optional<LibraryBook> exists = libraryWarehouse
                .stream()
                .filter(a -> a.getBookID() == newLibraryBook.getBookID())
                .findAny();
        if (exists.isPresent()) {
            libraryWarehouse.remove(newLibraryBook);
        } else {
            throw new IllegalStateException("We don't have this book! We can't remove it");
        }
    }

    public void addCustomer(Customer customer) {
        Optional<Customer> exists = customerList
                .stream()
                .filter(a -> a.getCustomerID() == customer.getCustomerID())
                .findAny();
        if (!exists.isPresent()) {
            customerList.add(customer);
        } else {
            throw new IllegalStateException("This customer already exists!");
        }
    }

    public void removeCustomer(Customer customer) {
        Optional<Customer> exists = customerList
                .stream()
                .filter(a -> a.getCustomerID() == customer.getCustomerID())
                .findAny();
        if (exists.isPresent()) {
            customerList.remove(customer);
        } else {
            throw new IllegalStateException("We don't have this customer on our list. We can't remove it");
        }
    }

    public static long dateDifference(Date date) {
        Date currentDate = new Date();
        long difference = Math.abs(currentDate.getTime() - date.getTime());
        return difference / ((long) (1000 * 60 * 60 * 24));
    }


}
