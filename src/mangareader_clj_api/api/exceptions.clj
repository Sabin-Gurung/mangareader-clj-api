(ns mangareader-clj-api.api.exceptions
  (:require
    [reitit.ring.middleware.exception :as r-ex]
    [ring.util.response :as resp]
    [taoensso.timbre :as log]
    ))

(defn not-found-handler [exception req]
  (let [{:keys [message type]} (ex-data exception)]
    (resp/not-found {:message "Resource not found"
                     :title   message
                     :type    type
                     :uri     (:uri req)})))

(defn- get-req-body [{:keys [body] :as request}]
  (if (nil? body) "" (:params request)))

(defn request-log [request]
  {:url          (:uri request)
   :body         (get-req-body request)
   :query-params (:query-params request)})

(defn log-request-exception [request exception]
  (log/error {:service (:request-method request)
              :details {:request   (request-log request)
                        :exception exception}}))

(def middleware
  (r-ex/create-exception-middleware
    (merge
      r-ex/default-handlers
      {:api/not-found not-found-handler}
      )))

(comment
  )