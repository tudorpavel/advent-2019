(ns advent)

(defn fuel-required [mass]
  (- (quot mass 3) 2))

(defn solve1 [lines]
  (reduce + (map (comp fuel-required read-string) lines)))

(defn solve2 [lines]
  "Soon..")

(defn read-input []
  (clojure.string/split-lines (slurp *in*)))

(defn -main []
  (let [input (read-input)]
    (println "Part 1:" (solve1 input))
    (println "Part 2:" (solve2 input))))
