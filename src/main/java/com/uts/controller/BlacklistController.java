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
@RequestMapping("/api/whitelist")
@ResponseBody
public class BlacklistController {
    @Autowired
    private BlackListService whiteListService;

    @PostMapping("/add")
    public int addToBlacklist(@RequestParam String domainname,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        BlackList whiteList = new BlackList();
        whiteList.setDomainName(domainname);
        whiteList.setUserId(user.getId());
        whiteList.setCreatedAt(java.time.LocalDateTime.now());
        whiteList.setUpdatedAt(java.time.LocalDateTime.now());
        return whiteListService.addToBlacklist(whiteList);//0 failed 1 successful
    }

    @PostMapping("/delete")
    public int deleteFromBlacklist(@RequestParam String id,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (!whiteListService.isInBlacklist(id, user.getId())){
            throw new BusinessException(ErrorCode.DOMAIN_NOT_FOUND.getCode(), ErrorCode.DOMAIN_NOT_FOUND.getMessage());
        }
        return whiteListService.deleteFromBlacklist(Long.parseLong(id));//0 failed 1 successful
    }

    @PostMapping("/update")
    public int updateBlacklist(@RequestParam String domainname,@RequestParam String id, HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        if (whiteListService.isInBlacklist(domainname, user.getId())){
            throw new BusinessException(ErrorCode.DOMAIN_ALREADY_EXISTS.getCode(), ErrorCode.DOMAIN_ALREADY_EXISTS.getMessage());
        }
        BlackList whiteList = new BlackList();
        whiteList.setId(Long.parseLong(id));
        whiteList.setDomainName(domainname);
        whiteList.setUpdatedAt(java.time.LocalDateTime.now());
        whiteList.setUserId(user.getId());
        return whiteListService.updateBlacklist(whiteList);
    }
    @PostMapping("/list")
    public List<BlackList> list(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        List<BlackList> whiteLists = whiteListService.queryBlacklistByUserId(user.getId());
        whiteLists.forEach(whiteList -> {
            whiteList.setUserId(null);
        });
        return whiteLists;
    }
}
