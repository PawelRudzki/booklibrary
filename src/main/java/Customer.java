import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.List;

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
        library.acceptBook(libraryBookId);
    }


    public void borrowBook(Library library, LibraryBook libraryBook) {
        library.lendBook(this, libraryBook);
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
