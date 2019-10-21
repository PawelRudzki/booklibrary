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
}

