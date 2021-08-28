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

(def a-manga-chapters-res
  {:id       a-manga-id
   :chapters (:chapters a-manga)
   :total    1})

(def a-manga-chapter-1
  {:manga-id   "manga-rb968358"
   :chapter-id "chapter-1"
   :title      "Pieces Of You\nVol.1 Chapter 1"
   :contents   ["http://cm.blazefast.co/f8/b5/f8b534f378e37faed631c1d319a46307.jpg"
                "http://cm.blazefast.co/93/ab/93abe603170ea09590f71373cd528ac0.jpg"
                "http://cm.blazefast.co/42/55/4255e60c5ff7a50177a457ff5e28ed92.jpg"
                "http://cm.blazefast.co/63/81/6381afdf71ef596b8e24cd630cd7c796.jpg"
                "http://cm.blazefast.co/78/62/78624db5760e0d539a4cde7359cd63af.jpg"
                "http://cm.blazefast.co/a8/b2/a8b2d83ce9d133f7c52f8ddb6482c1c4.jpg"
                "http://cm.blazefast.co/ad/6a/ad6a721688489fa40518a276433ee2f7.jpg"
                "http://cm.blazefast.co/85/eb/85ebb6712949f042629965c9d05b8d9c.jpg"
                "http://cm.blazefast.co/28/62/2862be05e3cbd098c66581785de556e3.jpg"
                "http://cm.blazefast.co/85/b7/85b73995ca683785449ec90a2c2fd226.jpg"
                "http://cm.blazefast.co/7f/4a/7f4abc656c8f6fffbc5eb10c18610053.jpg"
                "http://cm.blazefast.co/42/4d/424dde6a0b91e67599a5411fb25088b9.jpg"]
   :source     "https://mangareader.tv/chapter/manga-rb968358/chapter-1"})

(def a-manga-chapter-1-response
  (-> (select-keys a-manga-chapter-1 [:title :contents :source])
      (assoc :mangaId "manga-rb968358")
      (assoc :chapterId "chapter-1")))

(def a-manga-response
  (-> a-manga
      (assoc :id a-manga-id)
      (update :chapters count)))
