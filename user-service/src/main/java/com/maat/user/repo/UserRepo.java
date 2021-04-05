package com.maat.user.repo;

import com.maat.user.beans.UserDetail;

public interface UserRepo<T extends UserDetail> {
    T save(T user);
    UserDetail findByUsername(String username);
}
