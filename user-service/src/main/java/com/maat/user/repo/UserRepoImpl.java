package com.maat.user.repo;

import com.maat.mongo.BaseGlobalBaseRepository;
import com.maat.mongo.MongoTemplateFactory;
import com.maat.user.beans.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl extends BaseGlobalBaseRepository<User, String> {
    public UserRepoImpl(MongoTemplateFactory templateFactory){
        super("READER_DEFAULT", User.class, templateFactory);
    }



}
