;; clj src/scripts/clojure/mudstone/scripts/plots.clj
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
;;----------------------------------------------------------------
(ns mudstone.scripts.expresso
  
  {:doc "solve symbolic systems of equations for interpolating 
         polynomials"
   :author "palisades dot lakes at gmail dot com"
   :version "2018-10-17"}
  
  (:require [clojure.java.io :as io]
            [clojure.string :as s])
  )

;;----------------------------------------------------------------

