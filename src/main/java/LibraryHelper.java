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
}
