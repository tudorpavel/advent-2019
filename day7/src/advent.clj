(ns advent
  (:require [clojure.math.combinatorics :as combo]
            [intcode-computer :as ic]))

(defn thruster-signal [initial-memory phase-settings]
  (reduce (fn [prev-output, phase-setting]
            (:output
             (ic/run-program initial-memory
                             [phase-setting prev-output])))
          0
          phase-settings))

(defn thruster-signal-looped [initial-memory, phase-settings]
  (with-local-vars [ampA (ic/init-program initial-memory [(phase-settings 0)])
                    ampB (ic/init-program initial-memory [(phase-settings 1)])
                    ampC (ic/init-program initial-memory [(phase-settings 2)])
                    ampD (ic/init-program initial-memory [(phase-settings 3)])
                    ampE (ic/init-program initial-memory [(phase-settings 4)])]
    (var-set ampE (assoc @ampE :output 0))
    (while (not= :EXIT (:output @ampE))
      (do
        (var-set ampA (update @ampA :inputs conj (:output @ampE)))
        (var-set ampA (ic/exec-program @ampA))
        (var-set ampB (update @ampB :inputs conj (:output @ampA)))
        (var-set ampB (ic/exec-program @ampB))
        (var-set ampC (update @ampC :inputs conj (:output @ampB)))
        (var-set ampC (ic/exec-program @ampC))
        (var-set ampD (update @ampD :inputs conj (:output @ampC)))
        (var-set ampD (ic/exec-program @ampD))
        (var-set ampE (update @ampE :inputs conj (:output @ampD)))
        (var-set ampE (ic/exec-program @ampE))))

    (first (:inputs @ampA))))

(defn max-thruster-signal [program]
  (apply max (map (partial thruster-signal program)
                  (combo/permutations (range 5)))))

(defn max-thruster-signal-looped [program]
  (apply max (map (partial thruster-signal-looped program)
                  (combo/permutations (range 5 10)))))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [program (ic/make-program (read-input))]
    (println "Part 1:" (max-thruster-signal program))
    (println "Part 2:" (max-thruster-signal-looped program))))
