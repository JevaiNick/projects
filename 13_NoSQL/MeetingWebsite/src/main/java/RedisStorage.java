import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.ArrayList;

public class RedisStorage {
    private RedissonClient redisson;
    private RKeys rKeys;
    private RScoredSortedSet<String> users;
    private RScoredSortedSet<String> vipUsers;

    private final static String KEY = "WEBSITE_USERS";
    private final static String VIPKEY = "WEBSITE_VIP_USERS";

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            System.out.println("Не удалось подключиться к Redis");
            System.out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        users = redisson.getScoredSortedSet(KEY);
        vipUsers = redisson.getScoredSortedSet(VIPKEY);
        rKeys.delete(KEY);
        rKeys.delete(VIPKEY);
    }

    public void addingUser(int number){
        users.add(number, "User num." + String.valueOf(number));
    }
    public ArrayList<String> getNumberOfUsers(){
        Iterable<String> iterable = users.valueRange(Double.NEGATIVE_INFINITY,true, Double.POSITIVE_INFINITY, true);
        ArrayList<String> numbersOfUsers = new ArrayList<String>();
        for (String number : iterable){
            numbersOfUsers.add(number);
        }
        return numbersOfUsers;
    }
    public ArrayList<String> getVIPNumberOfUsers(){
        Iterable<String> iterable = vipUsers.valueRange(Double.NEGATIVE_INFINITY,true, Double.POSITIVE_INFINITY, true);
        ArrayList<String> numbersOfUsers = new ArrayList<String>();
        for (String number : iterable){
            numbersOfUsers.add(number);
        }
        return numbersOfUsers;
    }

    public void makeUserVIP(int number){
        addingVIPUser(number);
    }
    public void clearVIP(){
        vipUsers.clear();
    }

    public void addingVIPUser(int number){
        vipUsers.add(number, "VIP User num." + String.valueOf(number));
    }

    void shutdown() {
        redisson.shutdown();
    }
}
