(ns mangareader-clj-api.api.exceptions
  (:require
    [reitit.ring.middleware.exception :as r-ex]
    [ring.util.response :as resp]
    [taoensso.timbre :as log]
    ))

(defn not-found-handler [exception req]
  (let [{:keys [message] :as detail} (ex-data exception)]
    (resp/not-found {:detail detail
                     :title  message
                     :type   "problem:invalid-chapter-or-manga"
                     :uri    (:uri req)})))

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

(defn exception-500-handler [exception req]
  (log-request-exception req exception)
  {:status  500
   :body    {:detail (.getMessage exception)
             :title  "There was an unknown error"
             :type   "problem:internal-server-error"
             :uri    (:uri req)}
   :headers {}})

(def middleware
  (r-ex/create-exception-middleware
    (merge
      r-ex/default-handlers
      {:api/not-found not-found-handler
       ::r-ex/default exception-500-handler})))

(comment
  )