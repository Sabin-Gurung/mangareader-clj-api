(ns mangareader-clj-api.api.use-cases.fetch-manga-should
  (:require [clojure.test :refer :all]
            [mock-clj.core :refer [with-mock calls]]
            [mangareader-clj-api.api.use-cases.fetch-manga :as use-case]
            [mangareader-clj-api.services.mangareader :as m]
            [mangareader-clj-api.api.data :as data]
            ))

(deftest fetches-manga-information
  (testing "fetches-manga-information"
    (with-mock [m/manga data/a-manga]
               (is (= data/a-manga-response
                      (use-case/execute (:id data/a-manga-response))))
               (is (= [[(:id data/a-manga-response)]] (calls m/manga))))))
