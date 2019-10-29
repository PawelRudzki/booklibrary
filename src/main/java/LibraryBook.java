import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class LibraryBook extends LibraryTypes {

    private Book book;
    private int id;
    private int singleBorrowingDuration;
    private Date borrowDate;
    private int borrowedBy;


    public LibraryBook() {
        book = new Book();
    }

    @Override
    public String toString() {
        return book + " ID: " + id + " " + singleBorrowingDuration
                + " " + borrowDate + " " + borrowedBy;
    }

    public void writeToXML(XMLStreamWriter streamWriter, boolean writeBorrowingDetails)
            throws XMLStreamException {

        streamWriter.writeStartElement("libraryBook");
        streamWriter.writeAttribute("id", String.valueOf(this.getId()));

        streamWriter.writeStartElement("isbn");
        streamWriter.writeCharacters(this.getBook().getIsbn());
        streamWriter.writeEndElement();

        streamWriter.writeStartElement("title");
        streamWriter.writeCharacters(this.getBook().getTitle());
        streamWriter.writeEndElement();

        streamWriter.writeStartElement("authors");
        for (Author author : this.getBook().getAuthors()) {
            streamWriter.writeStartElement("author");
            streamWriter.writeCharacters(author.toString());
            streamWriter.writeEndElement();
        }
        streamWriter.writeEndElement();

        streamWriter.writeStartElement("publicationYear");
        streamWriter.writeCharacters(this.getBook().getPublicationYear());
        streamWriter.writeEndElement();

        streamWriter.writeStartElement("publishingHouse");
        streamWriter.writeCharacters(this.getBook().getPublishingHouse());
        streamWriter.writeEndElement();

        streamWriter.writeStartElement("category");
        streamWriter.writeCharacters(this.getBook().getBookCategory().toString());
        streamWriter.writeEndElement();

        if (writeBorrowingDetails) {

            streamWriter.writeStartElement("singleBorrowingDuration");
            streamWriter.writeCharacters(String.valueOf(this.getSingleBorrowingDuration()));
            streamWriter.writeEndElement();

            streamWriter.writeStartElement("borrowDate");
            if (this.getBorrowDate() != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                streamWriter.writeCharacters(simpleDateFormat.format(this.getBorrowDate()));
            } else {
                streamWriter.writeCharacters("0");
            }
            streamWriter.writeEndElement();

            streamWriter.writeStartElement("borrowedBy");
            if (this.getBorrowedBy() != 0) {
                streamWriter.writeCharacters(String.valueOf(this.getBorrowedBy()));
            } else {
                streamWriter.writeCharacters("0");
            }
            streamWriter.writeEndElement();
        }

        streamWriter.writeEndElement();
    }

}


