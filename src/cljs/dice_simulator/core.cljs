(ns dice-simulator.core
  (:require
    [reagent.core :as r]))

(enable-console-print!)

(defonce app-state
  (r/atom
    {:dice 2
     :faces 2
     :iterations 10000
     :result nil}))

(def dice [1 2 3 4 5 6])

(def faces [1 2 3 4 5 6])

(def iterations [100 1000 10000 100000 1000000])

(defn button [kw c i]
  (let [v (get c i)]
   ^{:key i}
   [:button
    {:on-click #(swap! app-state assoc kw v)
     :disabled (= v (kw @app-state))}
    v]))

(defn sim []
  (let [d (:dice @app-state)
        f (:faces @app-state)
        n (:iterations @app-state)]
    (loop [i 0 res {true 0 false 0}]
      (if (= i n)
        (swap! app-state assoc :result res)
        (let [r (take d (repeatedly #(rand-int 6)))
              k (reduce #(or %1 (< %2 f)) false r)]
          (recur (inc i) (update-in res [k] inc)))))))

(def sep [:div {:style {:height "16px"}}])

(defn app []
  [:div
   [:div
    [:div "Dice"]
    (doall (map-indexed #(button :dice dice %) dice))]
   [:div
    [:div "Faces"]
    (doall (map-indexed #(button :faces faces %) faces))]
   [:div
    [:div "Iterations"]
    (doall (map-indexed #(button :iterations iterations %) iterations))]
   sep
   [:div
    [:button
     {:on-click sim
      :disabled (not (and (:dice @app-state)
                          (:faces @app-state)
                          (:iterations @app-state)))}
     "Go!"]]
   sep
   (when-let [res (:result @app-state)]
     [:div
       [:div (str (/ (int (* 10000 (/ (res true) (+ (res true) (res false))))) 100) "%")]
       [:div (str res)]])])

(defn ^:export run[]
  (r/render
    [app]
    (js/document.getElementById "app")))

(run)

