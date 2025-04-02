package com.uts.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uts.enums.ErrorCode;
import com.uts.exception.BusinessException;
import com.uts.mapper.BlacklistMapper;
import com.uts.pojo.BlackList;
import com.uts.service.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListServiceImpl implements BlackListService {
    @Autowired
    private BlacklistMapper blacklistMapper;
    @Override
    public int addToBlacklist(BlackList blackList) {
        QueryWrapper<BlackList> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_name", blackList.getDomainName()).eq("user_id", blackList.getUserId());
        if (blacklistMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DOMAIN_ALREADY_EXISTS.getCode(),ErrorCode.DOMAIN_ALREADY_EXISTS.getMessage());
        }
        return blacklistMapper.insert(blackList);
    }

    @Override
    public int deleteFromBlacklist(Long id) {

        return blacklistMapper.deleteById(id);
    }

    @Override
    public int updateBlacklist(BlackList blackList) {
        return blacklistMapper.updateById(blackList);
    }

    @Override
    public boolean isInBlacklist(long id, long userid) {
        QueryWrapper<BlackList> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id).eq("user_id",userid);
        return blacklistMapper.selectCount(wrapper)>0;
    }

    @Override
    public List<BlackList> queryBlacklistByUserId(Long userid) {
        QueryWrapper<BlackList> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);
        return blacklistMapper.selectList(wrapper);
    }

    @Override
    public boolean isInBlacklist(String domainName,long userid) {
        QueryWrapper<BlackList> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_Name",domainName).eq("user_id",userid);
        return blacklistMapper.selectCount(wrapper) > 0;
    }
}
