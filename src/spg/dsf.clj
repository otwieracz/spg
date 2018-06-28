(ns spg.dsf
  "Very basic DSF atom parsing."
  (:require [clojure.java.io :as io]
            [clj-struct.core :as clj-struct]
            [clojure.string :as string]))

;; https://developer.x-plane.com/?article=dsf-file-format-specification

(def +string-atom-separator+ (char 0))

(def +atoms+ {:terrain-types "TERT"
              :objects "OBJT"
              :polygons "POLT"
              :vector-network "NETW"
              :raster-definition "DEMN"})

(def +sizes+ {:atom-id (/ 32 8) ;; 32bit atom ID
              :atom-count (/ 32 8) ;; 32 bit atom count
              })

(defn- string-to-little-endian [x] (string/reverse x))

(defn- split-string-atom
  "Split string atom `STRING` on `+string-atom-separator+`"
  [string]
  (map #(apply str %)
       (remove #(= (first %) +string-atom-separator+)
               (partition-by #(= % +string-atom-separator+) (seq string)))))

(defn- find-atom-positions
  "Find positions of every atom from `+ATOMS+`, parse it and find its beginning and end. Return map."
  [input]
  (into {}
        (map (fn [[atom atom-identifier]]
               (if-let [start-position (string/index-of input (string-to-little-endian atom-identifier))]
                 (let [end-marker-bytes (.getBytes (subs input
                                                         (+ start-position (:atom-id +sizes+))
                                                         (+ start-position (:atom-id +sizes+) (:atom-count +sizes+)))
                                                   "ascii")]
                   (when start-position
                     [atom
                      {:start (+ start-position
                                 (:atom-id +sizes+)
                                 (:atom-count +sizes+))
                       ;; Each atom consists of a 32-bit atom ID followed by a
                       ;; 32-bit unsigned byte count for the size of the atom,
                       ;; including this 8-byte header.
                       :end (+ start-position
                               (first (clj-struct/unpack "<L" end-marker-bytes)))}]))))
             +atoms+)))

(defn get-atoms
  "Extract atoms from DSF file `DSF-FILE`"
  [dsf-file]
  (let [input (slurp (io/file dsf-file))]
    (into {}
          (map (fn [[atom {:keys [start end]}]]
                 [atom (split-string-atom (subs input start end))])
               (find-atom-positions input)))))
