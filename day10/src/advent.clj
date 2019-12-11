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

(defn distance [[x1 y1] [x2 y2]]
  (Math/sqrt (+ (Math/pow (- x1 x2) 2)
                (Math/pow (- y1 y2) 2))))

(defn segments->point-set [segments]
  (reduce #(apply conj %1 %2) (sorted-set) segments))

(defn coliniar-sets [points]
  (->> (combo/combinations points 2)
       (group-by #(apply line-equation %))
       (map (fn [[_ v]] (segments->point-set v)))))

(defn half-sorted-targets [center points]
  (->> points
       (group-by #(line-equation center %))
       ;; Sort keys by line slope and values by distance from center
       (map (fn [[k v]] [(first k) (sort-by #(distance center %) v)]))
       (sort)
       (map second)))

(defn sorted-targets [center row-strings]
  (let [points (remove #(= center %)
                       (make-points row-strings))
        {left true right false} (group-by
                                 #(or (< (first %) (first center))
                                      (and (= (first center) (first %))
                                           (< (second center) (second %))))
                                 points)]
    (concat
     (half-sorted-targets center right)
     (half-sorted-targets center left))))

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

(defn kill-order [center row-strings]
  (let [targets (map atom (sorted-targets center row-strings))]
       (reduce
        (fn [acc asteroids]
          (if (> (:useless-steps acc) (count targets))
            (reduced (:points acc))
            (if (empty? @asteroids)
              (update acc :useless-steps inc)
              (let [[h & t] @asteroids]
                (swap! asteroids (constantly t))
                (assoc (update acc :points conj h)
                       :useless-steps 0)))))
        {:points []
         :useless-steps 0}
        (cycle targets))))

(defn nth-kill [center row-strings n]
  ((kill-order center row-strings) (dec n)))

(defn -main []
  (defn read-input []
    (clojure.string/split-lines (slurp *in*)))

  (let [input (read-input)
        [location detected-count] (best-monitoring-asteroid input)]
    (println "Part 1:" detected-count)
    (println "Part 2:" (let [[x y] (nth-kill location input 200)]
                         (+ (* x 100) y)))))
