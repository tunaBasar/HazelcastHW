import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.Random;

public class main {
    public static void main(String[] args) {
        Config config = new Config();
        NetworkConfig network = config.getNetworkConfig();
        network.setPublicAddress("172.17.0.2:5701");
        HazelcastInstance hz = Hazelcast.newHazelcastInstance(config);
        IMap<Integer,Integer> map = hz.getMap("numbers");

        for (int testsize: new int[]{20000,100000}) {
            long startTime = System.currentTimeMillis();
            Random random = new Random();
            for (int i = 0; i < testsize; i++) {
                map.put(i,random.nextInt());
            }
            long endTime = System.currentTimeMillis();
            System.out.println(testsize+" Put Süresi: "+(endTime - startTime)+"ms");
            startTime = System.currentTimeMillis();
            for (int i = 0; i < testsize; i++) {
                map.get(i);
            }
            endTime = System.currentTimeMillis();
            System.out.println(testsize+" Get Süresi: "+(endTime - startTime)+"ms");
        }
        hz.shutdown();
    }
}
