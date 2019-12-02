(ns advent)

(defn operand-vals [pos program]
  (list (program (program (+ pos 1)))
        (program (program (+ pos 2)))))

(defn apply-op [op pos program]
  (assoc program (program (+ pos 3))
         (apply op (operand-vals pos program))))

(defn run-program [pos program]
  (case (program pos)
    1 (recur (+ pos 4) (apply-op + pos program))
    2 (recur (+ pos 4) (apply-op * pos program))
    99 program
    (throw (AssertionError. "Unexpected instruction code."))))

(defn run-program-with-inputs [noun verb program]
  (first (run-program 0 (assoc (assoc program 2 verb) 1 noun))))

(defn solve1 [input]
  (run-program-with-inputs 12 2 input))

(defn solve2 [input]
  (first
   (for [noun (range 100)
         verb (range 100)
         :let [output (run-program-with-inputs noun verb input)]
         :when (= output 19690720)]
     (+ (* 100 noun) verb))))

(defn read-input []
  (vec (map read-string
            (clojure.string/split (first (clojure.string/split-lines (slurp *in*)))
                                  #","))))

(defn -main []
  (let [input (read-input)]
    (println "Part 1:" (solve1 input))
    (println "Part 2:" (solve2 input))))
