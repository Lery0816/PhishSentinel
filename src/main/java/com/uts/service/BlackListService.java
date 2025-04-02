package com.uts.service;

import com.uts.pojo.BlackList;

import java.util.List;

public interface BlackListService {
    //黑名单添加
    int addToBlacklist(BlackList blackList);
    //黑名单删除
    int deleteFromBlacklist(Long id);
    //黑名单修改
    int updateBlacklist(BlackList blackList);
    //查询该删除的黑名单是否属于用户名下
    boolean isInBlacklist(long id,long userid);
    //查询用户所有的黑名单列表
    List<BlackList> queryBlacklistByUserId(Long userid);
    //查询域名是否在用户黑名单中
    boolean isInBlacklist(String domainName,long userid);
}
