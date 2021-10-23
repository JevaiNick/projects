import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class FileAccess implements AutoCloseable {
    FileSystem hdfs;

    /**
     * Initializes the class, using rootPath as "/" directory
     *
     * @param rootPath - the path to the root of HDFS,
     *                 for example, hdfs://localhost:32771
     */
    public FileAccess(String rootPath) throws URISyntaxException, IOException {
        Configuration configuration = new Configuration();
        configuration.set("dfs.client.use.datanode.hostname", "true");
        configuration.setBoolean("dfs.support.append", true);
        System.setProperty("HADOOP_USER_NAME", "root");
        hdfs = FileSystem.get(
                new URI(rootPath), configuration
        );
    }

    /**
     * Creates empty file or directory
     *
     * @param path
     */
    public void create(String path) throws IOException {
        Path file = new Path(path);

        if (hdfs.exists(file)) {
            hdfs.delete(file, true);
        }
        hdfs.create(file).close();
    }

    /**
     * Appends content to the file
     *
     * @param path
     * @param content
     */
    public void append(String path, String content) throws IOException {

        boolean isAppendable = Boolean.parseBoolean(hdfs.getConf().get("dfs.support.append"));
        if (isAppendable) {
            Path file = new Path(path);
            OutputStream os = hdfs.create(file);
            BufferedWriter br = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8")
            );

            for(int i = 0; i < 10_00; i++) {
                br.write(getRandomWord() + " ");
            }

            br.flush();
            br.close();
//            try (FSDataOutputStream append = hdfs.append(file)) {
//                append.writeChars(content);
//            }

        }
    }

    /**
     * Returns content of the file
     *
     * @param path
     * @return
     */
    public String read(String path) throws IOException {
        Path file = new Path(path);
        StringBuilder str = new StringBuilder();
        if (hdfs.exists(file)) {
            try (FSDataInputStream open = hdfs.open(file)) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(open))) {
                    String line = reader.readLine();
                    while (line != null) {
                        str.append(line);
                        line = reader.readLine();
                    }
                }
            }

            return str.toString();
        }
        return "File doesn't exist.";
    }

    /**
     * Deletes file or directory
     *
     * @param path
     */
    public void delete(String path) throws IOException {
        Path file = new Path(path);
        hdfs.delete(file, true);
    }

    /**
     * Checks, is the "path" is directory or file
     *
     * @param path
     * @return
     */
    public boolean isDirectory(String path) throws IOException {
        Path file = new Path(path);
        if (hdfs.isFile(file)) {
            return false;
        }
        return true;
    }

    /**
     * Return the list of files and subdirectories on any directory
     *
     * @param path
     * @return
     */
    public FileStatus[] list(String path) throws IOException {
        Path file = new Path(path);
        FileStatus[] files = hdfs.listStatus(file);

        return files;
    }

    @Override
    public void close() throws Exception {
        hdfs.close();
    }
    private static String symbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String getRandomWord()
    {
        StringBuilder builder = new StringBuilder();
        int length = 2 + (int) Math.round(10 * Math.random());
        int symbolsCount = symbols.length();
        for(int i = 0; i < length; i++) {
            builder.append(symbols.charAt((int) (symbolsCount * Math.random())));
        }

        return builder.toString();
    }
}
