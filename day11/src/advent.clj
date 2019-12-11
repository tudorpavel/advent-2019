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

(defn run-robot [memory]
  (exec-robot (ic/init-program memory [])
              {:hull {}
               :pos [0 0]
               :dir :N}))

(defn -main []
  (defn read-input []
    (first (clojure.string/split-lines (slurp *in*))))

  (let [memory (ic/str->memory (read-input))]
    (println "Part 1:" (count (run-robot memory)))
    (println "Part 2:" "Soon.")))
