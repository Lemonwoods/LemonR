package com.lemon.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lemon.dao.pojo.Message;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    public IPage<Message> getMessageRecordPages(IPage<Message> page,
                                                Long userIdOne,
                                                Long userIdTwo);
    public Message getLastMessage(Long userIdOne, Long userIdTwo);
}
