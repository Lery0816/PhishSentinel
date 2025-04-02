package com.uts.controller;

import com.uts.enums.ErrorCode;
import com.uts.exception.BusinessException;
import com.uts.pojo.BlackList;
import com.uts.pojo.User;
import com.uts.service.BlackListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/blacklist")
@ResponseBody
public class BlacklistController {
    @Autowired
    private BlackListService blackListService;

    @PostMapping("/add")
    public BlackList addToBlacklist(@RequestParam String domainname,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        BlackList blackList = new BlackList();
        blackList.setDomainName(domainname);
        blackList.setUserId(user.getId());
        blackList.setCreatedAt(java.time.LocalDateTime.now());
        blackList.setUpdatedAt(java.time.LocalDateTime.now());
        int result = blackListService.addToBlacklist(blackList);
        if (result == 1) {
            return blackList; // Return the full object with ID
        }
        throw new BusinessException(ErrorCode.DOMAIN_ALREADY_EXISTS.getCode(), "Failed to add domain to blacklist");
    }

    @PostMapping("/delete")
    public int deleteFromBlacklist(@RequestParam Long id, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<BlackList> userBlacklists = blackListService.queryBlacklistByUserId(user.getId());
        boolean found = userBlacklists.stream()
            .anyMatch(item -> item.getId().equals(id));
        
        if (!found) {
            throw new BusinessException(ErrorCode.DOMAIN_NOT_FOUND.getCode(), ErrorCode.DOMAIN_NOT_FOUND.getMessage());
        }
        return blackListService.deleteFromBlacklist(id);
    }

    @PostMapping("/update")
    public int updateBlacklist(@RequestParam String domainname,@RequestParam String id, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (blackListService.isInBlacklist(domainname, user.getId())){
            throw new BusinessException(ErrorCode.DOMAIN_ALREADY_EXISTS.getCode(), ErrorCode.DOMAIN_ALREADY_EXISTS.getMessage());
        }
        BlackList blackList = new BlackList();
        blackList.setId(Long.parseLong(id));
        blackList.setDomainName(domainname);
        blackList.setUpdatedAt(java.time.LocalDateTime.now());
        blackList.setUserId(user.getId());
        return blackListService.updateBlacklist(blackList);
    }

    @PostMapping("/list")
    public List<BlackList> list(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<BlackList> blackLists = blackListService.queryBlacklistByUserId(user.getId());
        blackLists.forEach(blackList -> {
            blackList.setUserId(null);
        });
        return blackLists;
    }
}
