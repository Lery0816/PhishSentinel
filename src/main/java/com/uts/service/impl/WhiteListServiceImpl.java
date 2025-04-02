package com.uts.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.uts.enums.ErrorCode;
import com.uts.exception.BusinessException;
import com.uts.mapper.BlacklistMapper;
import com.uts.mapper.WhitelistMapper;
import com.uts.pojo.BlackList;
import com.uts.pojo.WhiteList;
import com.uts.service.BlackListService;
import com.uts.service.WhiteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WhiteListServiceImpl implements WhiteListService {
    @Autowired
    private WhitelistMapper whitelistMapper;
    @Override
    public int addToWhitelist(WhiteList whiteList) {
        QueryWrapper<WhiteList> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_name", whiteList.getDomainName()).eq("user_id", whiteList.getUserId());
        if (whitelistMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DOMAIN_ALREADY_EXISTS.getCode(),ErrorCode.DOMAIN_ALREADY_EXISTS.getMessage());
        }
        return whitelistMapper.insert(whiteList);
    }

    @Override
    public int deleteFromWhitelist(Long id) {

        return whitelistMapper.deleteById(id);
    }

    @Override
    public int updateWhitelist(WhiteList whiteList) {
        return whitelistMapper.updateById(whiteList);
    }

    @Override
    public boolean isInWhitelist(long id, long userid) {
        QueryWrapper<WhiteList> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id).eq("user_id",userid);
        return whitelistMapper.selectCount(wrapper)>0;
    }

    @Override
    public List<WhiteList> queryWhitelistByUserId(Long userid) {
        QueryWrapper<WhiteList> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userid);
        return whitelistMapper.selectList(wrapper);
    }

    @Override
    public boolean isInWhitelist(String domainName,long userid) {
        QueryWrapper<WhiteList> wrapper = new QueryWrapper<>();
        wrapper.eq("domain_Name",domainName).eq("user_id",userid);
        return whitelistMapper.selectCount(wrapper) > 0;
    }
}
