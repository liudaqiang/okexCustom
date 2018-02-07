package com.lq.okex.base;

import java.util.List;

public interface BaseDao<T> {
	
	List<T> getAll();  
    
	T getOne(Integer id);  
  
    void insert(T t);  
  	
    void update(T t);  
  
    void delete(Integer id);  
    
    List<T> get(T t);
}
