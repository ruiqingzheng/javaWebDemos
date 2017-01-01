package com.hendry.mybatis.service;

import java.util.List;

import org.junit.Test;

import com.hendry.mybatis.beans.Message;
import com.hendry.mybatis.dao.MessageDao;

public class MessageService {
	
	public static List<Message> listService(String command , String description) {
		
		MessageDao messageDao = new MessageDao() ;
		return messageDao.queryMessage(command, description);
				
	}
	
	@Test
	public void testService(){
		String command = "查看";
		List<Message> msgList = MessageService.listService(command, null);
		for(Message msg : msgList) {
			System.out.println(msg.toString());
		}
	}

}
