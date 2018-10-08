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

(def interpolant-classes
  AffineFunctional1d ConstantFunction
;;----------------------------------------------------------------

(doseq [^doubles vk value-knots]
  (println (Arrays/toString vk)))

(println AffineFunctional1d/INTERPOLATORS)
(println AffineFunctional1d/interpolateXY)