package weaver.agent.server.commons;

/**
 * @author w
 * @date 2020-01-15 00:28
 */
public class ThreadTree {
    private static ThreadLocal<ThreadTree>  threadLocal = new ThreadLocal<>();
    public ThreadTree(String threadName, long runTime, ThreadTree childTree){
        this.setThreadName(threadName);
        this.setRunTime(runTime);
        this.setChildTree(childTree);
    }
    public static ThreadTree get(){
        return threadLocal.get();
    }
    public static void set(ThreadTree threadTree){
        threadTree.setRunTime(ThreadTree.get()==null?0:ThreadTree.get().getRunTime()+1);

            threadLocal.set(threadTree);


    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public ThreadTree getChildTree() {
        return childTree;
    }

    public void setChildTree(ThreadTree childTree) {
        this.childTree = childTree;
    }

    private String threadName;
    private long runTime;
    private ThreadTree childTree;

    @Override
    public String toString() {
        return "ThreadTree{" +
                "threadName='" + threadName + '\'' +
                ", runTime=" + runTime +
                '}';
    }
}
