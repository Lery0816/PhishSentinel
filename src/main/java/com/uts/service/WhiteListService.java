package com.uts.service;

import com.uts.pojo.WhiteList;

import java.util.List;

public interface WhiteListService {

    int addToWhitelist(WhiteList whiteList);

    int deleteFromWhitelist(Long id);

    int updateWhitelist(WhiteList whiteList);

    boolean isInWhitelist(long id,long userid);

    List<WhiteList> queryWhitelistByUserId(Long userid);

    boolean isInWhitelist(String domainName,long userid);
}
