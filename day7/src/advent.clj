(ns advent
  (:require [clojure.math.combinatorics :as combo]
            [intcode-computer :as ic]))

(defn thruster-signal [memory settings]
  (let [amps (map #(atom (ic/init-program memory [%])) settings)]
    (reduce (fn [prev-amp cur-amp]
              (let [program (ic/exec-program
                             (update @cur-amp :inputs conj (:output prev-amp)))]
                (if (= :EXIT (:output program))
                  (reduced (first (:inputs program)))
                  (swap! cur-amp (constantly program)))))
            {:output 0}
            (cycle amps))))

(defn max-thruster-signal [memory settings]
  (apply max (map (partial thruster-signal memory)
                  (combo/permutations settings))))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [memory (ic/str->memory (read-input))]
    (println "Part 1:" (max-thruster-signal memory (range 5)))
    (println "Part 2:" (max-thruster-signal memory (range 5 10)))))
