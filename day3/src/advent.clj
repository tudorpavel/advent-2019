(ns advent)

(defn next-point [point step]
  (let [dir (first step)
        delta (read-string (subs step 1))]
    (case dir
      \U (update point :y #(+ % delta))
      \R (update point :x #(+ % delta))
      \D (update point :y #(- % delta))
      \L (update point :x #(- % delta))
      (throw (AssertionError. "Unexpected direction char."))
      )))

(defn point->str [point]
  (str (:x point) "," (:y point)))

(defn str->point [str]
  (let [[x y] (map read-string (clojure.string/split str #","))]
    {:x x, :y y}))

(defn draw-segment [start end wire-id hash]
  (defn coord-range [a b]
    (if (< a b)
      (range (inc a) (inc b))
      (reverse (range b a))))
  (defn update-point [acc p]
    (let [[step-count h] acc
          s (point->str p)
          new-step-count (inc step-count)]
      (if (get-in h [s wire-id])
        [new-step-count h]
        [new-step-count (assoc h s (conj (h s {}) {wire-id new-step-count}))]
        )
      ))
  (cond
    (= (:x start) (:x end)) (second
                             (reduce update-point
                                     [(get-in hash [(point->str start) wire-id] 0) hash]
                                     (map (fn [y] {:x (:x start), :y y})
                                          (coord-range (:y start) (:y end)))))
    (= (:y start) (:y end)) (second
                             (reduce update-point
                                     [(get-in hash [(point->str start) wire-id] 0) hash]
                                     (map (fn [x] {:x x, :y (:y start)})
                                          (coord-range (:x start) (:x end)))))
    :else (throw (AssertionError. "Given segment is diagonal?"))))

(defn trace [wire current wire-id hash]
  (let [step (first wire)
        next (next-point current step)]
    (if (empty? (rest wire))
      (draw-segment current next wire-id hash)
      (recur (rest wire) next wire-id (draw-segment current next wire-id hash)))
    ))

(defn intersection-points [hash]
  "intersection-points returns a list of the form
  (["6,5" {:wire1 15, :wire2 15}] ["3,3" {:wire1 20, :wire2 20}])"

  (filter (fn [h] (every? #(contains? (second h) %) [:wire1 :wire2]))
          hash))

(defn manhattan-distance-from-origin [hash]
  (defn actual-distance [p]
    (+ (Math/abs (:x p))
       (Math/abs (:y p))))

  (apply min (map (comp actual-distance str->point first) (intersection-points hash)))
  )

(defn trace-wires [wire1 wire2]
  (trace wire2 {:x 0, :y 0} :wire2
         (trace wire1 {:x 0, :y 0} :wire1 {})))

(defn solve1 [input]
  (let [[wire1, wire2] input]
    (manhattan-distance-from-origin (trace-wires wire1 wire2))))

(defn solve2 [input]
  (defn total-steps [intersection-point]
    (let [h (second intersection-point)]
      (+ (:wire1 h) (:wire2 h))))

  (let [[wire1, wire2] input]
    (apply min (map total-steps (intersection-points (trace-wires wire1 wire2))))))

(defn read-input []
  (map #(clojure.string/split % #",") (clojure.string/split-lines (slurp *in*))))

(defn -main []
  (let [input (read-input)]
    (println "Part 1:" (solve1 input))
    (println "Part 2:" (solve2 input))))
