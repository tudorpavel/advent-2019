(ns intcode-computer-test
  (:require [intcode-computer :refer :all]
            [clojure.test :refer :all]))

;; delete and make private after TDD
(deftest tdd
  ;; (is (= '(99) (parse-op 99)))
  ;; (is (= '(2 0 1) (parse-op 1002)))
  ;; (is (= '(3 1 1 0 1) (parse-op 101103)))

  ;; (is (= '(33 3) (operands 0 '(0 1) [1002 4 3 4 33])))

  ;; (is (= '(2 33 3) (instruction 0 [1002 4 3 4 33])))
  ;; (is (= '(99) (instruction 0 [99])))
  ;; (is (= '(2 33 4) (instruction 0 [2 4 3 4 33])))
  )

(deftest make-program-test
  (is (= [1 0 0 0 99] (make-program "1,0,0,0,99")))
  (is (= [1 1 1 4 99 5 6 0 99] (make-program "1,1,1,4,99,5,6,0,99")))
  )

(deftest run-program-test
  ;; Day2
  (is (= {:outputs [], :memory [2 0 0 0 99]}
         (run-program [1 0 0 0 99] [])))
  (is (= {:outputs [], :memory [2 3 0 6 99]}
         (run-program [2 3 0 3 99])))
  (is (= {:outputs [], :memory [2 4 4 5 99 9801]}
         (run-program [2 4 4 5 99 0])))
  (is (= {:outputs [], :memory [30 1 1 4 2 5 6 0 99]}
         (run-program [1 1 1 4 99 5 6 0 99])))
  ;; Day5 - Part1
  (is (= {:outputs [], :memory [1002 4 3 4 99]}
         (run-program [1002 4 3 4 33])))
  (is (= {:outputs [], :memory [3 6 1002 6 3 6 99]}
         (run-program [3 6 1002 6 3 6 0] [33])))
  (is (= {:outputs [99], :memory [3 8 1002 8 3 8 4 8 99]}
         (run-program [3 8 1002 8 3 8 4 8 0] [33])))

  ;; Day5 - Part2
  (is (= [1] (:outputs (run-program [3 9 8 9 10 9 4 9 99 -1 8] [8]))))
  (is (= [0] (:outputs (run-program [3 9 8 9 10 9 4 9 99 -1 8] [2]))))
  (is (= [0] (:outputs (run-program [3 9 7 9 10 9 4 9 99 -1 8] [8]))))
  (is (= [1] (:outputs (run-program [3 9 7 9 10 9 4 9 99 -1 8] [2]))))
  (is (= [1] (:outputs (run-program [3 3 1108 -1 8 3 4 3 99] [8]))))
  (is (= [0] (:outputs (run-program [3 3 1108 -1 8 3 4 3 99] [2]))))
  (is (= [0] (:outputs (run-program [3 3 1107 -1 8 3 4 3 99] [8]))))
  (is (= [1] (:outputs (run-program [3 3 1107 -1 8 3 4 3 99] [2]))))
  (is (= [0] (:outputs
              (run-program [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9]
                           [0]))))
  (is (= [1] (:outputs
              (run-program [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9]
                           [2]))))
  (is (= [0] (:outputs
              (run-program [3 3 1105 -1 9 1101 0 0 12 4 12 99 1]
                           [0]))))
  (is (= [1] (:outputs
              (run-program [3 3 1105 -1 9 1101 0 0 12 4 12 99 1]
                           [2]))))
  (is (= [999] (:outputs
                (run-program [3 21 1008 21 8 20 1005 20 22 107 8 21
                              20 1006 20 31 1106 0 36 98 0 0 1002 21
                              125 20 4 20 1105 1 46 104 999 1105 1 46
                              1101 1000 1 20 4 20 1105 1 46 98 99]
                             [5]))))
  (is (= [1000] (:outputs
                 (run-program [3 21 1008 21 8 20 1005 20 22 107 8 21
                               20 1006 20 31 1106 0 36 98 0 0 1002 21
                               125 20 4 20 1105 1 46 104 999 1105 1 46
                               1101 1000 1 20 4 20 1105 1 46 98 99]
                              [8]))))
  )
