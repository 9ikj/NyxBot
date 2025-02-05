package com.nyx.bot.aop;

import com.mikuac.shiro.dto.event.message.AnyMessageEvent;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import com.nyx.bot.repo.impl.black.BlackService;
import com.nyx.bot.repo.impl.white.WhiteService;
import com.nyx.bot.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class BlackCheckAspect {

    //黑白名单过滤
    @Around(value = "execution(* com.nyx.bot.plugin.*.*Handler(..))")
    public Object handler(ProceedingJoinPoint pjp) {
        try {
            for (Object arg : pjp.getArgs()) {
                if (arg instanceof AnyMessageEvent) {
                    if (isCheck(((AnyMessageEvent) arg).getGroupId(), ((AnyMessageEvent) arg).getUserId())) {
                        return pjp.proceed();
                    }
                }
                if (arg instanceof GroupMessageEvent) {
                    if (isCheck(((GroupMessageEvent) arg).getGroupId(), ((GroupMessageEvent) arg).getUserId())) {
                        return pjp.proceed();
                    }
                }
                if (arg instanceof PrivateMessageEvent) {
                    if (isCheck(0L, ((PrivateMessageEvent) arg).getUserId())) {
                        return pjp.proceed();
                    }
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    /**
     * 黑白名单过滤, 白名单优先级高于黑名单
     * 白名单中存在时，返回true
     * 白名单中不存在时，黑名单中存在，则返回false
     * 默认返回 true
     *
     * @param group group
     * @param prove prove
     * @return 是否存在名单中
     */
    private boolean isCheck(Long group, Long prove) {
        // 白名单中存在时，返回true
        if (SpringUtils.getBean(WhiteService.class).isWhite(group, prove)) return true;
        // 黑名单中存在，则返回false
        if (!SpringUtils.getBean(BlackService.class).isBlack(group, prove)) return false;
        // 默认返回 true
        return true;
    }

}
