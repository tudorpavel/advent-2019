(ns intcode-computer)

(defn- parse-op [op]
  (let [[a b & rest] (reverse (str op))]
    (map read-string (conj (map str rest) (str b a)))))

(defn- compute-result
  ([program inputs] (compute-result 0 inputs {:outputs [] :memory program}))
  ([pos inputs result]
   (let [{memory :memory} result
         [code & param-modes] (parse-op (memory pos))]
     (defn operands []
       (defn operand [mode p]
         (memory (if (= mode 1) p (memory p))))

       (list (operand (first param-modes) (+ pos 1))
             (operand (second param-modes) (+ pos 2))))

     (defn new-memory [arith-op]
       (assoc memory
              (memory (+ pos 3))
              (apply arith-op (operands))))

     (defn new-result [arith-op]
       (assoc result
              :memory
              (new-memory arith-op)))

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
       4 (recur (+ pos 2)
                inputs
                (update result
                        :outputs
                        #(conj % (memory (memory (inc pos))))))
       99 result
       (throw (AssertionError. "Unexpected instruction code."))))))

(defn make-program [program-str]
  (mapv read-string (.split program-str ",")))

(defn run-program
  ([program] (run-program program []))
  ([program inputs] (compute-result program inputs)))
