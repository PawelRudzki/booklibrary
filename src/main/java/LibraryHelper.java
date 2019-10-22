import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

public class LibraryHelper<T extends LibraryTypes> {

    public boolean isThisIdInGivenList(int id, List<T> list) {
        Optional<T> exists = list
                .stream()
                .filter(a -> a.getId() == id)
                .findAny();
        if (!exists.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public void add(T libraryType, List<T> list) {
        if (!isThisIdInGivenList(libraryType.getId(), list)) {
            list.add(libraryType);
        } else {
            throw new IllegalStateException("Can't add element because it already exists.");
        }
    }

    public void remove(T libraryType, List<T> list) {
        if (isThisIdInGivenList(libraryType.getId(), list)) {
            list.remove(libraryType);
        } else {
            throw new IllegalStateException("Can't remove element because it already exists.");
        }

    }

    public void writeBookListToXML(String outputFile, List<LibraryBook> bookList, Boolean writeBorrowingDetails) throws XMLStreamException, FileNotFoundException {

        OutputStream os = new FileOutputStream(new File(outputFile));
        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(os, "utf-8");


        streamWriter.writeStartDocument("1.0");
        streamWriter.writeStartElement("books");

        for (LibraryBook libraryBook : bookList) {

            streamWriter.writeStartElement("libraryBook");
            streamWriter.writeAttribute("id", String.valueOf(libraryBook.getId()));

            streamWriter.writeStartElement("isbn");
            streamWriter.writeCharacters(libraryBook.getBook().getIsbn());
            streamWriter.writeEndElement();

            streamWriter.writeStartElement("title");
            streamWriter.writeCharacters(libraryBook.getBook().getTitle());
            streamWriter.writeEndElement();

            streamWriter.writeStartElement("authors");
            for (Author author : libraryBook.getBook().getAuthors()) {
                streamWriter.writeStartElement("author");
                streamWriter.writeCharacters(author.toString());
                streamWriter.writeEndElement();
            }
            streamWriter.writeEndElement();

            streamWriter.writeStartElement("publicationYear");
            streamWriter.writeCharacters(libraryBook.getBook().getPublicationYear());
            streamWriter.writeEndElement();

            streamWriter.writeStartElement("publishingHouse");
            streamWriter.writeCharacters(libraryBook.getBook().getPublishingHouse());
            streamWriter.writeEndElement();

            streamWriter.writeStartElement("category");
            streamWriter.writeCharacters(libraryBook.getBook().getBookCategory().toString());
            streamWriter.writeEndElement();

            if (writeBorrowingDetails) {

                streamWriter.writeStartElement("singleBorrowingDuration");
                streamWriter.writeCharacters(String.valueOf(libraryBook.getSingleBorrowingDuration()));
                streamWriter.writeEndElement();

                streamWriter.writeStartElement("borrowDate");
                if (libraryBook.getBorrowDate() != null) {
                    streamWriter.writeCharacters(libraryBook.getBorrowDate().toString());
                } else {
                    streamWriter.writeCharacters("-------------");
                }
                streamWriter.writeEndElement();

                streamWriter.writeStartElement("borrowedBy");
                if (libraryBook.getBorrowedBy() != null) {
                    streamWriter.writeCharacters(libraryBook.getBorrowedBy().toString());
                } else {
                    streamWriter.writeCharacters("-------------");
                }
                streamWriter.writeEndElement();
            }

            streamWriter.writeEndElement();
        }

        streamWriter.writeEndElement();
        streamWriter.writeEndDocument();

        streamWriter.close();
    }

}

