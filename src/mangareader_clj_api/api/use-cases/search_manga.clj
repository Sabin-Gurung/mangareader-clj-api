(ns mangareader-clj-api.api.use-cases.search-manga
  (:require
    [schema.core :as s])
  )

(s/defschema SearchResDto
  {:source     s/Str
   :offset     s/Int
   :totalPages s/Int
   :mangas     [{
                 :id            s/Str
                 :totalChapters s/Int
                 :title         s/Str
                 :thumbnail     s/Str}]})

(defn execute [term page]
  )
