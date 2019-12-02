(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest operand-vals-test
  (is (= '(3 2) (operand-vals 0 [2 3 0 3 99])))
  )

(deftest run-program-test
  (is (= [2 0 0 0 99] (run-program 0 [1 0 0 0 99])))
  (is (= [2 3 0 6 99] (run-program 0 [2 3 0 3 99])))
  (is (= [2 4 4 5 99 9801] (run-program 0 [2 4 4 5 99 0])))
  (is (= [30 1 1 4 2 5 6 0 99] (run-program 0 [1 1 1 4 99 5 6 0 99])))
  )
