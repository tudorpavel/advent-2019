(ns intcode-computer)

(defn- parse-op [op]
  (let [[a b & rest] (reverse (str op))]
    (map (comp int bigint) (conj (map str rest) (str b a)))))

(defn init-program [memory inputs]
  {:pos 0 :memory memory
   :inputs inputs :output nil})

(defn exec-program
  ([program]
   (let [{pos :pos} program
         {memory :memory} program
         [code & param-modes] (parse-op (memory pos))]
     (defn operand [mode p]
       (memory (if (= mode 1) p (memory p))))

     (defn operands []
       (list (operand (first param-modes) (+ pos 1))
             (operand (second param-modes) (+ pos 2))))

     (defn new-memory [func]
       (assoc memory
              (memory (+ pos 3))
              (apply func (operands))))

     (defn new-program [func]
       (assoc program
              :pos (+ pos 4)
              :memory (new-memory func)))

     (case code
       1 (recur (new-program +))
       2 (recur (new-program *))
       3 (let [[input & inputs] (:inputs program)]
           (recur (assoc program
                         :pos (+ pos 2)
                         :inputs inputs
                         :memory (assoc memory
                                        (memory (inc pos))
                                        input))))
       4 (let [operand (operand (first param-modes) (inc pos))]
           (assoc program :output operand :pos (+ pos 2)))
       5 (let [operands (operands)]
           (recur (assoc program
                         :pos
                         (if (zero? (first operands))
                           (+ pos 3)
                           (second operands)))))
       6 (let [operands (operands)]
           (recur (assoc program
                         :pos
                         (if (zero? (first operands))
                           (second operands)
                           (+ pos 3)))))
       7 (recur (new-program #(if (< %1 %2) 1 0)))
       8 (recur (new-program #(if (= %1 %2) 1 0)))
       99 (assoc program :output :EXIT)
       (throw (AssertionError. "Unexpected instruction code."))))))

(defn make-program [program-str]
  (mapv read-string (.split program-str ",")))

(defn run-program
  ([memory] (run-program memory []))
  ([memory inputs] (exec-program (init-program memory inputs))))
