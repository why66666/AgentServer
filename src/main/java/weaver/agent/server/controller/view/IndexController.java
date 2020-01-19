package weaver.agent.server.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import weaver.agent.server.commons.TestThread;
import weaver.agent.server.commons.ThreadTree;
import weaver.agent.server.listener.SessionListener;

/**
 * @author w
 * @date 2020-01-03 18:18
 */
@Controller
public class IndexController {
    @RequestMapping(value = "/index.html")
    public String index(Model model) throws InterruptedException {
        ThreadTree threadTree = new ThreadTree(Thread.currentThread().getStackTrace()[1].getMethodName().toString(),1000,null);
        ThreadTree.set(threadTree);
        model.addAttribute("sessionCount", SessionListener.userCount);
        new TestThread().printThread();
        return "index";
    }
    @RequestMapping(value = "/")
    public String ForwordIndex(Model model) throws InterruptedException {
        new TestThread().printThread();
        return "forward:/index.html";
    }
}
