(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest run-robot-test
  (is (= {'(0 0) 0
          '(-1 0) 0
          '(-1 1) 1
          '(0 1) 1
          '(1 0) 1
          '(1 -1) 1}
         (run-robot [3 100 104 1 104 0
                     3 100 104 0 104 0
                     3 100 104 1 104 0
                     3 100 104 1 104 0
                     3 100 104 0 104 1
                     3 100 104 1 104 0
                     3 100 104 1 104 0 99] 0))))
