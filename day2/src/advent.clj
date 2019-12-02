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

(defn solve1 [input]
  (first (run-program 0 (assoc (assoc input 2 2) 1 12))))

(defn solve2 [input]
  "Soon..")

(defn read-input []
  (vec (map read-string
            (clojure.string/split (first (clojure.string/split-lines (slurp *in*)))
                                  #","))))

(defn -main []
  (let [input (read-input)]
    (println "Part 1:" (solve1 input))
    (println "Part 2:" (solve2 input))))
