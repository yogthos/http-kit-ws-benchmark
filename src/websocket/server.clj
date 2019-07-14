(ns websocket.server
  (:require
   [reitit.ring :as ring]
   [jsonista.core :as json]
   [org.httpkit.server :refer [send! with-channel on-close on-receive]]))

(defonce channels (atom #{}))

(defn connect! [channel]
  (swap! channels conj channel))

(defn disconnect! [channel status]
  (swap! channels disj channel))

(defn broadcast [ch payload]
  (let [msg (json/write-value-as-string {:type "broadcast" :payload payload})]
    (run! #(send! % msg) @channels))
  (send! ch (json/write-value-as-string {:type "broadcastResult" :payload payload})))

(defn echo [ch payload]
  (send! ch (json/write-value-as-string {:type "echo" :payload payload})))

(defn unknown-type-response [ch _]
  (send! ch (json/write-value-as-string {:type "error" :payload "ERROR: unknown message type"})))

(defn dispatch [ch msg]
  (let [parsed (json/read-value msg)]
    ((case (get parsed "type")
        "echo" echo
        "broadcast" broadcast
        unknown-type-response)
      ch (get parsed "payload"))))

(defn ws-handler [request]
  (with-channel request channel
    (connect! channel)
    (on-close channel #(disconnect! channel %))
    (on-receive channel #(dispatch channel %))))

(defonce app
  (ring/ring-handler
   (ring/router
    [["/ws" {:get ws-handler}]])))
