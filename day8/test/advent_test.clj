(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest find-min-zeros-test
  (is (= {0 2 1 4} (find-min-zeros '({0 2 1 4} {0 3 5 23}))))
  (is (= {0 2 1 4} (find-min-zeros '({0 3 5 23} {0 2 1 4}))))
  )

(deftest decode-image-test
  (is (= '(0 1 1 0) (decode-image '((0 2 2 2)
                                    (1 1 2 2)
                                    (2 2 1 2)
                                    (0 0 0 0)))))
  )
