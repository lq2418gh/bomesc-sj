package dkd.paltform.util;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static JedisPool jedisPool = null;
    //初始化Redis连接池
    static {
    	Properties prop = SpringUtil.getRedisProp();
        try {
            JedisPoolConfig config = new JedisPoolConfig();          
            config.setMaxTotal(parseInt(prop.getProperty("MAX_ACTIVE")));
            config.setMaxIdle(parseInt(prop.getProperty("MAX_IDLE")));
            config.setMaxWaitMillis(parseInt(prop.getProperty("MAX_WAIT")));
            config.setTestOnBorrow(parseBoolean("TEST_ON_BORROW"));
            jedisPool = new JedisPool(config, prop.getProperty("ADDR"), parseInt(prop.getProperty("PORT")), parseInt(prop.getProperty("TIMEOUT")), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static int parseInt(String str){
    	return Integer.parseInt(str);
    }
    private static boolean parseBoolean(String str){
    	return Boolean.parseBoolean(str);
    }
    //获取Jedis实例
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}