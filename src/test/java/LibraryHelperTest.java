import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LibraryHelperTest {


    @Test
    public void writeBooksToXMLTest() throws IOException, XMLStreamException, ParserConfigurationException, SAXException {

        //given
        BooksContainer booksContainer = new BooksContainer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        LibraryHelper libraryHelper = new LibraryHelper();


        //when
        libraryHelper.writeBookListToXML("writeBooksOutput.xml",
                library.getLibraryWarehouse(),true);


        //then

    }

    @Test
    public void loadListFromXMLAndWriteIt() throws IOException, XMLStreamException, SAXException, ParserConfigurationException {
        //given
        BooksContainer booksContainer = new BooksContainer();

        Library library = new Library(new ArrayList<>(), new ArrayList<>());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());
        library.addBook(booksContainer.getLibraryBook());


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
        libraryHelper.writeBookListToXML("writeBooksOutput.xml", library.getLibraryWarehouse(),
                true);


        //then

        assertEquals(libraryInputBookList, libraryOutputBookList);
    }
}