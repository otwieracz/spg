(ns spg.utils
  (:require [clojure.string :as string]))

(defn windows?
  []
  (string/includes? (string/lower-case (.. System getProperties (get "os.name")))
                    "windows"))

(defn newl
  "Handle Windows `newline` \\n vs \\r\\n"
  []
  (if (windows?)
    "\r\n"
    "\n"))
