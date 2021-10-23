import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;

public class Main
{


    public static void main(String[] args) throws Exception {
        /*Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname", "true");
        System.setProperty("HADOOP_USER_NAME", "root");

        FileSystem hdfs = FileSystem.get(
            new URI("hdfs://localhost:8020"), configuration
        );
        Path file = new Path("hdfs://localhost:8020/test/file.txt");

        if (hdfs.exists(file)) {
            hdfs.delete(file, true);
        }

        OutputStream os = hdfs.create(file);
        BufferedWriter br = new BufferedWriter(
            new OutputStreamWriter(os, "UTF-8")
        );

        for(int i = 0; i < 10_000_000; i++) {
            br.write(getRandomWord() + " ");
        }

        br.flush();
        br.close();
        hdfs.close();*/
        //"hdfs://localhost:8020/test/file.txt"
        FileAccess fileAccess = new FileAccess("hdfs://4169a3f945f6:8020");
        fileAccess.create("hdfs://4169a3f945f6:8020/test/file.txt");
        fileAccess.append("hdfs://4169a3f945f6:8020/test/file.txt", " ");
        System.out.println(fileAccess.read( "hdfs://4169a3f945f6:8020/test/file.txt"));
    }


}
