import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public abstract class LibraryTypes {

    public abstract int getId();

    public abstract void writeToXML(XMLStreamWriter xmlStreamWriter, boolean writeBorrowingDetails)
    throws XMLStreamException;
}
