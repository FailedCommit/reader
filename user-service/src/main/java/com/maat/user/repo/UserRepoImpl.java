package com.maat.user.repo;

import com.maat.mongo.BaseGlobalMongoRepository;
import com.maat.user.beans.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl extends BaseGlobalMongoRepository<User, String> implements UserRepo {
    public UserRepoImpl() {
        super("READER_DEFAULT", User.class, null, null);
    }

    @Override
    public User save(User user) {
        return user;
    }
}
