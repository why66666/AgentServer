package weaver.agent.server.commons;

/**
 * @author w
 * @date 2020-01-15 00:34
 */
public class TestThread {
    public void printThread() throws InterruptedException {
        System.out.println(ThreadTree.get());
    }
    public void printThread2(){
        System.out.println(ThreadTree.get());
    }
}
