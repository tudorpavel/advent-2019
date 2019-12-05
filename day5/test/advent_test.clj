(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest solve1-test
  (with-redefs-fn {#'intcode-computer/run-program
                   (fn [_ i] {:outputs i})}
    #(is (= [1] (solve1 [])))))
