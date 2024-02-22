package com.yolo.config.redis;

import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:shenxi
 * @Des:redis锁配置
 * @Date:2018/12/6
 */
@Log4j2
@Configuration
public class RedissonConfig {
    @Value("${redis.url}")
    private String cluster;
//    @Value("${redis.type:}")
//    private String type;
    @Value("${redis.password:}")
    private String psw;

    @Bean
    public RedissonClient getRedisson() {
        String[] nodes = cluster.split(",");
        //redisson版本是3.9.1，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = "redis://" + nodes[i];
        }
        Config config = new Config();
        log.info("tst=============={}={},{}",cluster,psw);
        String type=nodes.length==1?"single":"cluster";

        if ("single".equals(type)) {
            if (psw != null && !"".equals(psw)) {
                config.useSingleServer().setAddress("redis://" + cluster).setPassword(psw); //这是用的单机server
            } else {
                config.useSingleServer().setAddress("redis://" + cluster); //这是用的单机server
            }
            //设置集群状态扫描时间
//                    .addNodeAddress(nodes);
        } else {
            if (psw != null && !"".equals(psw)) {
                config.useClusterServers() //这是用的集群server
                        .setScanInterval(2000) //设置集群状态扫描时间
                        .addNodeAddress(nodes)
                        .setPassword(psw);
            } else {
                config.useClusterServers() //这是用的集群server
                        .setScanInterval(2000) //设置集群状态扫描时间
                        .addNodeAddress(nodes);
            }
        }
        RedissonClient redisson = Redisson.create(config);
//        try {
//            System.out.println(redisson.getConfig().toJSON());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //可通过打印redisson.getConfig().toJSON().toString()来检测是否配置成功
        return redisson;
    }

}

