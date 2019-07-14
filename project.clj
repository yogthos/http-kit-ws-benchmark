
(defproject websocket "0.1.0"
  :description "A test Websocket server"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [hiccup "1.0.5"]
                 [http-kit "2.3.0"]
                 [org.clojure/tools.logging "0.4.1"]
                 [environ "1.1.0"]
                 [metosin/reitit-ring "0.3.9"]
                 [metosin/jsonista "0.2.3"]]

  :plugins [[io.taylorwood/lein-native-image "0.3.0"]
            [nrepl/lein-nrepl "0.3.2"]]

  :aot :all

  :main websocket.main

  :native-image {:name     "app"
                 :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
                 :opts     ["--enable-url-protocols=http"
                            "--report-unsupported-elements-at-runtime"
                            "--initialize-at-build-time"
                            "--allow-incomplete-classpath"
                            ;;avoid spawning build server
                            "--no-server"]})
