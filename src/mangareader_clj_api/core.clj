(ns mangareader-clj-api.core
  (:require
    [mangareader-clj-api.api.handler :refer [app]]
    [org.httpkit.server :as server]
    )
  (:gen-class))

(defn -main
  [& args]
  (let [[port & _] args
        p (or port 8080)]
    (println "Running server on port 8080")
    (server/run-server app {:port p})))
