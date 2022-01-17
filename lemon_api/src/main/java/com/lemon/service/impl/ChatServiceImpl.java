package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.dao.mapper.MessageMapper;
import com.lemon.dao.pojo.Message;
import com.lemon.service.ChatService;
import com.lemon.vo.MessageVo;
import com.lemon.vo.param.PageParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private MessageMapper messageMapper;

    private MessageVo transferToMessageVo(Message message){
        MessageVo messageVo = new MessageVo();
        BeanUtils.copyProperties(message, messageVo);
        return messageVo;
    }

    private List<MessageVo> transferToMessageVoList(List<Message> messageList){
        List<MessageVo> messageVoList = new ArrayList<>();
        for(Message message:messageList) messageVoList.add(transferToMessageVo(message));
        return messageVoList;
    }

    @Override
    public Message getEmptyMessage() {
        Message message = new Message();
        message.setRead(false);
        return message;
    }

    @Override
    public void savaMessage(Message messageRecord) {
        messageMapper.insert(messageRecord);
    }

    @Override
    public List<Message> getUnreadMessage(Long userId) {
        LambdaQueryWrapper<Message> messageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        messageLambdaQueryWrapper.eq(Message::getToUserId, userId);
        messageLambdaQueryWrapper.eq(Message::isRead, false);
        List<Message> messageList = messageMapper.selectList(messageLambdaQueryWrapper);
        return messageList;
    }

    @Override
    public void hasReadFromUser(Long fromUserId, Long toUserId) {
        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("from_user_id", fromUserId);
        messageUpdateWrapper.eq("to_user_id", toUserId);
        messageUpdateWrapper.eq("read", false);
        messageUpdateWrapper.set("read", true);
        messageMapper.update(null, messageUpdateWrapper);
    }

    @Override
    public List<MessageVo> getMessageRecord(Long fromUserId, PageParam pageParam) {
        Page<Message> messagePage = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Message> messageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        messageLambdaQueryWrapper.eq(Message::getFromUserId, fromUserId);
        messageLambdaQueryWrapper.eq(Message::getToUserId, toString());
        messageLambdaQueryWrapper.orderByDesc(Message::getCreateDate);
        IPage<Message> messageList = messageMapper.selectPage(messagePage, messageLambdaQueryWrapper);
        return transferToMessageVoList(messageList.getRecords());
    }
}
