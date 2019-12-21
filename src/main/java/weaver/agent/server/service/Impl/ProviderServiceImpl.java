package weaver.agent.server.service.Impl;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import weaver.agent.server.bean.BaseBean;
import weaver.agent.server.service.RedisService;
import weaver.agent.server.service.interfase.ProviderService;

import java.util.*;

/**
 * @author w
 * @date 2019-12-01 21:54
 */
@Service
public class ProviderServiceImpl extends RedisService<BaseBean> implements ProviderService {
    @Override
    public void addEntry(Map map) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        if(redisTemplate.hasKey(map.get("ip").toString() + ":FileDel") == null || !redisTemplate.hasKey(map.get("ip").toString() + ":FileDel")){
            System.out.println("不存在"+map.get("ip").toString()+ ":FileDel");
            hash.putAll(map.get("ip").toString() + ":FileDel",map);
        }
        if(redisTemplate.hasKey(map.get("ip").toString() + ":SqlDel") == null || !redisTemplate.hasKey(map.get("ip").toString() + ":SqlDel")){
            System.out.println("不存在"+map.get("ip").toString()+ ":SqlDel");
            hash.putAll(map.get("ip").toString() + ":SqlDel",map);
        }

    }

    @Override
    public Map getByKey(Map map) {
        return redisTemplate.opsForHash().entries(map.get("key").toString());
    }

    @Override
    public void setSqlDel(Map map) {
        if(redisTemplate.hasKey(map.get("key").toString()) == null){
            System.out.println("不存在key");
        }else{
            Set<Object> keySet = redisTemplate.opsForHash().keys(map.get("key").toString());
            int i = 0;
            while (keySet.contains("sdStack_" + i)) {
                i++;
            }
            map.put("sdStack_" + i,map.get("sdStack").toString().split(", "));
            map.remove("sdStack");
            redisTemplate.opsForHash().putAll(map.get("key").toString(), map);
        }
    }

    @Override
    public void setfileDel(Map map) {
        /*if(redisTemplate.hasKey(map.get("ip").toString()) == null){
            System.out.println("不存在key");
        }else{
            Set<Object> keySet = redisTemplate.opsForHash().keys(map.get("key").toString());
            int i = 0;
            while (keySet.contains("sfStack_" + i)) {
                i++;
            }
            List list = Arrays.asList(map.get("sfStack").toString().split(", "));
            logger.debug("list==>"+list.get(1));
            map.put("sfStack_" + i,list);
            map.remove("sfStack");
            redisTemplate.opsForHash().putAll(map.get("key").toString(), map);
        }*/

        Set<Object> keySet = redisTemplate.opsForHash().keys(map.get("ip").toString() + ":FileDel");
        int i = 0;
        while (keySet.contains("sfStack_" + i)) {
            i++;
        }
        List list = Arrays.asList(map.get("sfStack").toString().split(", "));
        logger.debug("list==>" + list.get(1));
        map.put("sfStack_" + i, list);
        map.remove("sfStack");
        redisTemplate.opsForHash().putAll(map.get("key").toString(), map);

    }

    @Override
    public void setStatus(Map map) {
        if(redisTemplate.hasKey(map.get("key").toString()) == null){
            System.out.println("不存在key");
        }else{
            redisTemplate.opsForHash().putAll(map.get("key").toString(), map);
        }
    }

    @Override
    public List<Map> getList() {
        Set<String> keys = redisTemplate.keys("Token_*");
        List<Map> list = new ArrayList<>();
        assert keys != null;
        for(String key : keys){
            Map map = redisTemplate.opsForHash().entries(key);
            list.add(map);
        }
        return list;
    }

    @Override
    public Map<String, List> getFDMap(String key) {
        Map map = redisTemplate.opsForHash().entries(key);
        Map resultMap = new HashMap();
        for(Object s : map.keySet()){
            if(s.toString().startsWith("sfStack_")){
                resultMap.put(s,map.get(s));
            }
        }
        return resultMap;
    }

}
