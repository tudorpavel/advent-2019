(ns advent)

(defn digits [n]
  (if (pos? n)
    (conj (digits (quot n 10)) (mod n 10))
    []))

(defn count-digits [digs]
  (reduce #(update %1 %2 inc)
          (vec (repeat 10 0))
          digs))

(defn valid-with-rule? [n digit-count-pred]
  (let [digs (digits n)]
    (and (= (sort digs) digs)
         (pos? (count (filter digit-count-pred
                              (count-digits digs)))))))

(defn loosely-valid? [n]
  (valid-with-rule? n (partial < 1)))

(defn strictly-valid? [n]
  (valid-with-rule? n (partial = 2)))

(defn password-count-with-rule [input rule-pred]
  (let [[start end] input]
    (count (filter rule-pred (range start (inc end))))))

(defn solve1 [input]
  (password-count-with-rule input loosely-valid?))

(defn solve2 [input]
  (password-count-with-rule input strictly-valid?))

(defn read-input []
  (map read-string
       (clojure.string/split (first (clojure.string/split-lines (slurp *in*)))
                             #"-")))

(defn -main []
  (let [input (read-input)]
    (println "Part 1:" (solve1 input))
    (println "Part 2:" (solve2 input))))
