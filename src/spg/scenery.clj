(ns spg.scenery
  "Basic scenery parsing."
  (:require [spg.dsf :as dsf]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [clojure.string :as string]
            [clojure.set :as set]))

(def +builtin-libs+ '("lib" "objects"))

(defn- list-all-dsf
  "List all DSF files for scenery `SCENERY-DIR`"
  [scenery-dir]
  (reduce concat
          (filter #(not-empty %)
                  (fs/walk (fn [root dirs files]
                             (let [dsfs (filter #(string/ends-with? % ".dsf")
                                                files)]
                               (map #(fs/file root %)
                                    dsfs)))
                           scenery-dir))))

(defn- extract-dependencies
  "Extract list of dependencies from `DSF` file"
  [dsf]
  (distinct (map #(first (fs/split %))
                 (reduce concat (map val (dsf/get-atoms dsf))))))

(defn- scenery-provided-dependencies
  "List all dependencies which are provided by scenery `SCENERY-DIR`"
  [scenery-dir]
  (map #(last (fs/split %))
       (fs/list-dir scenery-dir)))

(defn- all-scenery-dependencies
  "List all dependencies for `SCENERY-DIR` DSF files"
  [scenery-dir]
  (distinct (reduce concat
                    (map extract-dependencies
                         (list-all-dsf scenery-dir))))
  )

(defn scenery-dependencies
  "List all external (not provided by the scenery itself) dependencies"
  [scenery-dir]
  (seq (set/difference (set (all-scenery-dependencies scenery-dir))
                       (set (scenery-provided-dependencies scenery-dir))
                       (set +builtin-libs+))))
