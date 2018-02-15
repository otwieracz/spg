(ns spg.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [spg.utils :as utils]
            [spg.scenemanager :as sm]
            [spg.scenerypacks :as sp]))

(defn make-scenery-packs-ini
  "Generate new `scenery_packs.ini` from provided `SceneManager.ini`"
  [dest-file scene-manager-ini]
  (with-open [w (io/writer dest-file)]
    (.write w (sp/make-scenerypacks-ini (sm/scenery-packs scene-manager-ini)))))

(def +cli-options+
  [["-p" "--scenery_packs FILE" "Path to scenery_packs.ini"
    :id :scenery-packs
    :default (if (utils/windows?)
               "Custom Scenery\\scenery_packs.ini"
               "./Custom Scenery/scenery_packs.ini"
               )]
   ["-m" "--scenemanager FILE" "Path to SceneManager.ini"
    :id :scenemanager
    :default (if (utils/windows?)
               "MODS\\SceneManager.ini"
               "./MODS/SceneManager.ini")]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [parsed-opts (parse-opts args +cli-options+)
        options (:options parsed-opts)]
    (if (:help options)
      ;; print help if `--help`
      (println (:summary parsed-opts))
      ;; or continue
      (do
        (print (str "Creating " (:scenery-packs options) "... "))
        (make-scenery-packs-ini (:scenery-packs options)
                                (:scenemanager options))
        (println "Done.")))))
