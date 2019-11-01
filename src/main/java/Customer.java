import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class Customer extends LibraryTypes {

    private int id;
    private String name;
    private String lastName;
    private List<Integer> booksBorrowedList;
    private double accountBalance;


    public void returnBook(Library library, int libraryBookId) {
        if(booksBorrowedList.contains(libraryBookId)) {
            library.acceptBook(libraryBookId);
        } else {
            throw new IllegalArgumentException("You don't have book of this ID!");
        }
    }


    public void borrowBook(Library library, LibraryBook libraryBook) {
        library.lendBook(this, libraryBook);
    }

    public boolean isLimitOfBooksBorrowedReached(Library library){
        List<LibraryBook> booksBorrowedByCustomer = library.getLibraryWarehouse()
                .stream()
                .filter(a -> a.getBorrowedBy() == this.getId())
                .collect(Collectors.toList());
        if (booksBorrowedByCustomer.size()<=library.getBOOKS_BORROWED_LIMIT()){
            return false;
        } else{
            return true;
        }
    }


    public void writeToXML(XMLStreamWriter streamWriter, boolean writeBorrowingDetails)
            throws XMLStreamException {

        streamWriter.writeStartElement("customer");
        streamWriter.writeAttribute("id", String.valueOf(this.getId()));

        streamWriter.writeStartElement("name");
        streamWriter.writeCharacters(this.getName());
        streamWriter.writeEndElement();

        streamWriter.writeStartElement("lastName");
        streamWriter.writeCharacters(this.getLastName());
        streamWriter.writeEndElement();


        if (writeBorrowingDetails) {

            streamWriter.writeStartElement("accountBalance");
            streamWriter.writeCharacters(String.format("%.2f", this.getAccountBalance()));
            streamWriter.writeEndElement();

            streamWriter.writeStartElement("borrowedBooks");
            for (int libraryBookId: this.getBooksBorrowedList()) {
                streamWriter.writeStartElement("borrowedBook");
                streamWriter.writeCharacters(String.valueOf(libraryBookId));
                streamWriter.writeEndElement();
            }
            streamWriter.writeEndElement();
        }
        streamWriter.writeEndElement();
    }


    @Override
    public String toString() {
        return id + " " + name + " " + lastName;
    }


}
