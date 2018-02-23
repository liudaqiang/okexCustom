package com.lq.okex.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;  
  
/**    
 * Redis操作接口 
 * 
 * @author 林计钦 
 * @version 1.0 2013-6-14 上午08:54:14    
 */  
public class RedisAPI {  
    private static JedisPool pool = null;  
    private static final String REDIS_PASSWORD = "redis";
    private static Logger logger = LoggerFactory.getLogger(RedisAPI.class);
    /** 
     * 构建redis连接池 
     *  
     * @param ip 
     * @param port 
     * @return JedisPool 
     */  
    public static JedisPool getPool() {  
        if (pool == null) {  
            JedisPoolConfig config = new JedisPoolConfig();  
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
            config.setMaxTotal(500);
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
            config.setMaxIdle(5);  
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
            config.setMaxWaitMillis(1000 * 100);  
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
            config.setTestOnBorrow(true);  
           // pool = new JedisPool(config, "192.168.1.102", 6379,3000,REDIS_PASSWORD); 
            pool = new JedisPool(config,"127.0.0.1",6379,3000);
        }  
        return pool;  
    }  
      
    /** 
     * 返还到连接池 
     *  
     * @param pool  
     * @param redis 
     */  
    public static void returnResource(JedisPool pool, Jedis redis) {  
        if (redis != null) {  
            pool.returnResource(redis);  
        }  
    }  
      
    /** 
     * 获取数据 
     *  
     * @param key 
     * @return 
     */  
    public static String getStr(String key){  
        String value = null;  
        JedisPool pool = null;  
        Jedis jedis = null;  
        try {  
            pool = getPool();  
            jedis = pool.getResource();  
            value = jedis.get(key);  
        } catch (Exception e) {  
            //释放redis对象  
            pool.returnBrokenResource(jedis);  
            //e.printStackTrace();  
        } finally {  
            //返还到连接池  
            returnResource(pool, jedis);  
        }  
          
        return value;  
    }  
    public static void setStr(String key,String value,int time){
    	 JedisPool pool = null;  
         Jedis jedis = null;  
         try {  
             pool = getPool();  
             jedis = pool.getResource();  
             jedis.setex(key, time, value);
         } catch (Exception e) {  
             //释放redis对象  
             pool.returnBrokenResource(jedis);  
             logger.error("error:"+e.getMessage());
         } finally {  
             //返还到连接池  
             returnResource(pool, jedis);  
         }  
    }
    public static void setObj(String key,Object obj,int time){
    	JedisPool pool = null;  
        Jedis jedis = null;  
        try {  
            pool = getPool();  
            jedis = pool.getResource();  
            jedis.setex(key.getBytes(), time, SerializeUtil.serialize(obj));
        } catch (Exception e) {  
            //释放redis对象  
            pool.returnBrokenResource(jedis);  
        } finally {  
            //返还到连接池  
            returnResource(pool, jedis);  
        }  
    }
    public static Object getObj(String key){
    	 byte[] value = null;  
         JedisPool pool = null;  
         Jedis jedis = null;  
         try {  
             pool = getPool();  
             jedis = pool.getResource();  
             value = jedis.get(key.getBytes());  
             Object object = SerializeUtil. unserialize(value);
             return object;
         } catch (Exception e) {  
             //释放redis对象  
             pool.returnBrokenResource(jedis);  
         } finally {  
             //返还到连接池  
             returnResource(pool, jedis);  
         }  
         return null; 
    }
    
}  