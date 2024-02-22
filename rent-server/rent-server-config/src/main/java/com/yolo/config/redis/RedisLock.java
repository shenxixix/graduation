package com.yolo.config.redis;


import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author:shenxi
 * @Des:redis锁工具
 * @Date:2018/12/6
 */
@Service
@Log4j2
public class RedisLock {
    @Autowired
    private RedissonClient redissonClient;
    private final long WAIT_TIME = 3;
    private final long LEASE_TIME = 10;

    /**
     * 锁
     * @param lockKey 锁的key值，对应解锁需要
     * @return 返回是否锁住 true:获得锁;false:未获得锁
     */
    public boolean lock(String lockKey) {
        return tryLock(lockKey);
    }

    private boolean tryLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.info("锁错误:",e);
//            e.printStackTrace();
        }
        return false;
    }

    /**
     * 解锁
     * @param lockKey 锁key
     */
    public void unlock(String lockKey) {
        try {
            RLock lock = redissonClient.getLock(lockKey);
            //检查是否是当前线程保持，保持则解锁
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        } catch (Exception e) {
            log.info("解锁错误:",e);
        }
    }


}
