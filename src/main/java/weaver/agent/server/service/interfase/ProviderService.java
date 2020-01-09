package weaver.agent.server.service.interfase;

import weaver.agent.server.bean.BaseBean;
import weaver.agent.server.service.BaseService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author w
 * @date 2019-12-01 22:41
 */
public interface ProviderService extends BaseService<BaseBean> {
    public void addEntry(Map map);
    public void setSqlDel(Map map);
    public void setfileDel(Map map);
    List<Map> getList();

    List<Map> getFDMap(String ip);
}
