(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest first-non-zero-output-test
  (with-redefs-fn {#'intcode-computer/run-program
                   (fn [_ i] {:output 7})}
    #(is (= 7 (first-non-zero-output [] 1)))))
