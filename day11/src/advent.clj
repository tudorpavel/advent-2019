(ns advent
  (:require [intcode-computer :as ic]))

;; assumes X coord increases towards :E
;;     and Y coord increases towards :S
(def next-map {[:N 0] {:dir :W :pos [-1 0]}
               [:N 1] {:dir :E :pos [1  0]}
               [:E 0] {:dir :N :pos [0 -1]}
               [:E 1] {:dir :S :pos [0  1]}
               [:S 0] {:dir :E :pos [1  0]}
               [:S 1] {:dir :W :pos [-1 0]}
               [:W 0] {:dir :S :pos [0  1]}
               [:W 1] {:dir :N :pos [0 -1]}
               })

(defn exec-robot [program state]
  (let [color (get (:hull state) (:pos state) 0)
        program (ic/exec-program (assoc program :inputs [color]))]
    (if (= :EXIT (:output program))
      (:hull state)
      (let [paint (:output program)
            program (ic/exec-program program)
            next (next-map [(:dir state) (:output program)])
            next (update next :pos #(map + (:pos state) %))]
        (recur program
               (merge (update state :hull merge {(:pos state) paint})
                      next))))))

(defn run-robot [memory start-panel-color]
  (exec-robot (ic/init-program memory [])
              {:hull {[0 0] start-panel-color}
               :pos [0 0]
               :dir :N}))

(defn pretty-print [hull]
  (let [white-panels (remove #(zero? (second %)) hull)
        min-x (apply min (map first (keys hull)))
        max-x (apply max (map first (keys hull)))
        min-y (apply min (map second (keys hull)))
        max-y (apply max (map second (keys hull)))
        xs (range min-x (inc max-x))
        ys (range min-y (inc max-y))]
    (doall (map println
                (partition (count xs)
                           (map #(if (zero? %) " " "⬛")
                                (for [y ys x xs]
                                  (get hull [x y] 0))))))))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [memory (ic/str->memory (read-input))]
    (println "Part 1:" (count (run-robot memory 0)))
    (println "Part 2:")
    (pretty-print (run-robot memory 1))))
