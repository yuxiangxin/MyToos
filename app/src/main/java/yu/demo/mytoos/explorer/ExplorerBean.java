package yu.demo.mytoos.explorer;

import java.io.File;

import yu.demo.mytoos.fast.utils.IOUtil;

public class ExplorerBean implements Comparable<ExplorerBean> {

    private File file;
    private long length;
    private boolean isDir;

    public File getFile () {
        return file;
    }

    public ExplorerBean (File path) {
        this.file = path;
        isDir = file.isDirectory();
    }

    public long getLength () {
        return length;
    }

    public void setLength (long length) {
        this.length = length;
    }

    @Override
    public String toString () {
        return (isDir ? "目录:" : "文件:") + file + "\t" + IOUtil.formatFileLength(length);
    }

    @Override
    public int compareTo (ExplorerBean other) {
        long l = other.length - length;
        return l > 0 ? 1 : l < 0 ? -1 : 0;
    }
}
