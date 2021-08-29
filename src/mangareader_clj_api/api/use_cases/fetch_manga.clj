(ns mangareader-clj-api.api.use-cases.fetch-manga
  (:require
    [mangareader-clj-api.services.mangareader :as m]
    [schema.core :as s]
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

(s/defschema ChapterDetailResponseDto
  {:mangaId   s/Str
   :chapterId s/Str
   :title     s/Str
   :contents  [s/Str]
   :source    s/Str})

(defn handle-error [message]
  (throw (ex-info message {:type    :api/not-found
                           :message message})))

(defn execute [manga-id]
  (if-let [manga (m/manga manga-id)]
    (-> manga
        (assoc :id manga-id)
        (update :chapters count))
    (handle-error (format "cannot find manga %s" manga-id))))

(defn execute-chapters [manga-id]
  (if-let [{:keys [chapters]} (m/manga manga-id)]
    (-> {:id       manga-id
         :chapters chapters
         :total    (count chapters)})
    (handle-error (format "cannot find manga %s" manga-id))))


(defn execute-chapter [manga-id chapter-id]
  (if-let [manga (m/chapter manga-id chapter-id)]
    (-> manga
        (select-keys [:title :contents :source])
        (assoc :mangaId (:manga-id manga))
        (assoc :chapterId (:chapter-id manga)))
    (handle-error (format "cannot find manga %s chapter %s" manga-id chapter-id))))

(comment
  (m/manga "manga-rb968358")
  (execute-chapter "manga-rb968358" "chapter-1")
  )
