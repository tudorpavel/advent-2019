(ns advent)

(defn digits [n]
  (if (pos? n)
    (conj (digits (quot n 10)) (mod n 10))
    []))

(defn same-adjacent? [digs]
  (let [[h & t] digs]
    (cond
      (empty? t) false
      (= h (first t)) true
      :else (recur t))))

(defn valid-password? [n]
  (let [digs (digits n)]
    (and (= (sort digs) digs)
         (same-adjacent? digs))))

(defn solve1 [input]
  (let [[start end] input]
    (count (filter valid-password? (range start (inc end))))))

(defn solve2 [input]
  "Soon.")

(defn read-input []
  (map read-string
       (clojure.string/split (first (clojure.string/split-lines (slurp *in*)))
                             #"-")))

(defn -main []
  (let [input (read-input)]
    (println "Part 1:" (solve1 input))
    (println "Part 2:" (solve2 input))))
