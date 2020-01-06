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
        if (redisTemplate.hasKey(map.get("ip").toString() + ":FileDel") == null || !redisTemplate.hasKey(map.get("ip").toString() + ":FileDel")) {
            System.out.println("不存在" + map.get("ip").toString() + ":FileDel");
            hash.putAll(map.get("ip").toString() + ":FileDel", map);
        }
        if (redisTemplate.hasKey(map.get("ip").toString() + ":SqlDel") == null || !redisTemplate.hasKey(map.get("ip").toString() + ":SqlDel")) {
            System.out.println("不存在" + map.get("ip").toString() + ":SqlDel");
            hash.putAll(map.get("ip").toString() + ":SqlDel", map);
        }

    }

    @Override
    public Map getByKey(Map map) {
        return redisTemplate.opsForHash().entries(map.get("key").toString());
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
        redisTemplate.opsForHash().putAll(map.get("ip").toString() + ":FileDel", map);

    }

    @Override
    public void setStatus(Map map) {
        if (redisTemplate.hasKey(map.get("key").toString()) == null) {
            System.out.println("不存在key");
        } else {
            redisTemplate.opsForHash().putAll(map.get("key").toString(), map);
        }
    }

    @Override
    public List<Map> getList() {
        Set<String> keys = redisTemplate.keys("*");
        Set<String> ips = new HashSet<>();
        assert keys != null;
        for (String k :
                keys) {
            ips.add(k.substring(0, k.indexOf(":")));
        }
        System.out.println(ips);
        List<Map> list = new ArrayList<>();
        for (String ip : ips) {
            Map map = new HashMap();
            map.put("ip", ip);
            //Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(ip + ":FileDel", ScanOptions.scanOptions().match("sfStack_*").build());
            if(redisTemplate.opsForHash().hasKey(ip+":FileDel","sfStack_1")){
                map.put("fileDel",true);
            }
            /*while (cursor.hasNext()) {
                Map.Entry<Object, Object> entry = cursor.next();
                System.out.println("***---"+entry.getKey()+"===>"+entry.getValue());
            }*/
            if (redisTemplate.opsForHash().hasKey(ip + ":SqlDel", "sdStack_1")) {
                map.put("sqlDel", true);
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public Map<String, List> getFDMap(String ip) {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(ip + ":FileDel", ScanOptions.scanOptions().match("sfStack_*").build());
        Map resultMap = new HashMap();
        //Map map = redisTemplate.opsForHash().entries(ip + ":FileDel");
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            List<String> list = Arrays.asList(entry.getValue().toString().split(","));
            for(String s:list){
                if(s.contains("weaver.")){
                    resultMap.put(entry.getKey(),s);
                    break;
                }else if(s.contains("_jsp")){
                    resultMap.put(entry.getKey(),s);
                    break;
                }else if(s.contains("com.cloudstore.")) {
                    resultMap.put(entry.getKey(),s);
                    break;
                }else {
                        resultMap.put(entry.getKey(),entry.getValue());
                }
            }
        }

        /*for(Object s : map.keySet()){
            if(s.toString().startsWith("sfStack_")){
                resultMap.put(s,map.get(s));
            }
        }*/
        return resultMap;
    }

}
