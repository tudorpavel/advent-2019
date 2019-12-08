(ns advent)

(defn find-min-zeros [freqs]
  (defn min-zeros [min freq]
    (if (< (freq 0 0) (min 0 0)) freq min))

  (reduce min-zeros freqs))

(defn part1 [layers]
  (let [min (find-min-zeros (map frequencies layers))]
    (* (min 1) (min 2))))

(defn decode-image [layers]
  (defn replace-pixel [img-px l-px]
    (if (= img-px 2) l-px img-px))

  (defn combine [img layer]
    (if (contains? (set img) 2)
      (map replace-pixel img layer)
      (reduced img)))

  (reduce combine layers))

(defn pretty-print [decoded-image width]
  (doall (map println
              (partition width (map #(if (= % 0) " " "⬛")
                                    decoded-image)))))

(defn -main []
  (defn read-input []
    (map (comp read-string str)
         (first (clojure.string/split-lines (slurp *in*)))))

  (let [width 25
        height 6
        layers (partition (* width height) (read-input))]
    (println "Part 1:" (part1 layers))
    (println "Part 2:")
    (pretty-print (decode-image layers) width)))
