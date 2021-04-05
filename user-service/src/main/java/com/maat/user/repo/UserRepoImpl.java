package com.maat.user.repo;

import com.maat.mongo.BaseGlobalBaseRepository;
import com.maat.mongo.MongoTemplateFactory;
import com.maat.user.beans.UserDetail;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepoImpl extends BaseGlobalBaseRepository<UserDetail, Long> {
    public UserRepoImpl(MongoTemplateFactory templateFactory){
        super("READER_DEFAULT", UserDetail.class, templateFactory);
    }
}
