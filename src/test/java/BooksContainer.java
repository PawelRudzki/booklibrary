import java.util.*;

public class BooksContainer {

    public Author getAuthor() {
        return new Author("Adam", "Mickiewicz");
    }

    public Book getBook() {
        Set<Author> authorList = new TreeSet<>();
        authorList.add(getAuthor());
        return new Book("ISBN 123-234-21-234", "Janko Muzykant", authorList,
                "1999", "Zysk i Ska", BookCategory.ADVENTURE);
    }

    public LibraryBook getLibraryBook() {
        Book book = getBook();
        Random generator = new Random();
        return new LibraryBook(book, generator.nextInt(1000) + 500,
                14, null, null);
    }

    public Customer getCustomer() {
        Random generator = new Random();
        return new Customer(generator.nextInt(1000) + 500,
                "Jan", "Nowak", new ArrayList<>(), 0);
    }

    public Customer getCustomerWithBooks() {
        Random generator = new Random();
        List<LibraryBook> bookList = new ArrayList<>();
        bookList.add(getLibraryBook());
        return new Customer(generator.nextInt(1000) + 500,
                "Jan", "Nowak", new ArrayList<>(), 0);
    }

}
