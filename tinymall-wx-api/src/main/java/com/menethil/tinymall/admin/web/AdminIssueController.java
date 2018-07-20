package com.menethil.tinymall.admin.web;

import com.menethil.tinymall.admin.annotation.LoginAdmin;
import com.menethil.tinymall.core.util.ResponseUtil;
import com.menethil.tinymall.db.domain.TinymallIssue;
import com.menethil.tinymall.db.service.TinymallIssueService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/issue")
public class AdminIssueController {
    private final Log logger = LogFactory.getLog(AdminIssueController.class);

    @Autowired
    private TinymallIssueService issueService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String question,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<TinymallIssue> issueList = issueService.querySelective(question, page, limit, sort, order);
        int total = issueService.countSelective(question, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", issueList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody TinymallIssue issue){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        issue.setAddTime(LocalDateTime.now());
        issueService.add(issue);
        return ResponseUtil.ok(issue);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        TinymallIssue issue = issueService.findById(id);
        return ResponseUtil.ok(issue);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody TinymallIssue issue){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        issueService.updateById(issue);
        return ResponseUtil.ok(issue);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody TinymallIssue issue){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        issueService.deleteById(issue.getId());
        return ResponseUtil.ok();
    }

}
