# Screenshot API

**GET** ```localhost:7000/v1/screenshot/{url-encoded website url}```

This request takes a simple screenshot of the given website and returns the image in the response.

| Get parameter | Description              | Value     |
|---------------|--------------------------|-----------|
| width         | Width of the screenshot  | 1 - 10000 |
| height        | Height of the screenshot | 1 - 10000 |