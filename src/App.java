import com.library.core.model.book.BookCategory;
import com.library.core.model.user.Librarian;
import com.library.core.model.user.Member;
import com.library.core.model.user.User;
import com.library.database.model.BookHandler;
import com.library.database.model.RentedBooksHandler;
import com.library.database.model.UsersHandler;
import com.library.database.repository.BooksDataManager;
import com.library.database.repository.LibraryDataManager;
import com.library.database.repository.UserDataManager;
import com.library.database.utils.Utils;
import com.library.userinterface.LibrarianUI;
import com.library.userinterface.LoginUI;
import com.library.userinterface.MemberUI;

import java.time.Year;

public class App {
    public static void main(String[] args) {
        BookHandler bookHandler = BookHandler.getInstance();
        RentedBooksHandler rentedBooksHandler = RentedBooksHandler.getInstance();
        UsersHandler usersHandler = UsersHandler.getInstance();
        BooksDataManager booksDataManager = new BooksDataManager(bookHandler, rentedBooksHandler);
        LibraryDataManager libraryDataManager = new LibraryDataManager();
        UserDataManager userDataManager = new UserDataManager(usersHandler, rentedBooksHandler, booksDataManager, libraryDataManager);
        userDataManager.addLibrarian("sankar", "9876543210", "1");
        userDataManager.addMember("suresh","9876543211","1");
        booksDataManager.addBook("book","nithya", Year.of(1999), BookCategory.ENGLISH);
        System.out.println("Welcome to Library Management System");
        while (true) {
            System.out.println("1.Sign-Up\n2.Sign-In");
            int userPreference = Utils.getInteger();
            if (userPreference == 1) {
                Member member = new LoginUI(userDataManager).signUp();
                MemberUI memberUI = new MemberUI(member, libraryDataManager);
                memberUI.showMenu();
            } else if (userPreference == 2) {
                User user = new LoginUI(userDataManager).signIn();
                if (user instanceof Member) {
                    MemberUI memberUI = new MemberUI((Member) user, libraryDataManager);
                    memberUI.showMenu();
                } else if (user instanceof Librarian) {
                    LibrarianUI librarianUI = new LibrarianUI((Librarian) user);
                    librarianUI.showMenu();
                }
            }

        }
    }

}