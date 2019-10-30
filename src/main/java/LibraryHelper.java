import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Customer returnCustomerWithGivenID(int id, List<Customer> list) {
        Optional<Customer> exists = list
                .stream()
                .filter(a -> a.getId() == id)
                .findAny();
        if (!exists.isPresent()) {
            return null;
        } else {
            return exists.get();
        }
    }


    public LibraryBook returnLibraryBookWithGivenID(int id, List<LibraryBook> list) {
        Optional<LibraryBook> exists = list
                .stream()
                .filter(a -> a.getId() == id)
                .findAny();
        if (!exists.isPresent()) {
            return null;
        } else {
            return exists.get();
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

    public long dateDifferenceToNow(Date date) {
        Date currentDate = new Date();
        long difference = currentDate.getTime() - date.getTime();
        return difference / ((long) (1000 * 60 * 60 * 24));
    }

    //XML methods

    public void createLibraryTypeRaport(String raportName, List<T> bookList, boolean writeBorrowingDetails) throws IOException, XMLStreamException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(" dd-MM-yyyy (HH_mm_ss)");
        File file = new File(raportName + simpleDateFormat.format(new Date().getTime()) + ".xml");
        if (file.createNewFile()) {
            writeLibraryTypeListToXML(file.getCanonicalPath(), bookList, writeBorrowingDetails);
            System.out.println("Raport created successfully.");
        } else {
            System.out.println("Raport with this name already exists. Can't overwrite it.");
        }
    }

    public void writeLibraryTypeListToXML(String outputFile, List<T> libraryTypeObjectsList, boolean writeBoorowingDetails) throws XMLStreamException, FileNotFoundException {


        OutputStream os = new FileOutputStream(new File(outputFile));
        XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
        XMLStreamWriter streamWriter = outputFactory.createXMLStreamWriter(os, "utf-8");


        streamWriter.writeStartDocument("1.0");
        streamWriter.writeStartElement("customers");

        for(T libraryTypeObject : libraryTypeObjectsList){
            libraryTypeObject.writeToXML(streamWriter, writeBoorowingDetails);
        }

        streamWriter.writeEndElement();
        streamWriter.writeEndDocument();

        streamWriter.close();
    }


}

