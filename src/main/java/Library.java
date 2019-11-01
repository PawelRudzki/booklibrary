import lombok.AllArgsConstructor;
import lombok.Getter;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Library {

    private final double ONE_DAY_PENALTY = 0.10;
    private final int BOOKS_BORROWED_LIMIT = 5;
    private List<LibraryBook> libraryWarehouse;
    private List<Customer> customerList;
    private final LibraryHelper libraryHelper = new LibraryHelper();

    public void lendBook(Customer customer, LibraryBook libraryBook) {

        //Is given customer ID in this library?
        if (!libraryHelper.isThisIdInGivenList(customer.getId(), this.getCustomerList())) {
            throw new IllegalArgumentException("This is not customer of our library!");
        }

        //Is given customer penalty balance below limit?
        if (customer.getAccountBalance() > 20) {
            throw new IllegalStateException("This customer reached a limit of account balance");
        }

        //Does given customer reach limit of books borrowed?
        if (customer.isLimitOfBooksBorrowedReached(this)) {
            throw new IllegalStateException("This customer reached limit of books borrowed from out library");
        } else {

            //Do we have requested book in this library?
            if (!libraryHelper.isThisIdInGivenList(libraryBook.getId(), this.getLibraryWarehouse())) {
                throw new IllegalArgumentException("Book was not found in our library");
            } else {

                if (!libraryHelper.isThisBookAlreadyBorrowed(libraryBook)) {
                    libraryBook.setBorrowedBy(customer.getId());
                    libraryBook.setBorrowDate(new Date());
                    customer.getBooksBorrowedList().add(libraryBook.getId());
                } else {
                    throw new IllegalStateException("This book is already borrowed.");
                }

            }
        }
    }

    public void acceptBook(int libraryBookId) {
        LibraryHelper libraryHelper = new LibraryHelper();
        if (!libraryHelper.isThisIdInGivenList(libraryBookId, this.getLibraryWarehouse())) {
            throw new IllegalArgumentException("This book is not from our library.");
        } else {
            LibraryBook tmpLibraryBook = (LibraryBook) libraryHelper.returnLibraryTypeWithGivenID(libraryBookId, this.libraryWarehouse);
            long daysReturnedAfterDeadline = libraryHelper.daysFromGivenDateTillNow(tmpLibraryBook.getBorrowDate());
            if (daysReturnedAfterDeadline > tmpLibraryBook.getSingleBorrowingDuration()) {
                Customer tmpCustomer = (Customer) libraryHelper.returnLibraryTypeWithGivenID(tmpLibraryBook.getBorrowedBy(), this.customerList);
                tmpCustomer.setAccountBalance(
                        tmpCustomer.getAccountBalance() + daysReturnedAfterDeadline * ONE_DAY_PENALTY);
            }
            tmpLibraryBook.setBorrowedBy(0);
            tmpLibraryBook.setBorrowDate(null);
        }
    }

    public void loadLibraryBooksFromXML(String inputFile) throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        LibraryBooksXMLHandler libraryBooksXMLHandler = new LibraryBooksXMLHandler();

        try {
            saxParser.parse(new File(inputFile + ".xml"), libraryBooksXMLHandler);
        } catch (SAXParseException e) {
            throw new IllegalArgumentException("Problem with input file. It's corrupted or empty.");
        }
        List<LibraryBook> tmpList = libraryBooksXMLHandler.getLibraryBookList();
        if (tmpList.size() > 0) {
            for (LibraryBook libraryBook : tmpList) {
                addBook(libraryBook);
            }
        } else {
            throw new IllegalArgumentException("No books found in input source.");
        }
    }

    public void createLibraryTypeRaport(String raportType)
            throws IOException, XMLStreamException {

        switch (raportType) {

            //LibraryBooks raports
            case "ABR": {
                libraryHelper.createLibraryTypeRaport("ALL BOOKS RAPORT", libraryWarehouse, true);
                break;
            }
            case "KBR": {
                List<LibraryBook> bookRaportList = libraryWarehouse
                        .stream()
                        .filter(b -> b.getBorrowDate() != null)
                        .filter(a -> libraryHelper.daysFromGivenDateTillNow(a.getBorrowDate()) > a.getSingleBorrowingDuration())
                        .collect(Collectors.toList());
                this.getLibraryHelper().createLibraryTypeRaport("KEPT BOOKS RAPORT", bookRaportList, true);
                break;
            }

            //Customers raports
            case ("ACR"): {
                getLibraryHelper().createLibraryTypeRaport("ALL CUSTOMERS RAPORT", customerList, true);
                break;
            }
            case ("NBCR"): {
                List<Customer> customerRaportList = customerList
                        .stream()
                        .filter(a -> a.getAccountBalance() > 0)
                        .collect(Collectors.toList());
                this.getLibraryHelper().createLibraryTypeRaport("NEGATIVE BALANCE CUSTOMER RAPORT", customerRaportList, true);
                break;
            }
        }
    }

    public int getNextLibraryBookId() {
        do {
            int tmpId = libraryWarehouse.size() + 1;
            if (!libraryHelper.isThisIdInGivenList(tmpId, libraryWarehouse)) {
                return tmpId;
            }
        } while (true);
    }

    public int getNextCustomerId() {
        do {
            int tmpId = customerList.size() + 1;
            if (!libraryHelper.isThisIdInGivenList(tmpId, customerList)) {
                return tmpId;
            }
        } while (true);
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

}
