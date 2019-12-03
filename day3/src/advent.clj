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

(defn draw-segment [start end update-op hash]
  (defn coord-range [a b]
    (if (< a b)
      (range (inc a) (inc b))
      (range b a)))
  (defn update-point [h p]
    (let [s (point->str p)]
      (assoc h s (update-op (h s 0)))))
  (cond
    (= (:x start) (:x end)) (reduce update-point hash
                                    (map (fn [y] {:x (:x start), :y y})
                                         (coord-range (:y start) (:y end))))
    (= (:y start) (:y end)) (reduce update-point hash
                                    (map (fn [x] {:x x, :y (:y start)})
                                         (coord-range (:x start) (:x end))))
    :else (throw (AssertionError. "Given segment is diagonal?"))))

(defn trace [wire current update-op hash]
  (let [step (first wire)
        next (next-point current step)]
    (if (empty? (rest wire))
      (draw-segment current next update-op hash)
      (recur (rest wire) next update-op (draw-segment current next update-op hash)))
    ))

(defn manhattan-distance-from-origin [hash]
  (defn actual-distance [p]
    (+ (Math/abs (:x p))
       (Math/abs (:y p))))
  (let [intersection-points (filter #(= (second %) 16) hash)]
    ;; intersection-points is a list of the form (["6,5" 2] ["3,3" 4])
    (apply min (map (comp actual-distance str->point first) intersection-points))
    ))

(defn solve1 [input]
  (let [[wire1, wire2] input]
    ;; desperate solution: self intersections will be 6 for wire1 and 26 for wire2.
    ;; the intersections we care about will have value exactly 16
    (manhattan-distance-from-origin (trace wire2
                                           {:x 0, :y 0}
                                           #(+ % 13)
                                           (trace wire1
                                                  {:x 0, :y 0}
                                                  #(+ % 3)
                                                  {})))
    ))

(defn solve2 [input]
  "Soon.")

(defn read-input []
  (map #(clojure.string/split % #",") (clojure.string/split-lines (slurp *in*))))

(defn -main []
  (let [input (read-input)]
    (println "Part 1:" (solve1 input))
    (println "Part 2:" (solve2 input))))
