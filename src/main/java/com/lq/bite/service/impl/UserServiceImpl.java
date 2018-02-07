package com.lq.bite.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lq.bite.dao.UserDao;
import com.lq.bite.entity.User;
import com.lq.bite.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public User getOne(Integer id) {
		return userDao.getOne(id);
	}

	@Override
	public void insert(User t) {
		userDao.insert(t);
	}

	@Override
	public void update(User t) {
		userDao.update(t);
	}

	@Override
	public void delete(Integer id) {
		userDao.delete(id);
	}

	@Override
	public List<User> get(User t) {
		return userDao.get(t);
	}

}
