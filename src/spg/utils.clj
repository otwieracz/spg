(ns spg.utils)

(defn windows?
  []
  (zero? (.indexOf
          (.. System getProperties (get "os.name"))
          "Windows")))

(defn newl
  "Handle Windows `newline` \\n vs \\r\\n"
  []
  (if (windows?)
    "\r\n"
    "\n"))
