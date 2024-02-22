package com.yolo.config.redis;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashSet;
import java.util.Set;

/**
 * redis配置
 */
@Log4j2
@Configuration
public class RedisConfig {


//    @Value("${redis.type:}")
//    private String type;

    @Value("${redis.url}")
    private String REDIS_CLUSTER_URL;
    @Value("${redis.password:}")
    private String psw;
//    @Value("${redis.single.url}")
//    private String REDIS_SINGLE_URL;

    @Bean(name = "redisNodes")
    public Iterable<RedisNode> getRedisNodes() {
        if (StringUtils.isBlank(REDIS_CLUSTER_URL)) {
            log.warn("REDIS配置文件为空");
            return null;
        }

        String[] urls = StringUtils.split(REDIS_CLUSTER_URL, ',');
        Set<RedisNode> nodes = new HashSet<>();
        //Sets.newHashSetWithExpectedSize(urls.length);

        for (String url : urls) {
            String[] splits = url.split(":");
            nodes.add(new RedisNode(splits[0], Integer.parseInt(splits[1])));
        }
        return nodes;
    }

    @Bean(name = "redisClusterConfiguration")
    public RedisClusterConfiguration getClusterConfiguration(Iterable<RedisNode> redisNodes) {
        if (redisNodes == null) {
            return null;
        }

        RedisClusterConfiguration configuration = new RedisClusterConfiguration();
        configuration.setClusterNodes(redisNodes);
        configuration.setMaxRedirects(5);
        if (psw != null && !"".equals(psw)) {
            configuration.setPassword(RedisPassword.of(psw));
        }
        return configuration;
    }

    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory getRedisConnectionFactory(RedisClusterConfiguration redisClusterConfiguration) {
        String type = redisClusterConfiguration.getClusterNodes().size() == 1 ? "single" : "cluster";

        if ("single".equals(type)) {
            if (StringUtils.isEmpty(REDIS_CLUSTER_URL) || !REDIS_CLUSTER_URL.contains(":")) {
                throw new RuntimeException("请检查redis配置，单点配置不正确");
            }

            String[] split = REDIS_CLUSTER_URL.split(":");
            return new LettuceConnectionFactory(split[0], Integer.parseInt(split[1]));
        }
//        if ("cluster".equals(type)) {
        if (redisClusterConfiguration == null) {
            throw new RuntimeException("请检查redis配置，集群配置不正确");
        }

        return new LettuceConnectionFactory(redisClusterConfiguration);
//        }
//        return null;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        return redisTemplate;
    }
}
