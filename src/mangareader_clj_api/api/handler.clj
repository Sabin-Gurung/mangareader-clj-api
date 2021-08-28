(ns mangareader-clj-api.api.handler
  (:require [org.httpkit.server :as server]
            [reitit.ring :as ring]
            [reitit.swagger :as swagger]
            [schema.core :as s]
            [reitit.swagger-ui :as swagger-ui]
            [jsonista.core :as j]
            [reitit.ring.coercion :as coercion]
            [reitit.coercion.schema]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.exception :as exception]
            [reitit.ring.middleware.parameters :as parameters]
            [muuntaja.core :as m])

  )

(defn swagger-routes []
  ["" {:no-doc true}
   ["/swagger.json" {:get (swagger/create-swagger-handler)}]
   ["/api-docs/*" {:get (swagger-ui/create-swagger-ui-handler {:url "/manga-api/swagger.json"})}]])

(def app
  (ring/ring-handler
    (ring/router
      ["/manga-api"
       ["/health" {:get (fn [_] {:status 200 :body {:status "ok"}})}]
       ["/plus"
        {:get {:summary    "Add two numbers"
               :parameters {:query {:x s/Int :y s/Int}}
               :responses  {200 {:body {:total s/Int}}}
               :handler    (fn [{{{:keys [x y]} :query} :parameters}] {:status 200 :body {:total (+ x y)}})}
         }]
       [(swagger-routes)]]
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
      (ring/create-default-handler))
    ))

(comment
  (-> (app {:request-method :get :uri "/manga-api/health"})
      :body
      (j/read-value j/keyword-keys-object-mapper)
      )

  (app {:request-method :get :uri "/manga-api/swagger.json"})
  (app {:request-method :get :uri "/manga-api/api-docs/index.html"})
  (app {:request-method :get :uri "/lala"})
  (app {:request-method :get :uri "/public/test.txt"})
  (app {:request-method :get :uri "/manga-api/api-docs/swagger.json"})
  (def server (server/run-server app {:port 8080}))
  (server)
  )