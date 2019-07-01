(ns rnum.core-test
  (:require [clojure.test :refer :all]
            [rnum.core :refer :all]))

(deftest simple-tests
    (is (= (to-numerals 1) "I"))
    (is (= (to-numerals 5) "V"))
    (is (= (to-numerals 88) "LXXXVIII"))
    (is (= (to-numerals 1712) "MDCCXII"))
    (is (= (to-numerals 2000) "MM"))
    (is (= (to-numerals 2029) "MMXXIX"))
    )

(deftest boundery-tests
    (is (= (to-numerals 0) ""))
    (is (= (to-numerals -1) ""))
    )

(deftest internal-to-factors
  (is (= (to-factors 12 [10 1]) [1 2]))
  (is (= (to-factors 12 [8 4 1]) [1 1 0]))
  (is (= (to-factors 100 [64 32 16 8 4 2 1]) [1 1 0 0 1 0 0 ]))
  (is (= (to-factors 99 [50 40 10 9 5 4 1]) [1 1 0 1 0 0 0]))
  )

(deftest internal-expand-to-string
  (is (= (expand-to-string [["x" 10]]) "xxxxxxxxxx"))
  (is (= (expand-to-string [["x" 2] ["y" 2]]) "xxyy"))
  (is (= (expand-to-string [["bob" 2] ["bad" 0] ["fred" 1] ["bad" 0]]) "bobbobfred"))
  )
