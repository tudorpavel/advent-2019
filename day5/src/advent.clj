(ns advent
  (:require [intcode-computer :as ic]))

(defn solve1 [program]
  (:outputs (ic/run-program program [1])))

(defn solve2 [program]
  "Soon.")

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [program (ic/make-program (read-input))]
    (println "Part 1:" (solve1 program))
    (println "Part 2:" (solve2 program))))
