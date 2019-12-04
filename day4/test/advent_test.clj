(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest digits-test
  (is (= [1 2 3] (digits 123)))
  )

(deftest count-digits-test
  (is (= [0 4 2 0 0 0 0 0 0 0] (count-digits (digits 111122))))
  (is (= [0 1 3 0 1 1 0 0 0 0] (count-digits (digits 122245))))
  )

(deftest loosely-valid?-test
  (is (loosely-valid? 111111))
  (is (not (loosely-valid? 223450)))
  (is (not (loosely-valid? 123789)))
  (is (loosely-valid? 112233))
  (is (loosely-valid? 123444))
  (is (loosely-valid? 111122))
  (is (not (loosely-valid? 153441)))
  )

(deftest strictly-valid?-test
  (is (not (strictly-valid? 111111)))
  (is (not (strictly-valid? 223450)))
  (is (not (strictly-valid? 123789)))
  (is (strictly-valid? 112233))
  (is (not (strictly-valid? 123444)))
  (is (strictly-valid? 111122))
  (is (not (strictly-valid? 153441)))
  )

(deftest solve1-test
  (is (= 4 (solve1 (list 111122 111125))))
  )

(deftest solve2-test
  (is (= 1 (solve2 (list 111122 111125))))
  )
