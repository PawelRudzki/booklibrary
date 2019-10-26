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
import java.util.Random;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Library {

    private final double ONE_DAY_PENALTY = 0.10;
    private List<LibraryBook> libraryWarehouse;
    private List<Customer> customerList;

    private final LibraryHelper libraryHelper = new LibraryHelper();

    public void lendBook(Customer customer, LibraryBook libraryBook) {
        LibraryHelper libraryHelper = new LibraryHelper();

        //Is given customer ID in this library?
        if (!libraryHelper.isThisIdInGivenList(customer.getId(), this.getCustomerList())) {
            throw new IllegalArgumentException("This is not our customer!");
        }

        //Is given customer penalty balance below limit?
        if (customer.getAccountBalance() > 20) {
            throw new IllegalStateException("This customer owes us a lot of money!");
        }

        //Does given customer have less than 5 books from this library?
        List<LibraryBook> booksBorrowedByCustomer = libraryWarehouse
                .stream()
                .filter(b -> b.getBorrowedBy() != null)
                .filter(a -> a.getBorrowedBy().getId() == customer.getId())
                .collect(Collectors.toList());
        if (booksBorrowedByCustomer.size() > 5) {
            throw new IllegalStateException("Sorry, you have too many books from us already!");
        } else {

            //Do we have requested book in this library?
            if (!libraryHelper.isThisIdInGivenList(libraryBook.getId(), this.getLibraryWarehouse())) {
                throw new IllegalArgumentException("We don't have this book!");
            } else {

                //Is requested book not borrowed at the moment?
                if (libraryBook.getBorrowedBy() == null) {
                    libraryBook.setBorrowedBy(customer);
                    libraryBook.setBorrowDate(new Date());
                    customer.getBooksBorrowedList().add(libraryBook);
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
            long daysReturnedAfterDeadline = libraryHelper.dateDifferenceToNow(libraryBook.getBorrowDate());
            if (daysReturnedAfterDeadline > libraryBook.getSingleBorrowingDuration()) {
                libraryBook.getBorrowedBy().setAccountBalance(
                        libraryBook.getBorrowedBy().getAccountBalance() + daysReturnedAfterDeadline * ONE_DAY_PENALTY);
            }
            libraryBook.setBorrowedBy(null);
            libraryBook.setBorrowDate(null);
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

            //before adding any book to libraryWarehouse I get ride of temporary customer
            //objects assigned to books by handler and connect them with customers from
            //customerSList of library
            for (LibraryBook libraryBook : tmpList) {
                if (libraryBook.getBorrowedBy() != null) {
                    int id = libraryBook.getBorrowedBy().getId();
                    if (libraryHelper.isThisIdInGivenList(id, customerList)) {
                        libraryBook.setBorrowedBy(customerList
                                .stream()
                                .filter(a -> a.getId() == id)
                                .findAny()
                                .orElse(null));
                    }
                }
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
                        .filter(a -> libraryHelper.dateDifferenceToNow(a.getBorrowDate()) > a.getSingleBorrowingDuration())
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

    public int getNextLibraryBookId(){
        return libraryWarehouse.size()+1;
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
