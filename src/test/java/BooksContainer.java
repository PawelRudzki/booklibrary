import java.util.*;

public class BooksContainer {

    final Random generator = new Random();


    public Author getAuthor() {
        String[] names = new String[]{"Andrzej", "Monika", "Juliusz", "Łukasz", "Genowefa", "Jim", "Nancy"};
        String[] lastNames = new String[]{"Kopcinsky", "Pigwa", "Faletionus", "Kleczko", "Trustworthy", "Smith", "Levinsky"};
        return new Author(names[generator.nextInt(6)], lastNames[generator.nextInt(6)]);
    }

    public Book getBook() {
        Set<Author> authorList = new TreeSet<>();
        authorList.add(getAuthor());
        for (int i = 0; i < generator.nextInt(3); i++) {
            authorList.add(getAuthor());
        }

        String isbn = String.valueOf(generator.nextInt(200) + 100)
                + "-" + String.valueOf(generator.nextInt(200) + 100)
                + "-" + String.valueOf(generator.nextInt(80) + 10)
                + "-" + String.valueOf(generator.nextInt(200) + 100);

        String[] titleArr = new String[]{"Money", "Sin", "Nuances", "Horses"
                , "Forgiveness", "Abduction", "UFO", "New", "Trying", "Nonsens", "Another", "First", "Never"};
        String title = "";
        title += titleArr[generator.nextInt(13)];
        for (int i = 0; i < generator.nextInt(3); i++) {
            title += " " + titleArr[generator.nextInt(13)];
        }

        String[] categoryArr = new String[]{"OTHER", "HISTORICAL", "ELEMENTARY", "DICTIONARY", "RELIGION", "ADVENTURE"};
        BookCategory bookCategory = BookCategory.OTHER.valueOf(categoryArr[generator.nextInt(6)]);

        String yearOfPublication = String.valueOf(generator.nextInt(100) + 1919);

        String[] publisherArr = new String[]{"Zysk i Ska", "PWN", "Wydawnictwo Czarne", "Helion"};
        String publisher = publisherArr[generator.nextInt(4)];


        return new Book(isbn, title, authorList, yearOfPublication, publisher, bookCategory);
    }

    public LibraryBook getLibraryBook(Library library) {
        Book book = getBook();
        return new LibraryBook(book, library.getNextLibraryBookId(),
                14, null, 0);
    }

    public Customer getCustomer(Library library) {
        Random generator = new Random();
        String[] names = new String[]{"Adam", "Monika", "Agnieszka", "Łukasz", "Beata", "Janusz", "Rafał"};
        String[] lastNames = new String[]{"Domański", "Nowak", "Kowalski", "Kaleta", "Bredniak", "Malinowski", "Janiak"};
        return new Customer(library.getNextCustomerId(),
                (names[generator.nextInt(6)]), lastNames[generator.nextInt(6)], new ArrayList<>(), 0);
    }

    public Library getLibraryWithBooksAndCustomers(Library library, int numberOfBooks, int numberOfCustomers) {

        for (int i = 0; i < numberOfBooks; i++) {
            library.getLibraryWarehouse().add(getLibraryBook(library));
        }

        for (int i = 0; i < numberOfCustomers; i++) {
            library.getCustomerList().add(getCustomer(library));
        }

        return library;
    }

    public void simulateUsageOfTheLibrary(Library library) {

        LibraryBook tmpBook;
        Customer tmpCustomer;
        Date date;

        //simulate borrowings
        for (int i = 0; i < 1000; i++) {
            date = new Date();
            tmpBook = library.getLibraryWarehouse().get(generator.nextInt(library.getLibraryWarehouse().size()));
            tmpCustomer = library.getCustomerList().get(generator.nextInt(library.getCustomerList().size()));

            if ((tmpBook.getBorrowedBy() == 0) && (tmpCustomer.getBooksBorrowedList().size() < 5)) {
                tmpCustomer.borrowBook(library, tmpBook);

                //simulate previous borrowing to 30 days back in time
                date.setTime(date.getTime() - (generator.nextInt(30) * 86400000));
                tmpBook.setBorrowDate(date);
            }
        }

        //simulate returns
        for (int i = 0; i < 300; i++) {
            tmpBook = library.getLibraryWarehouse()
                    .stream()
                    .filter(a -> a.getBorrowedBy() != 0)
                    .findFirst()
                    .orElse(null);
            if (tmpBook != null) {
                library.getLibraryHelper()
                        .returnLibraryTypeOfGivenID(tmpBook.getBorrowedBy(), library.getCustomerList())
                        .returnBook(library, tmpBook);
            } else {
                break;
            }
        }
    }
}
