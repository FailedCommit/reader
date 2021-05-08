package com.maat.user.repo;

import com.maat.user.beans.UserDetail;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl extends SimpleMongoRepository<UserDetail, Long> {
    public UserRepoImpl(MongoTemplateFactory templateFactory){
        super("READER_DEFAULT", UserDetail.class, templateFactory);
    }
}
