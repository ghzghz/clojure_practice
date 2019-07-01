(ns rnum.core
  (:gen-class))

(def numerals [["M" 1000]
               ["CM" 900]
               ["D" 500]
               ["CD" 400]
               ["C" 100]
               ["XC" 90]
               ["L" 50]
               ["XL" 40]
               ["X" 10]
               ["IX" 9]
               ["V" 5]
               ["IV" 4]
               ["I" 1]])

(defn firsts [v] (map first v))

(defn seconds [v] (map second v))

(defn to-factors [d basis]
  "Factor the decimal w.r.t. the suppled basis (vector of values)
  returns a vector that is positionally alligned with the basis
  e.g.
  (to-factors 12 [10 1])  -> [1 2]   i.e. [(1*10) + (2*1)]
  (to-factors 12 [8 4 1]) -> [1 1 0] i.e. [(1*8) + (1*4) + (0*1)]
  "
  (rest
    (firsts
      (reduce
        #(conj %1
              ((juxt quot mod)
               (last (last %1))
               %2))
         [[0 d]]
         basis))))

(defn expand-to-string [v]
  "take a vector of pairs: [str n] and expand it
  by repeating str n times and joining that with all values in vector
  (expand-to-string [['x' 2] ['y' 3]]) -> 'xxyyy'
  "
  (clojure.string/join
    (map
      #(let [[s n] %1]
        (clojure.string/join (repeat n s)))
      v)))

(defn to-numerals [d]
  "from decimal to roman numerals"
  (if
    (< d 1)
      ""
      (expand-to-string
        (map
          vector
          (firsts numerals)
          (to-factors d (seconds numerals))))))

(defn -main
  [& args]
  (println (map (juxt identity to-numerals) (range 100)))
  )

