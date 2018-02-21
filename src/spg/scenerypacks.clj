(ns spg.scenerypacks
  (:require [clojure.string :as string]
            [spg.utils :as utils]
            [spg.scenemanager :as scenemanager]
            [clojure.java.io :as io]))

(def +header+ (str "I"
                   (utils/newl)
                   "1000 Version"
                   (utils/newl)
                   "SCENERY"
                   (utils/newl)
                   (utils/newl)))

(def +start+ "SCENERY_PACK Custom Scenery/")

(defn- make-scenery-pack-line
  "Make SCENERY_PACK line from `SCENERY-NAME`"
  [scenery-name]
  (str +start+ scenery-name "/"))

(defn- scenery-pack-exists?
  "Check if scenery-pack `SCENERY-PACK` exists under `CUSTOM-SCENERY-DIR`"
  [custom-scenery-dir scenery-pack]
  (let [scenery-pack-dir (str custom-scenery-dir
                              (utils/fs-sep)
                              scenery-pack)]
    (.isDirectory (io/as-file scenery-pack-dir))))


(defn make-scenerypacks-ini
  "Generate scenery_packs.ini file for `SCENERIES` list only for entries actually existing under `CUSTOOM-SCENERY-DIR`. Call `HOOK` after every scenery finished. `HOOK` takes two arguments [sceneries-list current-scenery]"
  [custom-scenery-dir sceneries hook]
  ;; start by filtering, to leave only actually existing sceneries so it won't
  ;; list disabled ones
  (let [sceneries (filter #(scenery-pack-exists? custom-scenery-dir %)
                          sceneries)]
    {:scenery-pack-lines (str +header+
                              (utils/newl)
                              (string/join (utils/newl)
                                           (map #(doto (make-scenery-pack-line %)
                                                   (do
                                                     ;; Call `HOOK` with all sceneries and currently processed as arguments
                                                     (hook sceneries %)))
                                                sceneries)))
     :sceneries sceneries}))

