(ns spg.scenerypacks
  (:require [clojure.string :as string]
            [spg.utils :as utils]
            [spg.scenemanager :as scenemanager]))

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


(defn make-scenerypacks-ini
  "Generate scenery_packs.ini file for `SCENERIES` list"
  [sceneries]
  (str +header+
       (utils/newl)
       (string/join (utils/newl)
                    (map make-scenery-pack-line
                         sceneries))))

