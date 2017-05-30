(ns meetup.pages.core
  (:require
   [meetup.components.fullcalendar :as fullcalendar]
   [meetup.components.gitter :as gitter]

   [meetup.pages.navbar :as navbar]
   [cljsjs.material-ui]
   [cljsjs.material-ui-svg-icons]
   [cljsjs.moment]

   [reagent.core :as reagent]
   [reagent.session :as session]
   [ajax.core :as ajax]
   [ajax.edn :as edn]

   [bidi.bidi :as bidi]
   )
  )

;; TODO: need to handle fullcalendar's `googleCalendarError`

(def base-url "/meetup.clj")

(def app-routes
  [(str base-url "/")
   [["" :index]
    ["list" :list]
    ["chat" :chat]
    [true :four-o-four]]])

;; http://materializecss.com/icons.html
;; http://htmltohiccup.herokuapp.com/


;; https://fullcalendar.io/docs/event_data/Event_Object/
(defn month-calendar []
  [fullcalendar/component
   {
    :header {
             :left ""
             :center "title"
             :right "today prev,next"
             }
    :views {}
    :locale "en"
    :defaultView "month"
    :defaultDate (.format (js/moment))
    :timeFormat "H:mm"
    :navLinks true
    :editable false
    :eventLimit true
    :events
    (fn [start, end, timezone, callback]
      (ajax/GET (str base-url "/data/events.edn")
                {:response-format (edn/edn-response-format)
                 :handler
                 (fn [resp]
                   (let [events (:events resp)]
                     ;; (=
                     ;;  (-> "2017-03-23 19:30:00"
                     ;;      (js/moment "YYYY-MM-DD HH:mm:ss")
                     ;;      (.month ))
                     ;;  2)

                     (callback (clj->js events))
                     )
                   )
                 })
      )

    ;; https://fullcalendar.io/docs/google_calendar/
    :googleCalendarApiKey
    "AIzaSyBzjvpScabjRmNgYM6xDmZQnAKRLy9j_iE"

    :eventSources
    [{:googleCalendarId
      "jmnbmacphf206ju2ulmda01n9k@group.calendar.google.com" :color "blue"}]

    ;; https://fullcalendar.io/docs/mouse/eventClick/
    :eventClick
    (fn [event, jsEvent, view]
      (js/alert "hi")
      false)

    ;; https://fullcalendar.io/docs/selection/select_method/
    :selectable true
    :select
    (fn [start, end, jsEvent, view]
      (js/alert "[TODO] need to make add page")
      ;; # react-popup : http://minutemailer.github.io/react-popup/
      false)
    }])

(defn list-calendar []
  [fullcalendar/component
   {
    :header {
             :left ""
             :center "title"
             :right "today prev,next"
             }
    :views {}
    :locale "en"
    :defaultView "listMonth"
    :defaultDate (.format (js/moment))
    :timeFormat "H:mm"
    :navLinks true
    :editable false
    :eventLimit true
    :events
    (fn [start, end, timezone, callback]
      (ajax/GET (str base-url "/data/events.edn")
                {:response-format (edn/edn-response-format)
                 :handler
                 (fn [resp]
                   (let [events (:events resp)]
                     ;; (=
                     ;;  (-> "2017-03-23 19:30:00"
                     ;;      (js/moment "YYYY-MM-DD HH:mm:ss")
                     ;;      (.month ))
                     ;;  2)

                     (callback (clj->js events))
                     )
                   )
                 })
      )
    #_(fn [start, end, timezone, callback]
        (ajax/GET "/test.edn" {:handler
                               (fn [resp]
                                 (let [x (reader/read-string resp)]
                                   (println x)
                                   (callback (clj->js [(:event x)]))
                                   ))
                               })
        )
    :eventClick
    (fn [calEvent, jsEvent, view]
      (js/alert "hi")
      false)
    }])

;; [:i.large.material-icons "add"]




;; (schema/check bidi.schema/RoutePair app-routes)
;; (schema/validate bidi.schema/RoutePair app-routes)



(defmulti page-contents identity)

(defmethod page-contents :index []
  [:span
   [month-calendar]])

(defmethod page-contents :list []
  [:span
   [list-calendar]])

(defmethod page-contents :chat []
  [:span
   [gitter/component "https://gitter.im/clojure_tokyo/Lobby/~embed"]])

(defmethod page-contents :four-o-four []
  "Non-existing routes go here"
  [:span
   [:h1 "404: It dfadsis not here"]
   [:pre.verse
    "What you areng for, I do not have. How could I have, what does not exist?"]])


(defmethod page-contents :default []
  "Configured routes, missing an implementation, go here"
  [:span
   [:h1 "404: My basdfad"]
   [:pre.verse
    "This page should be here, but I never created it."]])

(defn page
  []
  (let [page (:current-page (session/get :route))]
    [:div
     [navbar/navbar app-routes page]

     ^{:key page}
     [:div.container [page-contents page]]]))
