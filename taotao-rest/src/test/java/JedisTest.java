import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 * @author w.x.y
 * @version V1.0
 * @Package PACKAGE_NAME
 * @Date 2017/9/15 9:42
 * @Modified
 */
public class JedisTest {


    /**
     * 测试连接单库
     *
     * @param []
     * @return void
     * @method testSingle
     * @author w.x.y
     * @date 2017/9/15 9:44
     */
    @Test
    public void testSingle() {

        /*
        1. 创建连接
        2. 使用命令进行set get操作
        3. 关闭连接
         */
        Jedis jedis = new Jedis("192.168.100.202", 6379);
        jedis.set("key1", "123");
        String s = jedis.get("key1");
        System.out.println(s);
        jedis.close();
    }

    /**
     * 测试连接池
     *
     * @param []
     * @return void
     * @method testPool
     * @author w.x.y
     * @date 2017/9/15 9:48
     */
    @Test
    public void testPool() {
       /* 由于不断的开关连接，会造成性能上面的浪费，故使用连接池。
       1. 创建连接池
       2. 从连接池里面获取一个资源对象
       3. 使用set get命令操作数据库
       4. 关闭资源
        */
        JedisPool jedisPool = new JedisPool("192.168.100.202", 6379);
        Jedis resource = jedisPool.getResource();
        String s = resource.get("key1");
        System.out.println(s);
        resource.close();

    }

    /**
     *
     * @method testCluster
     * @param []
     * @return void
     * @author w.x.y
     * @date 2017/9/15 10:08
     */
    @Test
    public void testCluster() {

        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.100.202", 7001));
        nodes.add(new HostAndPort("192.168.100.202", 7002));
        nodes.add(new HostAndPort("192.168.100.202", 7003));
        nodes.add(new HostAndPort("192.168.100.202", 7004));
        nodes.add(new HostAndPort("192.168.100.202", 7005));
        nodes.add(new HostAndPort("192.168.100.202", 7006));
        JedisCluster cluster = new JedisCluster(nodes);
        cluster.set("hello", "wxy");
        cluster.set("gyl", "wxy");
        cluster.set("henan", "zhengzhou");
        cluster.set("guangzhou", "shenzhen");
        String hello = cluster.get("hello");
        String gyl = cluster.get("gyl");
        String henan = cluster.get("henan");
        String guangzhou = cluster.get("guangzhou");
        System.out.println("hello: " + hello + "\ngyl: " + gyl + "\nhenan: " + henan + "\nguangzhou: " + guangzhou);
    }

}
