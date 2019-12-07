(ns advent
  (:require [intcode-computer :as ic]))

(defn run-program [memory noun verb]
  (let [{[output & _] :memory}
        (ic/run-program (assoc memory 1 noun 2 verb))]
    output))

(defn solve1 [memory]
  (run-program memory 12 2))

(defn solve2 [memory]
  (first
   (for [noun (range 100)
         verb (range 100)
         :let [output (run-program memory noun verb)]
         :when (= output 19690720)]
     (+ (* 100 noun) verb))))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [memory (ic/str->memory (read-input))]
    (println "Part 1:" (solve1 memory))
    (println "Part 2:" (solve2 memory))))
