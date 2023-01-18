package com.library.core.repository.user;

import com.library.core.model.user.Member;
import com.library.core.model.user.User;

import java.util.Collection;

public interface UsersManager {

    Collection<User> getAllUsers();

    User getUser(String phoneNumber);

    boolean checkUserCredentials(String phoneNumber, String password);

    boolean userExists(String phoneNumber);

}
