package weaver.agent.server.service.Impl;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ScanOptions;
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
        if(redisTemplate.hasKey(map.get("ip").toString() + ":Type") == null ||!redisTemplate.hasKey(map.get("ip").toString() + ":Type")){
            System.out.println("不存在" + map.get("ip").toString() + ":Type");
            map.put("FileDel",0);
            map.put("SqlDel",0);
            hash.putAll(map.get("ip").toString() + ":Type", map);
        }
    }

    @Override
    public void setSqlDel(Map map) {
        if (redisTemplate.hasKey(map.get("key").toString()) == null) {
            System.out.println("不存在key");
        } else {
            Set<Object> keySet = redisTemplate.opsForHash().keys(map.get("key").toString());
            int i = 0;
            while (keySet.contains("sdStack_" + i)) {
                i++;
            }
            map.put("sdStack_" + i, map.get("sdStack").toString().split(", "));
            map.remove("sdStack");
            redisTemplate.opsForHash().putAll(map.get("key").toString(), map);
        }
    }

    @Override
    public void setfileDel(Map map) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        int i = 0;
        while (redisTemplate.hasKey(map.get("ip").toString() + ":FileDel:"+i)) {
            i++;
        }
        List<String> list = Arrays.asList(map.get("sfStack").toString().split(","));
        for(String s:list){
            if(s.contains("weaver.")){
                map.put("fdkeyword",s);
                break;
            }else if(s.contains("_jsp")){
                map.put("fdkeyword",s);
                break;
            }else if(s.contains("com.cloudstore.")) {
                map.put("fdkeyword",s);
                break;
            }else {
                map.put("fdkeyword",list.get(list.indexOf(" java.io.File.delete(File.java)")+1));
            }
        }
        hash.put(map.get("ip").toString() + ":Type","FileDel",Integer.parseInt(hash.get(map.get("ip").toString() + ":Type","FileDel").toString())+1);
        hash.putAll(map.get("ip").toString() + ":FileDel:"+i,map);
    }


    @Override
    public List<Map> getList() {
        Set<String> keys = redisTemplate.keys("*:Type");
        List<Map> list = new ArrayList<>();
        assert keys != null;
        for (String k :
                keys) {
            Map map = new HashMap();
            map.putAll(redisTemplate.opsForHash().entries(k));
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map> getFDMap(String ip) {

        Set<String> keys = redisTemplate.keys(ip+":FileDel:*");
        List<Map> list = new ArrayList<>();
        for(String k :
                keys){
            Map map = new HashMap();
            map.putAll(redisTemplate.opsForHash().entries(k));
            list.add(map);
        }
        return list;
    }

}
