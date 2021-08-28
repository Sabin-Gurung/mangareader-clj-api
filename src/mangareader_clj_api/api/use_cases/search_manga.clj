(ns mangareader-clj-api.api.use-cases.search-manga
  (:require
    [mangareader-clj-api.services.mangareader :as m]
    [schema.core :as s])
  )

(s/defschema SearchResDto
  {:source     s/Str
   :offset     s/Int
   :totalPages s/Int
   :mangas     [{
                 :id        s/Str
                 :chapters  s/Int
                 :title     s/Str
                 :thumbnail s/Str}]})

(defn execute [term page]
  (let [res (m/search term page)]
    (-> (select-keys res [:mangas :source :offset])
        (assoc :totalPages (:total-pages res)))))

(comment
  (execute "bleach" nil)
  (m/search "bleach" nil)
  )
