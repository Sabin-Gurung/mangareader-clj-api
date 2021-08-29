(defproject mangareader-clj-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[enlive "1.1.6"]
                 [http-kit "2.5.3"]
                 [metosin/jsonista "0.3.3"]
                 [metosin/reitit "0.5.15"]
                 [org.clojure/clojure "1.10.1"]
                 [prismatic/schema "1.1.12"]]
  :resource-paths ["resources"]
  :main ^:skip-aot mangareader-clj-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev     {:dependencies [[mock-clj "0.2.1"]
                                      [ring/ring-mock "0.4.0"]]}
             :prod    {}
             }
  )
