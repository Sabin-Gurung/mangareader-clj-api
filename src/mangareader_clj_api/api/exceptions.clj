(ns mangareader-clj-api.api.exceptions
  (:require
    [reitit.ring.middleware.exception :as r-ex]
    [ring.util.response :as resp]
    ))

(defn not-found-handler [exception req]
  (let [{:keys [message type]} (ex-data exception)]
    (resp/not-found {:message "Resource not found"
                     :title   message
                     :type    type
                     :uri     (:uri req)})))

(def middleware
  (r-ex/create-exception-middleware
    (merge
      r-ex/default-handlers
      {:api/not-found not-found-handler}
      )))

(comment
  )