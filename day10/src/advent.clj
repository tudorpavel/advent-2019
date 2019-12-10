(ns advent
  (:require [clojure.math.combinatorics :as combo]))

(defn map-row [row-string y]
  (map #(conj [%] y) (keep-indexed #(when (= %2 \#) %1) row-string)))

(defn make-points [row-strings]
  (reduce concat (map-indexed #(map-row %2 %1) row-strings)))

(defn line-equation [[x1 y1] [x2 y2]]
  "defined by pair [m b] from equation y=mx+b
   but with special case for x1=x2 which should check x=b"
  (cond
    (= y1 y2) [0 y1]
    (= x1 x2) [nil x1]
    :else (let [slope (/ (- y2 y1) (- x2 x1))]
            [slope (- y1 (* slope x1))])))

(defn point-on-line? [[x y] [m b]]
  "checks equation y=mx+b but when m=nil
   the case is special and checks x=b"
  (if (nil? m)
    (= x b)
    (= y (+ (* m x) b))))

(defn segments->point-set [segments]
  (reduce #(apply conj %1 %2) (sorted-set) segments))

(defn coliniar-sets [points]
  (->> (combo/combinations points 2)
       (group-by #(apply line-equation %))
       (map (fn [[_ v]] (segments->point-set v)))))

(defn count-neighbors [p set]
  (cond
    (not (contains? set p)) 0
    (= p (first set)) 1
    (= p (last set)) 1
    :else 2))

(defn count-visible-asteroids [p coliniar-sets]
  (->> coliniar-sets
       (map #(count-neighbors p %))
       (reduce +)))

(defn best-monitoring-asteroid [row-strings]
  (let [points (make-points row-strings)
        coliniar-sets (coliniar-sets points)]
    (reduce
     (fn [max-visible p]
       (let [count (count-visible-asteroids p coliniar-sets)]
         (if (> count (second max-visible))
           [p count]
           max-visible)))
     [(first points) (count-visible-asteroids (first points) coliniar-sets)]
     (rest points))))

(defn -main []
  (defn read-input []
    (clojure.string/split-lines (slurp *in*)))

  (let [input (read-input)]
    (println "Part 1:" (second (best-monitoring-asteroid input)))
    (println "Part 2:" "Soon.")))
