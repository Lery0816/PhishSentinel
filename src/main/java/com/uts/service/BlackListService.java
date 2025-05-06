package com.uts.service;

import com.uts.pojo.BlackList;

import java.util.List;

public interface BlackListService {

    int addToBlacklist(BlackList blackList);

    int deleteFromBlacklist(Long id);

    int updateBlacklist(BlackList blackList);

    boolean isInBlacklist(long id,long userid);

    List<BlackList> queryBlacklistByUserId(Long userid);

    boolean isInBlacklist(String domainName,long userid);
}
