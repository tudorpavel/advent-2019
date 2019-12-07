(ns advent
  (:require [intcode-computer :as ic]))

(defn first-non-zero-output [memory input]
  (defn first-non-zero-output-iter [program]
    (if (zero? (:output program))
      (recur (ic/exec-program program))
      (:output program)))

  (first-non-zero-output-iter (ic/run-program memory [input])))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [memory (ic/str->memory (read-input))]
    (println "Part 1:" (first-non-zero-output memory 1))
    (println "Part 2:" (first-non-zero-output memory 5))))
