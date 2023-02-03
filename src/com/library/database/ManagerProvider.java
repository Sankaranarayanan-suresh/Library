package com.library.database;

import com.library.database.model.BookHandler;
import com.library.database.model.UsersHandler;
import com.library.database.repository.BooksDataManager;
import com.library.database.repository.LibraryDataManager;
import com.library.database.repository.UserDataManager;

public class ManagerProvider {
    public static BooksDataManager getBooksDataManager() {
        return new BooksDataManager(BookHandler.getInstance());
    }

    public static LibraryDataManager getLibraryDataManager() {
        return new LibraryDataManager();
    }

    public static UserDataManager getUserDataManager() {
        return new UserDataManager(UsersHandler.getInstance(), getBooksDataManager(), getLibraryDataManager());
    }


}
