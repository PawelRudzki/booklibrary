import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Library {

    private final double ONE_DAY_PENALTY = 0.10;
    private List<LibraryBook> libraryWarehouse;
    private List<Customer> customerList;

    private final LibraryHelper libraryHelper = new LibraryHelper();

    public void getDebtRaport() {
        customerList
                .stream()
                .filter(a -> a.getAccountBalance() > 0)
                .forEach(b -> System.out.println(b.toString() + " debt: " + b.getAccountBalance() + "zł"));

        double debtValueTotal = customerList
                .stream()
                .mapToDouble(Customer::getAccountBalance)
                .reduce(0, (subtotal, b) -> subtotal + b);
        System.out.println("Total: " + debtValueTotal + "zł");
    }

    public void lendBook(Customer customer, LibraryBook libraryBook) {
        LibraryHelper libraryHelper = new LibraryHelper();
        if (!libraryHelper.isThisIdInGivenList(customer.getId(), this.getCustomerList())) {
            throw new IllegalArgumentException("This is not our customer!");
        }
        if (customer.getAccountBalance() > 20) {
            throw new IllegalStateException("You owe us a lot of money!");
        }
        List<LibraryBook> booksBorrowedByCustomer = libraryWarehouse
                .stream()
                .filter(b -> b.getBorrowedBy() != null)
                .filter(a -> a.getBorrowedBy().getId() == customer.getId())
                .collect(Collectors.toList());
        if (booksBorrowedByCustomer.size() > 5) {
            throw new IllegalStateException("Sorry, you have too many books from us already!");
        } else {
            if (!libraryHelper.isThisIdInGivenList(libraryBook.getId(), this.getLibraryWarehouse())) {
                throw new IllegalArgumentException("We don't have this book!");
            } else {
                LibraryBook bookToLend = libraryWarehouse
                        .stream()
                        .filter(a -> a.getId() == libraryBook.getId())
                        .findAny()
                        .orElse(null);
                if (bookToLend.getBorrowedBy() == null) {
                    bookToLend.setBorrowedBy(customer);
                    bookToLend.setBorrowDate(new Date());
                    customer.getBooksBorrowedList().add(bookToLend);
                } else {
                    throw new IllegalStateException("This book is already borrowed.");
                }
            }
        }
    }

    public void acceptBook(LibraryBook libraryBook) {
        LibraryHelper libraryHelper = new LibraryHelper();
        if (!libraryHelper.isThisIdInGivenList(libraryBook.getId(), this.getLibraryWarehouse())) {
            throw new IllegalArgumentException("This book is not from our library.");
        } else {
            long daysReturnedAfterDeadline = dateDifference(libraryBook.getBorrowDate());
            if (daysReturnedAfterDeadline > libraryBook.getSingleBorrowingDuration()) {
                libraryBook.getBorrowedBy().setAccountBalance(
                        libraryBook.getBorrowedBy().getAccountBalance() + daysReturnedAfterDeadline * ONE_DAY_PENALTY);
            }
            libraryBook.setBorrowedBy(null);
            libraryBook.setBorrowDate(null);
        }

    }

    public void addBook(LibraryBook newLibraryBook) {
        libraryHelper.add(newLibraryBook, this.getLibraryWarehouse());
    }

    public void removeBook(LibraryBook bookToRemove) {
        libraryHelper.remove(bookToRemove, this.getLibraryWarehouse());
    }

    public void addCustomer(Customer customer) {
        libraryHelper.add(customer, this.getCustomerList());
    }

    public void removeCustomer(Customer customer) {
        libraryHelper.remove(customer, this.getCustomerList());
    }

    public static long dateDifference(Date date) {
        Date currentDate = new Date();
        long difference = Math.abs(currentDate.getTime() - date.getTime());
        return difference / ((long) (1000 * 60 * 60 * 24));
    }


}
