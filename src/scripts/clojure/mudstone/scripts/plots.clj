(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
;;----------------------------------------------------------------
(ns mudstone.scripts.plots
  
  {:doc "data files for R plots of interpolants"
   :author "palisades dot lakes at gmail dot com"
   :version "2018-10-05"}
  
  (:require [clojure.java.io :as io])
  (:import [java.util Arrays]
           [mudstone.java.functions.scalar 
            AffineFunctional1d]))

;; clj src/scripts/clojure/mudstone/scripts/plots.clj

;;----------------------------------------------------------------
(def ^Double/TYPE GOLDEN-RATIO 
  (double (* 0.5 (+ 1.0 (Math/sqrt 5.0)))))

(def value-knots
  (mapv double-array
        [[-1.0 0.0 1.0 2.0]
         [0.0 1.0 GOLDEN-RATIO (* GOLDEN-RATIO GOLDEN-RATIO)]
         [0.99 1.0 1.01 1.02]
         [0.49 0.50 0.51 0.52]
         [-1.0e2 0.0 1.0e2 2.0e2]
         [99.0 100.0 101.0 102.0]
         [0.0 1.0e2 (* GOLDEN-RATIO 1.0e2) 
          (* GOLDEN-RATIO GOLDEN-RATIO 1.0e2)]]))

;;----------------------------------------------------------------

(doseq [^doubles vk value-knots]
  (println (Arrays/toString vk)))

(println AffineFunctional1d/INTERPOLATORS)
(println AffineFunctional1d/interpolateXY)