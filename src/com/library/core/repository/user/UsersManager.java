package com.library.core.repository.user;

import com.library.core.model.user.User;

public interface UsersManager {

    User getUser(String phoneNumber);

    boolean checkUserCredentials(String phoneNumber, String password);

    boolean userExists(String phoneNumber);


}
