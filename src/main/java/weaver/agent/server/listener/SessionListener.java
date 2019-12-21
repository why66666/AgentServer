package weaver.agent.server.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class SessionListener implements HttpSessionListener {
    /*@Autowired
    protected RedisTemplate<String,Object> redisTemplate;*/
    private Logger logger = LoggerFactory.getLogger(getClass());
    public static AtomicInteger userCount = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.debug("====================================session创建========================================");
        logger.debug("====================================ip: "+httpSessionEvent.getSession().getAttribute("ip")+" 上线========================================");
        userCount.getAndIncrement();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.debug("====================================session销毁========================================");
        logger.debug("====================================ip: "+httpSessionEvent.getSession().getAttribute("ip")+" 下线========================================");
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        //redisTemplate.opsForHash().put(httpSessionEvent.getSession().getAttribute("ip").toString(),"endTime",df.format(new Date()));
        userCount.getAndDecrement();
    }
}
