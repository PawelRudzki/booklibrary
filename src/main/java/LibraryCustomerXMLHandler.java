import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import java.util.ArrayList;
import java.util.List;

public class LibraryCustomerXMLHandler extends DefaultHandler {


    @Getter
    private List<Customer> customerList = null;
    private Customer tmpCustomer = null;

    private boolean bname = false;
    private boolean blastName = false;
    private boolean baccountBalance = false;
    private boolean bborrowedBook = false;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {


        if ("customer".equals(qName)) {
            int id = Integer.valueOf(attributes.getValue("id"));
            tmpCustomer = new Customer(id, null, null, new ArrayList<>(), 0);
            tmpCustomer.setId(id);
        }

        if (customerList == null) {
            customerList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("name")) {
            bname = true;
        } else if (qName.equalsIgnoreCase("blastName")) {
            blastName = true;
        } else if (qName.equalsIgnoreCase("accountBalance")) {
            baccountBalance = true;
        } else if (qName.equalsIgnoreCase("borrowedBook")) {
            bborrowedBook = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equalsIgnoreCase("customer")) {
            customerList.add(tmpCustomer);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {

        if (bname) {
            tmpCustomer.setName(String.valueOf(ch, start, length));
            bname = false;
        }

        if (blastName) {
            tmpCustomer.setLastName(String.valueOf(ch, start, length));
            blastName = false;
        }
        if (baccountBalance) {
            tmpCustomer.setAccountBalance(Integer.valueOf(String.valueOf(ch, start, length)));
            baccountBalance = false;
        }

        if(bborrowedBook){
            String[] tableOfLibraryBooks = String.valueOf(ch, start, length).split(" ");
            tmpCustomer.getBooksBorrowedList().add(new LibraryBook(null,
                    Integer.valueOf(tableOfLibraryBooks[0]), 0, null, null));
        }
    }

    @Override
    public void endDocument() {

    }
}

