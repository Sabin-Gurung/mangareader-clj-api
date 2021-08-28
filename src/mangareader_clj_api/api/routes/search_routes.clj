(ns mangareader-clj-api.api.routes.search-routes
  (:require
    [mangareader-clj-api.api.use-cases.search-manga :as use-case]
    [schema.core :as s]
    )
  )

(def routes
  ["" {:tags ["Search"]}
   ["/search/:term"
    {:get {:summary    "Get list of mangas that matches a term"
           :parameters {:path  {:term s/Str}
                        :query {(s/optional-key :page) s/Int}}
           :responses  {200 {:body use-case/SearchResDto}}
           :handler    (fn [{{{term :term} :path
                              {page :page} :query} :parameters}]
                         (use-case/execute term page))}}]
   ])