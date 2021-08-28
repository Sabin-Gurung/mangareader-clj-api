(ns mangareader-clj-api.api.use-cases.fetch-manga
  (:require [schema.core :as s]
            [mangareader-clj-api.services.mangareader :as m]
            ))

(s/defschema MangaDto
  {:id       s/Str
   :title    s/Str
   :genres   [s/Str]
   :chapters s/Int
   :source   s/Str})

(s/defschema ChaptersResponseDto
  {:id       s/Str
   :total    s/Int
   :chapters [{:source s/Str
               :title  s/Str
               :id     s/Str}]})

(defn execute [manga-id]
  (-> (m/manga manga-id)
      (assoc :id manga-id)
      (update :chapters count)))

(defn execute-chapters [manga-id]
  (let [{:keys [chapters]} (m/manga manga-id)]
    (-> {:id       manga-id
         :chapters chapters
         :total    (count chapters)
         })))


(comment
  (m/manga "manga-rb968358")
  )
