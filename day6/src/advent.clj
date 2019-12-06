(ns advent
  (:require clojure.set))

(defn- symmetric-difference [s1 s2]
  (clojure.set/union (clojure.set/difference s1 s2)
                     (clojure.set/difference s2 s1)))

(defn insert-orbit [orbits parent-id child-id]
  (let [parent-id (keyword parent-id)
        child-id (keyword child-id)]
    (update orbits parent-id
            (fn [vals] (if vals (conj vals child-id) (list child-id))))))

(defn pairs->orbits [pairs]
  (reduce #(apply insert-orbit %1 %2)
          {}
          (map #(clojure.string/split % #"\)") pairs)))

(defn compute-total-depths [orbits]
  (defn compute-depths-iter [id depth]
    (if (nil? id)
      depth
      (reduce + depth (map #(compute-depths-iter % (inc depth)) (id orbits)))))

  (compute-depths-iter :COM 0))

(defn min-transfer-count [orbits orbit1 orbit2]
  (defn path-to [id target]
    (cond
      (nil? id) nil
      (= id target) (list id)
      :else (let [subpath (remove nil? (mapcat #(path-to % target) (id orbits)))]
              (if (empty? subpath) nil
                  (cons id subpath)))))

  (count (disj (symmetric-difference (set (path-to :COM orbit1))
                                     (set (path-to :COM orbit2)))
               orbit1 orbit2)))

(defn read-input []
  (clojure.string/split-lines (slurp *in*)))

(defn -main []
  (let [orbits (pairs->orbits (read-input))]
    (println "Part 1:" (compute-total-depths orbits))
    (println "Part 2:" (min-transfer-count orbits :YOU :SAN))))
