(ns mangareader-clj-api.core
  (:require
    [mangareader-clj-api.api.handler :refer [app]]
    [org.httpkit.server :as server]
    [taoensso.timbre :as log]
    [config.core :refer [env]]
    )
  (:gen-class))

(defn -main
  [& args]
  (let [[port & _] args
        p (or (:port env) port 8080)
        p (cond-> p (string? p) (Integer/parseInt))]
    (log/info (str "Running server on port :" p))
    (server/run-server app {:port p})))

(comment
  (-main 3000)
  )
