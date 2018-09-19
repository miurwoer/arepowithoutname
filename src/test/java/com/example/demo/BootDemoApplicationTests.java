package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootDemoApplicationTests {

	/**
	 * 连接redis
	 */
	@Test
	public void contextLoads() {
		Jedis jedis = new Jedis("192.168.0.100",6379);
		jedis.set("sex","男");
		jedis.get("sex");
		System.out.println(jedis.get("sex"));
		jedis.close();
	}

	/**
	 * 连接池 的方式连接redis
	 */
	@Test
	public void pool(){
		JedisPoolConfig config = new JedisPoolConfig();//连接池的配置对象
		config.setMaxTotal(30);//最大连接数
		config.setMaxIdle(10);//最大空闲连接数

		JedisPool jedisPool = new JedisPool(config,"192.168.1.108",6379);//连接池
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			jedis.set("color","yellow");
			System.out.println(jedis.get("name"));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (jedis != null) {
				jedis.close();
			}
			if (jedisPool != null) {
				jedisPool.close();
			}
		}
	}
}
