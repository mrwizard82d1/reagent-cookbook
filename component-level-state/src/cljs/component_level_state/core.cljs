(ns component-level-state.core
    (:require [reagent.core :as reagent]))

;; Do this
(defn foo [] ; A function
  (let [component-state (reagent/atom {:count 0})] ; State available to inner, render function
    (fn [] ; That returns a (render) function
      [:div ; That returns hiccup
       [:p "Current count is: " (get @component-state :count)] ; that accesses function closure state
       [:button {:on-click #(swap! component-state update-in [:count] inc)} "Increment"]])))

;; But don't do this
(defn foo-mistake []  ; A function
  (let [component-state (reagent/atom {:count 0})] ; With local state
    [:div ; That returns a hiccup
     [:p "Current count is: " (get @component-state :count)]
     [:button {:on-click ; Clicking the button updates `component-state`; however, when Reagent / React
               #(swap! component-state ; invokes `foo-mistake` the next time, it will initialize
                       update-in [:count] inc)} "Increment"]])) ; `component-state` *again*.

;; But don't do this
(defn foo-mistake2 []  ; A function
  (let [component-state (reagent/atom {:count 0})] ; With local state
    [:div ; That returns a hiccup
     [:p "Current count is: " (get @component-state :count)]
     (.log js/console (str "`foo-mistake2` is being rendered using " 
                           (get @component-state :count) ".")) ; Display state at each call to `render`
     [:button {:on-click ; Clicking the button updates `component-state`; however, when Reagent / React
               #(swap! component-state ; invokes `foo-mistake` the next time, it will initialize
                       update-in [:count] inc)} "Increment"]])) ; `component-state` *again*.

;; Do this
(defn foo-inner-let [] ; A function
  (let [component-state (reagent/atom {:count 0})] ; State available to inner, render function
    (fn [] ; That returns a (render) function
      (let [count (get @component-state :count)]
        [:div ; That returns hiccup
         [:p "Current count is: " count] ; that accesses function closure state
         [:button {:on-click #(swap! component-state update-in [:count] inc)} "Increment"]]))))

(defn home []
  [:div
   [:h1 "`foo`"]
   [foo]
   [:h1 "`foo-mistake`"]
   [foo-mistake]
   [:h1 "`foo-mistake2`"]
   [foo-mistake2]
   [:h1 "`foo-inner-let`"]
   [foo-inner-let]])

(defn ^:export main []
  (reagent/render [home]
                  (.getElementById js/document "app")))

