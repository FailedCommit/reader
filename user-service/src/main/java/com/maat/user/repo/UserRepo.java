package com.maat.user.repo;

import com.maat.user.beans.User;

public interface UserRepo<T extends User> {
    T save(T user);
}
