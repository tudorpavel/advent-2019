(ns intcode-computer)

(defn- parse-op [op]
  (let [[a b & rest] (reverse (str op))]
    (map (comp int bigint) (conj (map str rest) (str b a)))))

(defn- compute-result
  ([program inputs] (compute-result 0 inputs {:outputs [] :memory program}))
  ([pos inputs result]
   (let [{memory :memory} result
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

     (defn new-result [func]
       (assoc result
              :memory
              (new-memory func)))

     (case code
       1 (recur (+ pos 4) inputs (new-result +))
       2 (recur (+ pos 4) inputs (new-result *))
       3 (let [[input & inputs] inputs]
           (recur (+ pos 2)
                  inputs
                  (assoc result :memory
                         (assoc memory
                                (memory (inc pos))
                                input))))
       4 (let [operand (operand (first param-modes) (inc pos))]
           (recur (+ pos 2)
                  inputs
                  (update result
                          :outputs
                          #(conj % operand))) )
       5 (let [operands (operands)]
           (recur (if (zero? (first operands))
                    (+ pos 3)
                    (second operands))
                  inputs
                  result))
       6 (let [operands (operands)]
           (recur (if (zero? (first operands))
                    (second operands)
                    (+ pos 3))
                  inputs
                  result))
       7 (recur (+ pos 4) inputs (new-result #(if (< %1 %2) 1 0)))
       8 (recur (+ pos 4) inputs (new-result #(if (= %1 %2) 1 0)))
       99 result
       (throw (AssertionError. "Unexpected instruction code."))))))

(defn make-program [program-str]
  (mapv read-string (.split program-str ",")))

(defn run-program
  ([program] (run-program program []))
  ([program inputs] (compute-result program inputs)))
