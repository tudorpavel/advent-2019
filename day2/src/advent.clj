(ns advent
  (:require [intcode-computer :as ic]))

(defn run-program [noun verb program]
  (let [{[output & _] :memory}
        (ic/run-program (assoc program 1 noun 2 verb))]
    output))

(defn solve1 [program]
  (run-program 12 2 program))

(defn solve2 [program]
  (first
   (for [noun (range 100)
         verb (range 100)
         :let [output (run-program noun verb program)]
         :when (= output 19690720)]
     (+ (* 100 noun) verb))))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [program (ic/make-program (read-input))]
    (println "Part 1:" (solve1 program))
    (println "Part 2:" (solve2 program))))
