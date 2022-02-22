package com.lemon.service;

import com.lemon.dao.pojo.Message;
import com.lemon.vo.ContactVo;
import com.lemon.vo.MessageVo;
import com.lemon.vo.param.PageParam;

import java.util.List;

public interface ChatService {
    Message getEmptyMessage();

    void savaMessage(Message messageRecord);

    List<MessageVo> getMessageVos(Long userId, PageParam pageParam);

    void hasReadFromUser(Long fromUserId, Long toUserId);

    List<MessageVo> getMessageRecord(Long fromUserId, PageParam pageParam);

    List<ContactVo> getContacts();
}
