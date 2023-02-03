package com.library.core.repository.user;

import com.library.core.model.user.Member;
import com.library.core.model.user.User;

import javax.jws.soap.SOAPBinding;
import java.util.Collection;

public interface UserDetailsManager {
    Collection<User> getAllUsers();
    Collection<User> getRentedUsers();

}
