import com.library.core.model.user.Librarian;
import com.library.core.model.user.Member;
import com.library.core.model.user.User;
import com.library.database.ManagerProvider;
import com.library.database.utils.Utils;
import com.library.userinterface.LibrarianUI;
import com.library.userinterface.LoginUI;
import com.library.userinterface.MemberUI;

public class App {

    public static void main(String[] args) {
        ManagerProvider.getUserDataManager().addLibrarian("sankar", "9876543210", "sankar@123W");
        System.out.println("Welcome to Library Management System");
        while (true) {
            System.out.println("1.Sign-Up\n2.Sign-In\n3.Exit");
            int userPreference = Utils.getInteger();
            if (userPreference == 1) {
                Member member = new LoginUI(ManagerProvider.getUserDataManager()).signUp();
                if (member == null){
                    continue;
                }
                MemberUI memberUI = new MemberUI(member, ManagerProvider.getLibraryDataManager());
                memberUI.showMenu();
            } else if (userPreference == 2) {
                User user = new LoginUI(ManagerProvider.getUserDataManager()).signIn();
                if (user instanceof Member) {
                    MemberUI memberUI = new MemberUI((Member) user, ManagerProvider.getLibraryDataManager());
                    memberUI.showMenu();
                } else if (user instanceof Librarian) {
                    LibrarianUI librarianUI = new LibrarianUI((Librarian) user);
                    librarianUI.showMenu();
                }
            } else if (userPreference == 3) {
                break;
            } else {
                System.out.println("Please select from given option!");
            }
        }
    }
}