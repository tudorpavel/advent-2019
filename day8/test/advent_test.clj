(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest find-min-zeros-test
  (is (= {0 2 1 4} (find-min-zeros '({0 2 1 4} {0 3 5 23}))))
  (is (= {0 2 1 4} (find-min-zeros '({0 3 5 23} {0 2 1 4}))))
  )
