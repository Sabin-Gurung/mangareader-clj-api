(ns mangareader-clj-api.api.handler
  (:require
    [jsonista.core :as j]
    [mangareader-clj-api.api.routes.manga-routes :as manga-routes]
    [muuntaja.core :as m]
    [org.httpkit.server :as server]
    [reitit.coercion.schema]
    [reitit.ring :as ring]
    [reitit.ring.coercion :as coercion]
    [reitit.ring.middleware.exception :as exception]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [reitit.ring.middleware.parameters :as parameters]
    [reitit.swagger :as swagger]
    [reitit.swagger-ui :as swagger-ui]
    [ring.util.response :as ring-resp]
    [schema.core :as s]
    ))

(defn swagger-routes []
  ["" {:no-doc true}
   ["/swagger.json" {:get (swagger/create-swagger-handler)}]
   ["/api-docs/*" {:get (swagger-ui/create-swagger-ui-handler {:url "/manga-api/swagger.json"})}]])

(defn default-handlers []
  (ring/create-default-handler
    {:not-found          (constantly (ring-resp/not-found {:message "Endpoint not found"}))
     :method-not-allowed (constantly {:status 405, :body {:message "Method not allowed"}})
     :not-acceptable     (constantly {:status 406, :body {:message "Request not acceptable"}})}))

(def app
  (ring/ring-handler
    (ring/router
      ["/manga-api"
       ["/health" {:get (fn [_] {:status 200 :body {:status "ok"}})}]
       manga-routes/routes
       (swagger-routes)
       ]
      {:data {:coercion   reitit.coercion.schema/coercion
              :muuntaja   m/instance
              :middleware [parameters/parameters-middleware
                           muuntaja/format-negotiate-middleware
                           muuntaja/format-response-middleware
                           exception/exception-middleware
                           muuntaja/format-request-middleware
                           coercion/coerce-response-middleware
                           coercion/coerce-request-middleware]
              }})
    (ring/routes
      (ring/create-resource-handler {:path "/public"})
      (default-handlers)
      )
    ))

(comment
  (-> (app {:request-method :get :uri "/manga-api/manga/ehlo"})
      :body
      ;(j/read-value j/keyword-keys-object-mapper)
      )

  (app {:request-method :get :uri "/manga-api/swagger.json"})
  (app {:request-method :get :uri "/manga-api/api-docs/index.html"})
  (app {:request-method :get :uri "/lala"})
  (app {:request-method :get :uri "/public/test.txt"})
  (app {:request-method :get :uri "/manga-api/api-docs/swagger.json"})
  (def server (server/run-server app {:port 8080}))
  (server)
  )