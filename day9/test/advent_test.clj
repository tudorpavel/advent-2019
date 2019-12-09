(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest boost-keycode-test
  (is (= 6 (boost-keycode [3 0 109 8 21101 3 3 1 104 3 99])))
  )
