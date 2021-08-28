(ns mangareader-clj-api.api.routes.manga-routes
  (:require
    [mangareader-clj-api.api.use-cases.fetch-manga :as use-case]
    [ring.util.response :as resp]
    [schema.core :as s]
    )
  )

(def routes
  ["" {:tags ["Manga"]}
   ["/manga/:id"
    {:get {:summary    "Get the information of a manga"
           :parameters {:path {:id s/Str}}
           :responses  {200 {:body use-case/MangaDto}}
           :handler    (fn [{{{id :id} :path} :parameters}]
                         (resp/response (use-case/execute id)))}}]

   ["/manga/:id/chapters"
    {:get {:summary    "Get list of chapters of a manga"
           :parameters {:path {:id s/Str}}
           :responses  {200 {:body use-case/ChaptersResponseDto}}
           :handler    (fn [{{{id :id} :path} :parameters}]
                         (resp/response (use-case/execute-chapters id)))}}]

   ["/manga/:id/chapters/:chapter-id"
    {:get {:summary    "Get list of chapters of a manga"
           :parameters {:path {:id s/Str :chapter-id s/Str}}
           :responses  {200 {:body use-case/ChapterDetailResponseDto}}
           :handler    (fn [{{{:keys [id chapter-id]} :path} :parameters}]
                         (resp/response (use-case/execute-chapter id chapter-id)))}}]

   ]
  )
