(ns advent)

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

(defn solve2 [orbits]
  "Soon.")

(defn read-input []
  (clojure.string/split-lines (slurp *in*)))

(defn -main []
  (let [orbits (pairs->orbits (read-input))]
    (println "Part 1:" (compute-total-depths orbits))
    (println "Part 2:" (solve2 orbits))))
