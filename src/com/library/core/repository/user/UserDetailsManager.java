package com.library.core.repository.user;

import com.library.core.model.user.User;

import java.util.Collection;

public interface UserDetailsManager {
    Collection<User> getAllUsers();

    Collection<User> getRentedUsers();

}
