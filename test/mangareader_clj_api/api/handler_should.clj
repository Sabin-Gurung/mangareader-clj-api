(ns mangareader-clj-api.api.handler-should
  (:require [clojure.test :refer :all]
            [mock-clj.core :refer [with-mock calls]]
            [mangareader-clj-api.api.handler :refer [app]]
            [ring.mock.request :as ring-mock]
            [jsonista.core :as j]
            [mangareader-clj-api.services.mangareader :as m]
            [mangareader-clj-api.api.data :as data]
            )
  )

(defn parse-response [body]
  (cond-> body (not (map? body)) (j/read-value j/keyword-keys-object-mapper)))


(deftest testing-health-endpoint
  (testing "GET /health endpoint"
    (let [req (ring-mock/request :get "/manga-api/health")
          {:keys [status headers body error] :as response} (app req)
          res (parse-response body)]
      (is (= 200 status))
      (is (= {:status "ok"} res)))))

(deftest testing-manga-endpoint
  (testing "GET /manga/{} endpoint"
    (with-mock [m/manga data/a-manga]
               (let [req (ring-mock/request :get "/manga-api/manga/manga-rb968358")
                     {:keys [status headers body error] :as response} (app req)
                     res (parse-response body)]
                 (is (= 200 status))
                 (is (= data/a-manga-response res))
                 (is (= [[data/a-manga-id]] (calls m/manga)))))))

(deftest testing-manga-chapters-endpoint
  (testing "GET /manga/:id/chapters endpoint"
    (with-mock [m/manga data/a-manga]
               (let [req (ring-mock/request :get "/manga-api/manga/manga-rb968358/chapters")
                     {:keys [status headers body error] :as response} (app req)
                     res (parse-response body)]
                 (is (= 200 status))
                 (is (= data/a-manga-chapters-res res))
                 (is (= [[data/a-manga-id]] (calls m/manga)))))))

(deftest testing-manga-chapters-endpoint
  (testing "GET /manga/:id/chapters/:chapter-id endpoint"
    (with-mock [m/chapter data/a-manga-chapter-1]
               (let [req (ring-mock/request :get "/manga-api/manga/manga-rb968358/chapters/chapter-1")
                     {:keys [status headers body error] :as response} (app req)
                     res (parse-response body)]
                 (is (= 200 status))
                 (is (= data/a-manga-chapter-1-response res))
                 (is (= [[data/a-manga-id "chapter-1"]] (calls m/chapter)))))))

