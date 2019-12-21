package weaver.agent.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import weaver.agent.server.bean.BaseBean;
import weaver.agent.server.service.BaseService;

import java.text.SimpleDateFormat;

/**
 * @author w
 * @date 2019-12-01 21:16
 */
public class CrudController<T extends BaseBean,S extends BaseService<T>> extends BaseController {
    @Autowired
    protected S service;
    protected SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
}
