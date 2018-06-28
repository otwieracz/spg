(defproject spg "0.3.0-SNAPSHOT"
  :description "scenery_pack.ini generator for X-Plane Scenery Manager"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [me.raynes/fs "1.4.6"]
                 [clj-struct "0.1.0"]
                 [seesaw "1.4.5"]]
  :main ^:skip-aot spg.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
