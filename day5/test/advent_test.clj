(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest first-non-zero-output-test
  (with-redefs-fn {#'intcode-computer/collect-outputs
                   (fn [_ i] [0 0 (i 0)])}
    #(is (= 7 (first-non-zero-output [] 7)))))
