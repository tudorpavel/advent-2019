(ns advent
  (:require [clojure.math.combinatorics :as combo]
            [intcode-computer :as ic]))

(defn thruster-signal [program phase-settings]
  (reduce (fn [prev-output, phase-setting]
            (first (:outputs
                    (ic/run-program program [phase-setting prev-output]))))
          0
          phase-settings))

(defn max-thruster-signal [program]
  (apply max (map (partial thruster-signal program)
                  (combo/permutations (range 5)))))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [program (ic/make-program (read-input))]
    (println "Part 1:" (max-thruster-signal program))
    (println "Part 2:" "Soon.")))
