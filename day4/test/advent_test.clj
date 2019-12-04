(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest digits-test
  (is (= [1 2 3] (digits 123)))
  )

(deftest same-adjacent?-test
  (is (same-adjacent? (digits 122345)))
  (is (not (same-adjacent? (digits 153452))))
  )

(deftest valid-password?-test
  (is (valid-password? 111111))
  (is (valid-password? 122345))
  (is (valid-password? 111123))
  (is (not (valid-password? 223450)))
  (is (not (valid-password? 123789)))
  )

(deftest solve1-test
  (is (= 3 (solve1 (list 111123 111125))))
  )
