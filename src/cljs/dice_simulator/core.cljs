(ns dice-simulator.core
  (:require
    [reagent.core :as r]))

(enable-console-print!)

(defonce app-state
  (r/atom
    {:result nil}))

(defn app []
  [:div
   (str (:result @app-state))])

(defn ^:export run[]
  (r/render
    [app]
    (js/document.getElementById "app")))

(run)

