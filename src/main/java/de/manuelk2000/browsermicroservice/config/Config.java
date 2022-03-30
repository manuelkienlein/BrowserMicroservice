package de.manuelk2000.browsermicroservice.config;

public class Config {

    private String host = "127.0.0.1";
    private int port = 7000;
    private ScreenshotConfig screenshots = new ScreenshotConfig();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ScreenshotConfig getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(ScreenshotConfig screenshots) {
        this.screenshots = screenshots;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(String.format("Host: %s\n", host))
                .append(String.format("Port: %d\n", port))
                .toString();
    }

}
