(ns advent
  (:require [intcode-computer :as ic]))

(defn boost-keycode [mem]
  (:output (ic/run-program mem [1])))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [memory (ic/str->memory (read-input))]
    (println "Part 1:" (boost-keycode memory))
    (println "Part 2:" "Soon.")))
