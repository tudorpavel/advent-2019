(ns intcode-computer)

(defn- read-memory [memory pos]
  (if (neg? pos)
    (throw (AssertionError. "Tried to read-memory from negative position."))
    (get memory pos 0)))

(defn- write-memory [memory pos val]
  (if (neg? pos)
    (throw (AssertionError. "Tried to write-memory at negative position."))
    (if (> pos (dec (count memory)))
      (assoc (into [] (concat memory (repeat (- pos (count memory)) 0)))
             pos val)
      (assoc memory pos val))))

(defn opcode [program]
  ((comp int bigint)
   (apply str (take-last 2 (str ((:memory program) (:pos program)))))))

(defn param-modes [op param-count]
  (let [[_ _ & rest] (reverse (str op))]
    (map #((comp int bigint str) (nth rest % 0)) (range param-count))))

(defn operand-address [program pos mode]
  (let [{mem :memory} program
        {rel :rel} program]
    (case mode
      2 (+ rel (read-memory mem pos))
      1 pos
      (read-memory mem pos))))

(defn addresses [program param-count]
  (let [{mem :memory} program
        {pos :pos} program]
    (map-indexed #(operand-address program (+ pos (inc %1)) %2)
                 (param-modes (read-memory mem pos) param-count))))

(defn binomial-op [program func]
  (let [{mem :memory} program
        [a1 a2 a3] (addresses program 3)]
    {:pos (+ (:pos program) 4)
     :memory (write-memory mem a3 (func (read-memory mem a1)
                                        (read-memory mem a2)))}))

(defmulti instruction opcode)

(defmethod instruction 1 [program]
  "ADD"
  (binomial-op program +))

(defmethod instruction 2 [program]
  "MUL"
  (binomial-op program *))

(defmethod instruction 3 [program]
  "INPUT"
  (let [{[input & inputs] :inputs} program
        [addr] (addresses program 1)]
    {:pos (+ (:pos program) 2)
     :inputs inputs
     :memory (write-memory (:memory program) addr input)}))

(defmethod instruction 4 [program]
  "OUTPUT"
  (let [[addr] (addresses program 1)]
    {:pos (+ (:pos program) 2)
     :output (read-memory (:memory program) addr)}))

(defmethod instruction 5 [program]
  "JMP-TRUE"
  (let [{mem :memory} program
        [a1 a2] (addresses program 2)]
    {:pos (if (zero? (read-memory mem a1))
            (+ (:pos program) 3)
            (read-memory mem a2))}))

(defmethod instruction 6 [program]
  "JMP-FALSE"
  (let [{mem :memory} program
        [a1 a2] (addresses program 2)]
    {:pos (if (zero? (read-memory mem a1))
            (read-memory mem a2)
            (+ (:pos program) 3))}))

(defmethod instruction 7 [program]
  "LESS-THAN"
  (binomial-op program #(if (< %1 %2) 1 0)))

(defmethod instruction 8 [program]
  "EQUAL"
  (binomial-op program #(if (= %1 %2) 1 0)))

(defmethod instruction 9 [program]
  "ADJUST-REL"
  (let [[addr] (addresses program 1)]
    {:pos (+ (:pos program) 2)
     :rel (+ (:rel program)
             (read-memory (:memory program) addr))}))

(defmethod instruction 99 [_]
  "EXIT"
  {:output :EXIT})

(defmethod instruction :default [program]
  "ERROR"
  (throw (IllegalArgumentException.
          (str "Unexpected instruction code '" (opcode program) "'."))))

(defn init-program [memory inputs]
  {:pos 0 :rel 0 :memory memory
   :inputs inputs :output nil})

(defn exec-program [program]
  (let [result (instruction program)]
    (if (:output result)
      (merge program result)
      (recur (merge program result)))))

(defn str->memory [mem-str]
  (mapv read-string (.split mem-str ",")))

(defn run-program
  ([memory] (run-program memory []))
  ([memory inputs] (exec-program (init-program memory inputs))))

(defn collect-outputs
  ([memory] (collect-outputs memory []))
  ([memory inputs]
   (defn collect-outputs-iter [program outputs]
     (if (= :EXIT (:output program))
       outputs
       (recur (exec-program program)
              (conj outputs (:output program)))))

   (collect-outputs-iter (run-program memory inputs) [])))
