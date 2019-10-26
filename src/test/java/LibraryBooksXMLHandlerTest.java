import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LibraryBooksXMLHandlerTest {

    @Test
    public void libraryBooksFromXMLToListTest() throws IOException, SAXException, ParserConfigurationException {


        //given
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        LibraryBooksXMLHandler myHandler = new LibraryBooksXMLHandler();
        saxParser.parse(new File("writeBooksInput.xml"), myHandler);

        //when
        List<LibraryBook> libraryBookList = myHandler.getLibraryBookList();

        //display list, to be sure what it contains
        for (LibraryBook libraryBook : libraryBookList) {
            System.out.println(libraryBook);
        }

        //then
        assertNotNull(libraryBookList.get(0));
        assertEquals(1000, libraryBookList.size());
    }

    }

