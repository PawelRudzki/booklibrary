import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LibraryHelperTest {


    @Test
    public void writeBooksToXMLTest() throws IOException, XMLStreamException, ParserConfigurationException, SAXException {

        //given
        BooksContainer booksContainer = new BooksContainer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        for(int i=0; i<3;i++) {
            library.addBook(booksContainer.getLibraryBook(library));
        }


        LibraryHelper libraryHelper = new LibraryHelper();


        //when
        libraryHelper.writeLibraryTypeListToXML("writeBooksOutput.xml",
                library.getLibraryWarehouse(), true);


        //then

    }

    @Test
    public void loadListFromXMLAndWriteIt() throws IOException, XMLStreamException, SAXException, ParserConfigurationException {
        //given
        BooksContainer booksContainer = new BooksContainer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        for(int i=0; i<4;i++) {
            library.addBook(booksContainer.getLibraryBook(library));
        }

        //load books from xml
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        LibraryBooksXMLHandler myHandler = new LibraryBooksXMLHandler();

        //save list of books from input file
        saxParser.parse(new File("writeBooksInput.xml"), myHandler);
        List<LibraryBook> libraryInputBookList = myHandler.getLibraryBookList();

        //save list of books from output file
        saxParser.parse(new File("writeBooksOutput.xml"), myHandler);
        List<LibraryBook> libraryOutputBookList = myHandler.getLibraryBookList();

        //when
        LibraryHelper libraryHelper = new LibraryHelper();
        libraryHelper.writeLibraryTypeListToXML("writeBooksOutput.xml", library.getLibraryWarehouse(),
                true);


        //then

        assertEquals(libraryInputBookList, libraryOutputBookList);
    }

    @Test
    public void dateDifferenceToNow() throws ParseException {

        //given
        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        //when
        long result1 = library.getLibraryHelper().dateDifferenceToNow(simpleDateFormat.parse("2018-10-23"));
        long result2 = library.getLibraryHelper().dateDifferenceToNow(simpleDateFormat.parse("2019-12-02"));
        long result3 = library.getLibraryHelper().dateDifferenceToNow(simpleDateFormat.parse("2019-10-23"));

        //then
        assertEquals(371l, result1);
        assertEquals(-33l, result2);
        assertEquals(6l, result3);

    }
}