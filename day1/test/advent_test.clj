(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest fuel-required-test
  (is (= 2 (fuel-required 12)))
  (is (= 2 (fuel-required 14)))
  (is (= 654 (fuel-required 1969)))
  (is (= 33583 (fuel-required 100756)))
  )
