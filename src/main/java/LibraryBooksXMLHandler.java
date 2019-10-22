import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LibraryBooksXMLHandler extends DefaultHandler {


    @Getter
    private List<LibraryBook> libraryBookList = null;
    private LibraryBook tmpLibraryBook = null;

    private boolean bisbn = false;
    private boolean btitle = false;
    private boolean bauthor = false;
    private boolean bclosingDate = false;
    private boolean bpublicationYear = false;
    private boolean bpublishingHouse = false;
    private boolean bcategory = false;
    private boolean bsingleBorrowingDuartion = false;
    private boolean bborrowDate = false;
    private boolean bborrowedBy = false;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {


        if ("libraryBook".equals(qName)) {
            int id = Integer.valueOf(attributes.getValue("id"));
            tmpLibraryBook = new LibraryBook();
            tmpLibraryBook.setId(id);
        }

        if (libraryBookList == null) {
            libraryBookList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("isbn")) {
            bisbn = true;
        } else if (qName.equalsIgnoreCase("title")) {
            btitle = true;
        } else if (qName.equalsIgnoreCase("author")) {
            bauthor = true;
        } else if (qName.equalsIgnoreCase("publicationYear")) {
            bpublicationYear = true;
        } else if (qName.equalsIgnoreCase("publishingHouse")) {
            bpublishingHouse = true;
        } else if (qName.equalsIgnoreCase("category")) {
            bcategory = true;
        } else if (qName.equalsIgnoreCase("singleBorrowingDuration")) {
            bsingleBorrowingDuartion = true;
        } else if (qName.equalsIgnoreCase("borrowDate")) {
            bborrowDate = true;
        } else if (qName.equalsIgnoreCase("borrowedBy")) {
            bborrowedBy = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("libraryBook")) {
            libraryBookList.add(tmpLibraryBook);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (bisbn) {
            tmpLibraryBook.getBook().setIsbn(String.valueOf(ch, start, length));
            bisbn = false;
        }
        if (btitle) {
            tmpLibraryBook.getBook().setTitle(String.valueOf(ch, start, length));
            btitle = false;
        }
        if (bauthor) {
            String[] tableOfAuthor = String.valueOf(ch, start, length).split(" ");
            tmpLibraryBook.getBook().getAuthors().add(new Author(tableOfAuthor[0], tableOfAuthor[1]));
            bauthor = false;
        }
        if (bpublicationYear) {
            tmpLibraryBook.getBook().setPublicationYear(String.valueOf(ch, start, length));
            bpublicationYear = false;
        }
        if (bpublishingHouse) {
            tmpLibraryBook.getBook().setPublishingHouse(String.valueOf(ch, start, length));
            bpublishingHouse = false;
        }
        if (bcategory) {
            tmpLibraryBook.getBook().setBookCategory(BookCategory.valueOf(String.valueOf(ch, start, length)));
            bcategory = false;
        }
        if (bsingleBorrowingDuartion) {
            tmpLibraryBook.setSingleBorrowingDuration(Integer.valueOf(String.valueOf(ch, start, length)));
            bsingleBorrowingDuartion = false;
        }
        if (bborrowDate) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if (Integer.valueOf(String.valueOf(ch, start, length))!=0) {
                tmpLibraryBook.setBorrowDate(Date.valueOf(String.valueOf(ch, start, length)));
            } else{
                tmpLibraryBook.setBorrowDate(null);
            }
            bborrowDate = false;
        }
        if (bborrowedBy) {
            if (Integer.valueOf(String.valueOf(ch, start, length))!=0) {
                String[] tableOfCustomer = String.valueOf(ch, start, length).split(" ");
            tmpLibraryBook.setBorrowedBy(new Customer(Integer.valueOf(tableOfCustomer[0]), tableOfCustomer[1],
                    tableOfCustomer[2], new ArrayList<>(), 0.0));
            } else{
                tmpLibraryBook.setBorrowedBy(null);
            }
            bborrowedBy = false;
        }
    }

    @Override
    public void endDocument() {

    }
}

