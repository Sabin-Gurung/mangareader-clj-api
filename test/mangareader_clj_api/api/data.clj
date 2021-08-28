(ns mangareader-clj-api.api.data
  (:require [clojure.test :refer :all]))

(def a-manga-id "manga-rb968358")

(def a-manga
  {:title    "Pieces Of You",
   :genres   '("Josei" "One shot" "Psychological" "Seinen" "Slice of life"),
   :chapters '({:source "https://mangareader.tv//chapter/manga-rb968358/chapter-1",
                :title  "Vol.1 Chapter 1",
                :id     "chapter-1"}),
   :source   "https://mangareader.tv/manga/manga-rb968358"})

(def a-manga-chapters-response
  {:id       a-manga-id
   :chapters (:chapters a-manga)
   :total    1})

(def a-manga-response
  (-> a-manga
      (assoc :id a-manga-id)
      (update :chapters count)))
