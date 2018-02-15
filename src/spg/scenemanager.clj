(ns spg.scenemanager
  (:require [clojure.string :as string]))

(defn- read-scenemanager-ini
  "Read SceneManager.ini from `PATH` and split"
  [path]
  (string/split-lines (slurp path)))

(defn- filter-scenery-paths
  "Keep only those `LINES` which define scenery packs"
  [lines]
  ;; Keep only lines starting with `=`
  (filter #(string/starts-with? % "=")
          lines))

(defn- extract-scenery-name
  "Extract scenery name from `PATH`"
  [path]
  (last (string/split path #"(\\|/)")))

(defn scenery-packs
  "Get all scenery packs from SceneManager.ini [INI-FILE], skip `SKIP-N` first path components"
  [ini-file]
  (filter #(not (nil? %))
          (map extract-scenery-name
               (-> (read-scenemanager-ini ini-file)
                   (filter-scenery-paths)))))

