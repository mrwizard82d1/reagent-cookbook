(ns add-routing.core
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [reagent.core :as reagent]
            [secretary.core :as secretary]
            [goog.events :as events]
            [goog.history.EventType :as EventType]))

;; Keep the application state in a single `reagent/atom`.
(def app-state (reagent/atom {}))

;; Listen to navigation changes in browser history
(defn hook-browser-navigation! [] 
  (doto (History.)
    (events/listen EventType/NAVIGATE
                   (fn [event] 
                     (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

;; Define application routes
(defn app-routes []
  ;; Use '#' navigation
  (secretary/set-config! :prefix "#")
  
  ;; "/" is our home page
  (defroute "/" [] 
    (swap! app-state assoc :page :home))

  ;; And "/about" is our about page
  (defroute "/about" []
    (swap! app-state assoc :page :about))

  ;; *After* defining *all* our routes, integrate with browser navigation
  (hook-browser-navigation!))

;; Create home and about pages that link to one another
(defn home []
  [:div 
   [:h1 "Home Page"]
   [:a {:href "#/about"} "about page"]])

(defn about []
  [:div 
   [:h1 "About Page"]
   [:a {:href "#/"} "home page"]])

;; The `current-page` multimethod determines the next page based on `app-state`
;;
;; Remember the second argument is the *function8 used to distinguish between different multimethods.
;;
;; Additionally, remember that each multimethod implementation must return a *vector* containing the
;; `reagent` (react) component.  (I do not fully understand the reason for this requirement. I think 
;; it is because `reagent` expects a "hiccup-like" result.
;;
;; Finally, the `:default` implementation is the "catch all" implementation. It returns an empty `div`.
;;
(defmulti current-page #(@app-state :page))
(defmethod current-page :home []
  [home])
(defmethod current-page :about []
  [about])
(defmethod current-page :default []
  [:div ])

;; `main` must perform two tasks: define the routes and render the current page.
;;
;; Again, `current-page` is a multi-method returning a *vector* containing a function returning a
;; `reagent` component.
;;
(defn ^:export main []
  (app-routes)
  (reagent/render [current-page]
                  (.getElementById js/document "app")))

