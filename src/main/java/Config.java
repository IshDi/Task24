import java.io.File;

public class Config {
    protected boolean enabled;
    protected File fileName;
    protected String format;

    public Config(boolean enabled, String fileName, String format) {
        this.enabled = enabled;
        this.fileName = new File("./" + fileName);
        this.format = format;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public File getFileName() {
        return fileName;
    }

    public String getFormat() {
        return format;
    }
}
