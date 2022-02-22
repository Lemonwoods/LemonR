package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.dao.mapper.MessageMapper;
import com.lemon.dao.pojo.Message;
import com.lemon.dao.pojo.User;
import com.lemon.service.ChatService;
import com.lemon.service.UserService;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.ContactVo;
import com.lemon.vo.MessageVo;
import com.lemon.vo.UserVo;
import com.lemon.vo.param.PageParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {
    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserService userService;

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

    private Long[] getContactsUserIdList(Long userId){
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct to_user_id");
        queryWrapper.lambda().eq(Message::getFromUserId, userId);
        List<Message> toUserIdList = messageMapper.selectList(queryWrapper);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct from_user_id");
        queryWrapper.lambda().eq(Message::getToUserId, userId);
        List<Message> fromUserIdList = messageMapper.selectList(queryWrapper);

        Set<Long> contactsUserIdSet = new HashSet<>();
        for(Message toUserId:toUserIdList) contactsUserIdSet.add(toUserId.getToUserId());
        for(Message fromUserId:fromUserIdList) contactsUserIdSet.add(fromUserId.getFromUserId());

        return contactsUserIdSet.toArray(new Long[0]);
    }

    @Override
    public Message getEmptyMessage() {
        Message message = new Message();
        message.setHasRead(false);
        return message;
    }

    @Override
    public void savaMessage(Message messageRecord) {
        messageMapper.insert(messageRecord);
    }

    @Override
    public List<MessageVo> getMessageVos(Long userId, PageParam pageParam) {
        IPage<Message> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        IPage<Message> messageRecordPages = messageMapper.getMessageRecordPages(page, UserThreadLocal.get().getId(), userId);
        return transferToMessageVoList(messageRecordPages.getRecords());
    }

    @Override
    public void hasReadFromUser(Long fromUserId, Long toUserId) {
        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("from_user_id", fromUserId);
        messageUpdateWrapper.eq("to_user_id", toUserId);
        messageUpdateWrapper.eq("hasRead", false);
        messageUpdateWrapper.set("hasRead", true);
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

    @Override
    public List<ContactVo> getContacts() {
        Long[] contactsUserIdList = getContactsUserIdList(UserThreadLocal.get().getId());

        List<ContactVo> contactVos = new LinkedList<>();

        for(Long userId: contactsUserIdList){
            Message lastMessage = messageMapper.getLastMessage(userId, UserThreadLocal.get().getId());
            UserVo userVo = userService.findUserVoById(UserThreadLocal.get().getId(), false);
            ContactVo contactVo = new ContactVo();
            contactVo.setUserId(userId);
            contactVo.setAvatar(userVo.getAvatar());
            contactVo.setNickName(userVo.getNickname());
            contactVo.setLastMessage(transferToMessageVo(lastMessage));
            contactVos.add(contactVo);
        }

        return contactVos;
    }


}
