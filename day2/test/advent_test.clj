(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest run-program-test
  (is (= 2 (run-program [1 0 0 0 99] 0 0)))
  (is (= 2 (run-program [2 3 0 3 99] 3 0)))
  (is (= 2 (run-program [2 4 4 5 99 0] 4 4)))
  (is (= 30 (run-program [1 1 1 4 99 5 6 0 99] 1 1)))
  )
