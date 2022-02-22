package com.lemon.controller;

import com.lemon.service.ChatService;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.ContactVo;
import com.lemon.vo.MessageVo;
import com.lemon.vo.Result;
import com.lemon.vo.param.PageParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatController {
    @Resource
    private ChatService chatService;

    @PostMapping("hasRead/fromUser/{fromUserId}")
    public Result hasReadFromUser(@PathVariable("fromUserId") Long fromUserId){
        chatService.hasReadFromUser(fromUserId, UserThreadLocal.get().getId());
        return Result.succeed(null);
    }

    @PostMapping("records/fromUser/{fromUserId}")
    public Result getMessageRecord(@PathVariable("fromUserId")Long fromUserId,
                                   @RequestBody PageParam pageParam){
        List<MessageVo> messageVoList = chatService.getMessageVos(fromUserId, pageParam);
        return Result.succeed(messageVoList);
    }

    @GetMapping("contacts")
    public Result getContacts(){
        List<ContactVo> contactVos = chatService.getContacts();
        return Result.succeed(contactVos);
    }
}
