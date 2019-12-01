(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest fuel-required-test
  (is (= 2 (fuel-required 12)))
  (is (= 2 (fuel-required 14)))
  (is (= 654 (fuel-required 1969)))
  (is (= 33583 (fuel-required 100756)))
  )

(deftest total-fuel-required-test
  (is (= 2 (total-fuel-required 12 0)))
  (is (= 2 (total-fuel-required 14 0)))
  (is (= 966 (total-fuel-required 1969 0)))
  (is (= 50346 (total-fuel-required 100756 0)))
  )
