### Prerequisites

* JDK
* GraalVM
* Leiningen

### Development

    lein run

### Building

To build the server:

    lein uberjar

Then, to run the server:

    java -jar target/websocket-0.1.0-standalone.jar

or 

    java -server -XX:+AggressiveOpts -XX:+UseG1GC -XX:MaxGCPauseMillis=50 -Xms8g -Xmx8g -jar target/websocket-0.1.0-standalone.jar # defaults to port 3000


To build the server using GraalVM:

    lein native-image

Then, run:

    target/app

### Benchmarking with Artillery

    npm install -g artillery
    artillery run benchmark.yml