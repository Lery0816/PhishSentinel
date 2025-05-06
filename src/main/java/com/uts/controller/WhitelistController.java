package com.uts.controller;

import com.uts.enums.ErrorCode;
import com.uts.exception.BusinessException;
import com.uts.pojo.WhiteList;
import com.uts.pojo.User;
import com.uts.service.WhiteListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/whitelist")
@ResponseBody
public class WhitelistController {
    @Autowired
    private WhiteListService whiteListService;

    @PostMapping("/add")
    public WhiteList addToWhitelist(@RequestParam String domainname,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        WhiteList whiteList = new WhiteList();
        whiteList.setDomainName(domainname);
        whiteList.setUserId(user.getId());
        whiteList.setCreatedAt(java.time.LocalDateTime.now());
        whiteList.setUpdatedAt(java.time.LocalDateTime.now());
        int result = whiteListService.addToWhitelist(whiteList);
        if (result == 1) {
            return whiteList; // Return the full object with ID
        }
        throw new BusinessException(ErrorCode.DOMAIN_ALREADY_EXISTS.getCode(), "Failed to add domain to whitelist");
    }

    @PostMapping("/delete")
    public int deleteFromWhitelist(@RequestParam Long id, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<WhiteList> userWhitelists = whiteListService.queryWhitelistByUserId(user.getId());
        boolean found = userWhitelists.stream()
            .anyMatch(item -> item.getId().equals(id));
        
        if (!found) {
            throw new BusinessException(ErrorCode.DOMAIN_NOT_FOUND.getCode(), ErrorCode.DOMAIN_NOT_FOUND.getMessage());
        }
        return whiteListService.deleteFromWhitelist(id);
    }

    @PostMapping("/update")
    public int updateWhitelist(@RequestParam String domainname,@RequestParam String id, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (whiteListService.isInWhitelist(domainname, user.getId())){
            throw new BusinessException(ErrorCode.DOMAIN_ALREADY_EXISTS.getCode(), ErrorCode.DOMAIN_ALREADY_EXISTS.getMessage());
        }
        WhiteList whiteList = new WhiteList();
        whiteList.setId(Long.parseLong(id));
        whiteList.setDomainName(domainname);
        whiteList.setUpdatedAt(java.time.LocalDateTime.now());
        whiteList.setUserId(user.getId());
        return whiteListService.updateWhitelist(whiteList);
    }

    @PostMapping("/list")
    public List<WhiteList> list(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<WhiteList> whiteLists = whiteListService.queryWhitelistByUserId(user.getId());
        whiteLists.forEach(whiteList -> {
            whiteList.setUserId(null);
        });
        return whiteLists;
    }
}
