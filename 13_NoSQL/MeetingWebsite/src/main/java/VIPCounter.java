public class VIPCounter implements Runnable {
    RedisStorage redis;

    VIPCounter(RedisStorage redis) {
        this.redis = redis;
    }

    public void run() {
        for (;;) {
            int i = Math.toIntExact(Math.round(Math.random() * 20));
            synchronized (redis) {
                redis.makeUserVIP(i);
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
