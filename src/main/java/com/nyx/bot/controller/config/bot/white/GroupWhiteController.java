package com.nyx.bot.controller.config.bot.white;

import com.fasterxml.jackson.annotation.JsonView;
import com.mikuac.shiro.core.BotContainer;
import com.nyx.bot.core.AjaxResult;
import com.nyx.bot.core.Views;
import com.nyx.bot.core.controller.BaseController;
import com.nyx.bot.core.page.TableDataInfo;
import com.nyx.bot.entity.bot.white.GroupWhite;
import com.nyx.bot.repo.impl.white.WhiteService;
import com.nyx.bot.utils.SpringUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config/bot/white/group")
public class GroupWhiteController extends BaseController {

    @Resource
    WhiteService whiteService;


    @PostMapping("/list")
    @JsonView(Views.View.class)
    public TableDataInfo list(@RequestBody GroupWhite white) {
        return getDataTable(whiteService.list(white));
    }


    @GetMapping("/add")
    public AjaxResult add() {
        AjaxResult ar = AjaxResult.success();
        SpringUtils.getBean(BotContainer.class).robots.forEach((aLong, bot) -> ar.put(String.valueOf(aLong), bot.getGroupList().getData()));
        return ar;
    }

    @PostMapping("/save")
    public AjaxResult add(@RequestBody GroupWhite white) {
        if (white == null) return error();
        return toAjax(whiteService.save(white) != null);
    }

    @GetMapping("/edit/{id}")
    public AjaxResult edit(@PathVariable("id") Long id) {
        return success().put("white", whiteService.findByGroup(id));
    }


    @PostMapping("/remove/{id}")
    public AjaxResult remove(@PathVariable("id") Long id) {
        whiteService.remove(id);
        return success();
    }

}
