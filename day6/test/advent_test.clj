(ns advent-test
  (:require [advent :refer :all]
            [clojure.test :refer :all]))

(deftest insert-orbit-test
  (is (= {:A '(:B)} (insert-orbit {} :A :B)))
  (is (= {:A '(:C :B)} (insert-orbit {:A '(:B)} :A :C)))
  (is (= {:A '(:B)} (insert-orbit {} "A" "B")))
  )

(deftest pairs->orbits-test
  (is (= {:COM '(:B)
          :B '(:G :C)
          :C '(:D)
          :D '(:I :E)
          :E '(:J :F)
          :G '(:H)
          :J '(:K)
          :K '(:L)}
         (pairs->orbits ["COM)B"
                         "B)C"
                         "C)D"
                         "D)E"
                         "E)F"
                         "B)G"
                         "G)H"
                         "D)I"
                         "E)J"
                         "J)K"
                         "K)L"]))))

(deftest compute-total-depths-test
  (let [orbits {:COM '(:B)
                :B '(:C :G)
                :C '(:D)
                :D '(:E :I)
                :E '(:F :J)
                :G '(:H)
                :J '(:K)
                :K '(:L)}]
    (is (= 42 (compute-total-depths orbits)))
    ))
