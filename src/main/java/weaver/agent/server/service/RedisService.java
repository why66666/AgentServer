package weaver.agent.server.service;

/**
 * @author w
 * @date 2019-12-01 21:21
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import weaver.agent.server.bean.BaseBean;


public abstract class RedisService<T extends BaseBean> implements BaseService<T>{

    @Autowired
    protected RedisTemplate<String,Object> redisTemplate;

    protected Logger logger = LoggerFactory.getLogger(getClass());

}
