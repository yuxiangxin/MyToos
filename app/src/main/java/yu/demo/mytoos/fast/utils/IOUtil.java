package yu.demo.mytoos.fast.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class IOUtil {

    public static final String UTF_8 = "UTF-8";


    public static void streamReadWrite (BufferedInputStream in, BufferedOutputStream out) throws IOException {
        int len = -1;
        while ((len = in.read()) != -1) {
            out.write(len);
        }
    }

    public static void streamReadWrite (InputStream in, OutputStream out) throws IOException {
        streamReadWrite(new BufferedInputStream(in), new BufferedOutputStream(out));
    }

    public final static String RN = "\r\n";

    public static InputStream openFileStream (File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getContent (InputStream in, String encode, boolean readFirstline) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    in, encode));
            if (readFirstline) {
                return reader.readLine();
            }
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + RN);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String getContent (InputStream in) {
        return getContent(in, UTF_8, false);
    }

    public static String getContent (InputStream in, String endoce) {
        return getContent(in, endoce, false);
    }

    public static String getFirstLine (InputStream in) {
        return getContent(in, UTF_8, true);
    }


    public static String getFirstLine (InputStream in, String encode) {
        return getContent(in, encode, true);
    }

    public static String getFirstLine (File file) {
        return getFirstLine(openFileStream(file));
    }

    public static void charReadWrite (BufferedReader in, BufferedWriter out) throws IOException {
        String line = null;
        while ((line = in.readLine()) != null) {
            out.write(line);
        }
    }


    public static void charReadWrite (Reader in, Writer out) throws IOException {
        charReadWrite(new BufferedReader(in), new BufferedWriter(out));
    }


    public static String getFileContentByReader (InputStreamReader in) throws IOException {
        return getFileContentByBufferedReader(new BufferedReader(in));
    }


    public static String getFileContentByBufferedReader (BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = in.readLine()) != null) {
            sb.append(line + RN);
        }
        if (sb.length() == 0) {
            return null;
        }
        return sb.substring(0, sb.length() - 2);
    }


    public static String getFileContext (File file, String coding) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(
                    file), coding));
            return getFileContentByBufferedReader(in);
        } finally {
            ioClose(in);
        }
    }


    public static String getFileContent (File file) throws IOException {
        return getFileContext(file, "UTF-8");
    }


    public static String getFileContent (File file, String coding) throws IOException {
        return getFileContext(file, coding);
    }

    public static void writeContent (String content, File file, boolean append) throws IOException {
        writeContent(content, UTF_8, file, append);
    }


    public static synchronized void writeContent (String content, String coding, File file, boolean append) throws IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), coding));
            out.write(content);
        } finally {
            ioClose(out);
        }
    }

    public static void ioClose (Closeable... in) {
        if (in == null)
            return;
        for (Closeable closeable : in) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    //doNothing
                }
            }
        }
    }
}
