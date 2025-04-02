package com.uts.service;

import com.uts.pojo.WhiteList;

import java.util.List;

public interface WhiteListService {
    //白名单添加
    int addToWhitelist(WhiteList whiteList);
    //白名单删除
    int deleteFromWhitelist(Long id);
    //白名单修改
    int updateWhitelist(WhiteList whiteList);
    //查询该删除的白名单是否属于用户名下
    boolean isInWhitelist(long id,long userid);
    //查询用户所有的白名单列表
    List<WhiteList> queryWhitelistByUserId(Long userid);
    //查询域名是否在用户白名单中
    boolean isInWhitelist(String domainName,long userid);
}
