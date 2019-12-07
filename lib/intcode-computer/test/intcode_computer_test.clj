(ns intcode-computer-test
  (:require [intcode-computer :refer :all]
            [clojure.test :refer :all]))

(deftest str->memory-test
  (is (= [1 0 0 0 99] (str->memory "1,0,0,0,99")))
  (is (= [1 1 1 4 99 5 6 0 99] (str->memory "1,1,1,4,99,5,6,0,99")))
  )

(deftest run-program-day2-test
  (is (= [2 0 0 0 99] (:memory (run-program [1 0 0 0 99] []))))
  (is (= [2 3 0 6 99] (:memory (run-program [2 3 0 3 99]))))
  (is (= [2 4 4 5 99 9801] (:memory (run-program [2 4 4 5 99 0]))))
  (is (= [30 1 1 4 2 5 6 0 99] (:memory (run-program [1 1 1 4 99 5 6 0 99]))))
  )

(deftest run-program-day5-part1-test
  (is (= [1002 4 3 4 99] (:memory (run-program [1002 4 3 4 33]))))
  (is (= [3 6 1002 6 3 6 99] (:memory (run-program [3 6 1002 6 3 6 0] [33]))))
  (let [prog (run-program [3 8 1002 8 3 8 4 8 0] [33])]
    (is (= 99 (:output prog)))
    (is (= [3 8 1002 8 3 8 4 8 99] (:memory prog))))
  )

(deftest run-program-day5-part2-test
  (is (= 1 (:output (run-program [3 9 8 9 10 9 4 9 99 -1 8] [8]))))
  (is (= 0 (:output (run-program [3 9 8 9 10 9 4 9 99 -1 8] [2]))))
  (is (= 0 (:output (run-program [3 9 7 9 10 9 4 9 99 -1 8] [8]))))
  (is (= 1 (:output (run-program [3 9 7 9 10 9 4 9 99 -1 8] [2]))))
  (is (= 1 (:output (run-program [3 3 1108 -1 8 3 4 3 99] [8]))))
  (is (= 0 (:output (run-program [3 3 1108 -1 8 3 4 3 99] [2]))))
  (is (= 0 (:output (run-program [3 3 1107 -1 8 3 4 3 99] [8]))))
  (is (= 1 (:output (run-program [3 3 1107 -1 8 3 4 3 99] [2]))))
  (is (= 0 (:output
              (run-program [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9]
                           [0]))))
  (is (= 1 (:output
              (run-program [3 12 6 12 15 1 13 14 13 4 13 99 -1 0 1 9]
                           [2]))))
  (is (= 0 (:output
              (run-program [3 3 1105 -1 9 1101 0 0 12 4 12 99 1]
                           [0]))))
  (is (= 1 (:output
              (run-program [3 3 1105 -1 9 1101 0 0 12 4 12 99 1]
                           [2]))))
  (is (= 999 (:output
                (run-program [3 21 1008 21 8 20 1005 20 22 107 8 21
                              20 1006 20 31 1106 0 36 98 0 0 1002 21
                              125 20 4 20 1105 1 46 104 999 1105 1 46
                              1101 1000 1 20 4 20 1105 1 46 98 99]
                             [5]))))
  (is (= 1000 (:output
                 (run-program [3 21 1008 21 8 20 1005 20 22 107 8 21
                               20 1006 20 31 1106 0 36 98 0 0 1002 21
                               125 20 4 20 1105 1 46 104 999 1105 1 46
                               1101 1000 1 20 4 20 1105 1 46 98 99]
                              [8]))))
  )
