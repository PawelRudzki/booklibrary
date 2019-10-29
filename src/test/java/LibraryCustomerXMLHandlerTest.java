import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class LibraryCustomerXMLHandlerTest {

    @Test
    public void libraryCustomerFromXMLToListTest() throws IOException, SAXException, ParserConfigurationException {


        //given
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        LibraryCustomerXMLHandler myHandler = new LibraryCustomerXMLHandler();
        saxParser.parse(new File("writeCustomerInput.xml"), myHandler);

        //when
        List<Customer> customerList = myHandler.getCustomerList();

        //display list, to be sure what it contains
        for (Customer customer : customerList) {
            System.out.println(customer);
        }

        //then
        assertNotNull(customerList.get(0));
        assertEquals(200, customerList.size());
    }

}

