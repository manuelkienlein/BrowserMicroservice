# Configuration

When starting the application, it will first check if there exists an configuration file "config.yml". If this file doesn't exists, it will use the default configuration the config file will be created.

Below is the default config.yml file:

```yml
host: "127.0.0.1"
port: 7000
screenshots:
  defaultWidth: 1920
  defaultHeight: 1200
  defaultBrowserEngine: "CHROME"
```

And here is a example of a configuration with all possible settings:

```yml
host: "127.0.0.1"
port: 7000
screenshots:
  defaultWidth: 1920
  defaultHeight: 1200
  defaultBrowserEngine: "CHROME"
storage:
  credentials:
    bucket: "bms-screenshots"
    region: "eu-central"
    accessKey: "key"
    secretKey: "secret"
    host: "localhost"
    port: 9000
    useSsl: false
```

If you want to use environment variables, you can include them by adding ```${env:PORT}```. Here is a full documentation about [config variables](https://commons.apache.org/proper/commons-text/apidocs/org/apache/commons/text/StringSubstitutor.html) using interpolation.