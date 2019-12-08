(ns advent)

(defn find-min-zeros [freqs]
  (defn min-zeros [min freq]
    (if (< (freq 0 0) (min 0 0)) freq min))

  (reduce min-zeros freqs))

(defn part1 [layers]
  (let [min (find-min-zeros (map frequencies layers))]
    (* (min 1) (min 2))))

(defn -main []
  (defn read-input []
    (map (comp read-string str)
         (first (clojure.string/split-lines (slurp *in*)))))

  (let [layers (partition (* 25 6) (read-input))]
    (println "Part 1:" (part1 layers))
    (println "Part 2:" "Soon.")))
