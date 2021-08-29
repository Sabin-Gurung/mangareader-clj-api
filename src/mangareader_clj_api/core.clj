(ns mangareader-clj-api.core
  (:require
    [mangareader-clj-api.api.handler :refer [app]]
    [org.httpkit.server :as server]
    [taoensso.timbre :as log]
    )
  (:gen-class))

(defn -main
  [& args]
  (let [[port & _] args
        p (or port "8080")]
    (log/info (str "Running server on port :" p))
    (server/run-server app {:port (Integer/parseInt p)})))

(comment
  (-main 3000)
  )
