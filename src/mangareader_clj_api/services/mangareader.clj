(ns mangareader-clj-api.services.mangareader
  (:require [org.httpkit.client :as http]
            [org.httpkit.sni-client :refer [default-client]]
            [net.cgrand.enlive-html :as en]
            [clojure.string :as str]
            ))

(def BASE-URL "https://mangareader.tv")

(defn -GET [endpoint]
  (-> @(http/get endpoint {:client @default-client})
      :body
      en/html-snippet))

(defn -parse-manga-genres [html]
  (->> (en/select html [:table.d41 :tbody :td :a.d42])
       (mapcat :content)
       (map str/trim)))

(defn -parse-manga-title [html]
  (->> (en/select html [:table.d41 :tbody :td :span.name])
       (mapcat :content)
       (map str/trim)
       first))

(defn -parse-manga-chapters [html]
  (->> (en/select html [:table.d48 :a])
       (map #(hash-map
               :id (-> % :attrs :href (str/split #"/") last)
               :source (-> % :attrs :href (->> (str BASE-URL)))
               :title (-> % :content first str/trim)))))

(defn -parse-chapter-title [html]
  (->> (en/select html [:h1.d55])
       (mapcat :content)
       (map str/trim)
       first))

(defn -parse-search-mangas [html]
  (let [parse-thumbnail (fn [h] (->> (en/select h [:div.d56])
                                     (map #(-> % :attrs :data-src ((partial str BASE-URL))))
                                     first))
        parse-title #(->> (en/select % [:div.d57 :a])
                          (mapcat :content)
                          first)
        parse-id #(->> (en/select % [:div.d57 :a])
                       (map (fn [h] (-> h :attrs :href (str/split #"/") last)))
                       first)
        parse-chapter-count (fn [h] (->> (en/select h [:div.d58])
                                         (mapcat :content)
                                         (into [] (comp
                                                    (map #(str/split % #" "))
                                                    (map first)
                                                    (map str/trim)
                                                    (map #(Integer/parseInt %))))
                                         first))]
    (->> (en/select html [:div.d54 :tr])
         (map #(-> {}
                   (assoc :id (parse-id %))
                   (assoc :total-chapters (parse-chapter-count %))
                   (assoc :title (parse-title %))
                   (assoc :thumbnail (parse-thumbnail %)))))))

(defn -parse-search-total-pages [html]
  (-> (en/select html [:div.d70 :a])
      (->> (mapcat :content) last)
      (str/split #"\(")
      last
      (str/split #"\)")
      first
      (str/trim)
      Integer/parseInt))

(defn -parse-chapter-contents [html]
  (->> (en/select html [:div#ib :img])
       (map (comp :data-src :attrs))))

(defn manga [id]
  "Fetches the manga information and chapter list for a manga"
  (let [api (str BASE-URL "/manga/" id)
        html (-GET api)]
    (-> {}
        (assoc :title (-parse-manga-title html))
        (assoc :genres (-parse-manga-genres html))
        (assoc :chapters (-parse-manga-chapters html))
        (assoc :source api))))

(defn chapter [manga-id chapter-id]
  "Fetches chapter information and contents for a manga chapter"
  (let [api (str BASE-URL "/chapter/" manga-id "/" chapter-id)
        html (-GET api)]
    (-> {}
        (assoc :manga-id manga-id)
        (assoc :chapter-id chapter-id)
        (assoc :title (-parse-chapter-title html))
        (assoc :contents (-parse-chapter-contents html))
        (assoc :source api))))

(defn search [term page]
  "Queries a term and returns matchin result"
  (let [query-term (-> term str/trim (str/replace #" " "+"))
        query-page (or page 1)
        api (str BASE-URL "/search/?w=" query-term "&page=" query-page)
        html (-GET api)]
    (-> {}
        (assoc :mangas (-parse-search-mangas html))
        (assoc :source api)
        (assoc :offset query-page)
        (assoc :total-pages (-parse-search-total-pages html)))))

(comment
  (string? ["asdf"])
  (->
    (-GET "https://mangareader.tv/search/?w=one+piece&page=2")
    ;(-parse-search-mangas)
    ;(-parse-search-total-pages)
    (en/select [:div.d70 :a])
    ;(->> (mapcat :content)
    ;     last
    ;     )
    ;(str/split #"\(")
    ;last
    ;(str/split #"\)")
    ;first
    ;(str/trim)
    ;Integer/parseInt
    ;first

    ;(en/select [:div.d57 :a])
    ;(->> (map (fn [h]
    ;            (-> h
    ;                :attrs :href
    ;                (str/split #"/")
    ;                last)))
    ;     first)

    ;(en/select [:div.d58])
    ;(->> (mapcat :content)
    ;     (map #(str/split % #" "))
    ;     (map first)
    ;     (map str/trim)
    ;     (map #(Integer/parseInt %))
    ;     first)

    )
  (Integer/parseInt "1")

  (->
    (-GET "https://mangareader.tv/manga/manga-rb968358")
    (en/select [:table.d48 :a])
    ;(-parse-manga-title)
    ;(-parse-manga-genres)
    ;(-parse-manga-chapters)
    )
  (->
    (manga "tkqu521609849722")
    ;(dissoc :chapters)
    )


  (->
    (-GET "https://mangareader.tv/chapter/manga-aa951409/chapter-1")
    ;(-parse-chapter-title)
    ;(-parse-chapter-contents)
    )

  (-> (-GET "https://mangareader.tv/search/?w=one+piece&page=1")
      ;(en/select [:div.d70 :a])
      ;last
      ;:content
      ;first
      )
  ;(chapter "manga-aa951409" "chapter-1")
  (search "one piece" nil)
  (manga "manga-rb968358")
  (manga "manga-lw988705")
  (chapter "manga-lw988705" "chapter-1")
  ;(search "odsfvafjalsfdsl;fkjlne" nil)
  ;(search "odsfvafjalsfdsl;fkjlne" 1)
  ;(search "odsfvafjalsfdsl;fkjlne" 2)

  )
