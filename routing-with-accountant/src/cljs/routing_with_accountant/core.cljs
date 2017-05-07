(ns routing-with-accountant.core
    (:require [reagent.core :as reagent]
              [reagent.session :as session]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

(enable-console-print!)

;; Views
;;
(defn home-page []
  [:div
   [:h1 "Home Page"]
   [:a {:href "/about"} "There's no place like home."]])

(defn about-page []
  [:div
   [:h1 "About Page"]
   [:a {:href "/"} "Toto, we're not in Kansas anymore!"]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; Routes
;;
(secretary/defroute "/" []
  (session/put! :current-page #'home-page)) 

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; Initialize app
;;
(defn ^:export main []
  (accountant/configure-navigation! {:nav-handler (fn [path]
                                                    (println (str "Dispatching to path " path))
                                                    (secretary/dispatch! path))
                                     :path-exists? (fn [path]
                                                     (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (reagent/render [current-page]
                  (.getElementById js/document "app")))

