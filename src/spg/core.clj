(ns spg.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.tools.cli :refer [parse-opts]]
            [spg.utils :as utils]
            [spg.scenemanager :as sm]
            [spg.scenerypacks :as sp]
            [seesaw.core :as sw]))

(defn- create-progress-window
  "Show window with progress bar and return this progress bar for updates"
  []
  (let [progress-bar (sw/progress-bar :min 0
                                      :value 0
                                      :orientation :horizontal
                                      :paint-string? true)
        close-button (sw/button :text ""
                                :enabled? false
                                :size [15 :by 5])
        listbox (sw/listbox :model (list))
        top-panel (sw/left-right-split progress-bar close-button)
        bottom-panel (sw/top-bottom-split (sw/label :text "Active sceneries:") (sw/scrollable listbox))
        panel (sw/top-bottom-split top-panel bottom-panel)
        frame (sw/frame :title "spg – Scenery Pack Generator"
                        :content panel
                        :minimum-size [640 :by 480])]
    (sw/invoke-later
     (-> frame
         sw/pack!
         sw/show!))
    {:progress-bar progress-bar
     :listbox listbox
     :close-button close-button
     :frame frame }))

(defn- make-scenerypacks-ini
  "Generate new `scenery_packs.ini` from provided `SceneManager.ini` in `custom-scenery-dir`. Call `HOOK`` every update"
  [custom-scenery-dir scene-manager-ini hook]
  (let [dest-file (str custom-scenery-dir
                       (utils/fs-sep)
                       "scenery_packs.ini")
        {:keys [scenery-pack-lines sceneries]} (sp/make-scenerypacks-ini custom-scenery-dir
                                                                         (sm/scenery-packs scene-manager-ini)
                                                                         hook)]
    ;; write scenery-pack-lines
    (with-open [w (io/writer dest-file)]
      (.write w scenery-pack-lines))
    ;; return list of sceneries written
    sceneries))

(def +cli-options+
  [["-c" "--custom_scenery DIRECTORY" "Path to `Custom Scenery` directory"
    :id :custom-scenery
    :default (if (utils/windows?)
               "Custom Scenery\\"
               "./Custom Scenery/")]
   ["-m" "--scenemanager FILE" "Path to `SceneManager.ini`"
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
      (do
        (println (:summary parsed-opts))
        (sw/alert (:summary parsed-opts)))
      ;; or continue
      (let [{:keys [progress-bar listbox close-button frame]} (create-progress-window)
            ;; Hook to be called by `spg.scenerypacks/make-scenerypacks-ini` after each update
            update-progress-bar-hook (fn [all-sceneries current-scenery]
                                       ;; update progress bar
                                       (sw/config! progress-bar
                                                   :max (count all-sceneries)
                                                   :value (+ (sw/config progress-bar :value)
                                                             1))
                                       ;; push new scenery to listbox
                                       (.add (sw/config listbox :model)
                                             0
                                             current-scenery)
                                       ;; sleep 20ms to make things more debuggable
                                       ;; ok just UI to look fancier :)
                                       (java.lang.Thread/sleep 20))]
        (sw/config! close-button :text (str "Generating `" (str (:custom-scenery options)
                                                                (utils/fs-sep)
                                                                "scenery_packs.ini")
                                            "`..."))
        (make-scenerypacks-ini (:custom-scenery options)
                               (:scenemanager options)
                               update-progress-bar-hook)
        ;; enable close button
        (sw/config! close-button
                    :enabled? true
                    :text "Close"
                    :listen [:action (fn [e] (sw/dispose! frame))])))))

