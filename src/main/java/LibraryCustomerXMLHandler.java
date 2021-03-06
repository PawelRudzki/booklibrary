import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import java.text.DecimalFormat;
import java.text.ParseException;
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
        } else if (qName.equalsIgnoreCase("lastName")) {
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
            try {
                tmpCustomer.setAccountBalance(DecimalFormat.getNumberInstance()
                        .parse(String.valueOf(ch, start, length)).doubleValue());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            baccountBalance = false;
        }

        if (bborrowedBook) {
            String[] tableOfLibraryBooks = String.valueOf(ch, start, length).split(" ");
            tmpCustomer.getBooksBorrowedList().add(Integer.valueOf(tableOfLibraryBooks[0]));
            bborrowedBook = false;

        }
    }

    @Override
    public void endDocument() {

    }
}

