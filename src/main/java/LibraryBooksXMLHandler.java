import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            if (!String.valueOf(ch, start, length).equals("0")) {
                try {
                    tmpLibraryBook.setBorrowDate((simpleDateFormat.parse(String.valueOf(ch, start, length))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                tmpLibraryBook.setBorrowDate(null);
            }
            bborrowDate = false;
        }
        if (bborrowedBy) {
            if (!String.valueOf(ch, start, length).equals("0")) {
                String[] tableOfCustomer = String.valueOf(ch, start, length).split(" ");
                tmpLibraryBook.setBorrowedBy(Integer.valueOf(tableOfCustomer[0]));
            } else {
                tmpLibraryBook.setBorrowedBy(0);
            }
            bborrowedBy = false;
        }
    }


    @Override
    public void endDocument() {

    }
}

